package org.apache.nutch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.BitSet;

public class BloomFilter {

	private static final int DEFAULT_SIZE = 2 << 4;
	private static final int[] seeds = new int[] { 5, 7, 11, 13, 31, 37, 61 };
	private BitSet bits = new BitSet(DEFAULT_SIZE);
	private SimpleHash[] func = new SimpleHash[seeds.length];

	private File file = null;
	private int interval = 3600 * 1000;
	private boolean loaded = false;

	public BloomFilter(File file, int interval) {
		this.file = file;
		this.interval = interval;

		for (int i = 0; i < seeds.length; i++) {
			func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
		}

		new SaveThread().start();
	}

	public void add(String value) {
		for (SimpleHash f : func) {
			bits.set(f.hash(value), true);
		}
	}

	public boolean contains(String value) {
		if (value == null) {
			return false;
		}
		boolean ret = true;
		for (SimpleHash f : func) {
			ret = ret && bits.get(f.hash(value));
		}
		return ret;
	}

	private static class SimpleHash {
		private int cap;
		private int seed;

		public SimpleHash(int cap, int seed) {
			this.cap = cap;
			this.seed = seed;
		}

		public int hash(String value) {
			int result = 0;
			int len = value.length();
			for (int i = 0; i < len; i++) {
				result = seed * result + value.charAt(i);
			}
			return (cap - 1) & result;
		}
	}

	public void save(File file) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			for (int i = 0; i < bits.size(); i++) {
				try {
					fos.write(bits.get(i) ? '1' : '0');
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	public void load(File file) {
		if (!file.exists()) {
			return;
		}
		FileInputStream fis = null;
		try {
			bits = new BitSet(DEFAULT_SIZE);
			fis = new FileInputStream(file);
			int b = 0;
			int i = 0;
			while ((b = fis.read()) != -1) {
				bits.set(i++, b == 49);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				// ignore
			}
			loaded = true;
		}
	}

	private class SaveThread extends Thread {

		@Override
		public void run() {
			while (true) {
				if (!loaded) {
					load(file);
				}
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					// ignore
				}
				save(file);
			}
		}
	}
}
