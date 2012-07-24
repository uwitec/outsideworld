package org.apache.nutch;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.BitSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BloomFilter {

	private static final Logger LOG = LoggerFactory
			.getLogger(BloomFilter.class);

	private static final int DEFAULT_SIZE = 2 << 24;
	private static final int[] seeds = new int[] { 5, 7, 11, 13, 31, 37, 61 };
	private BitSet bits = new BitSet(DEFAULT_SIZE);
	private SimpleHash[] func = new SimpleHash[seeds.length];

	private File file = null;
	private int interval = 1000 * 10;

	public BloomFilter(File file, int interval) {
		this.file = file;
		this.interval = interval;

		for (int i = 0; i < seeds.length; i++) {
			func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
		}

		load(file);

		new PersistThread().start();
	}

	public synchronized void add(String value) {
		for (SimpleHash f : func) {
			bits.set(f.hash(value), true);
		}
	}

	public synchronized boolean contains(String value) {
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
		LOG.debug("Writing {} for bloomfilter", file.getAbsolutePath());
		long start = System.currentTimeMillis();
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			for (int i = 0; i < bits.size(); i++) {
				try {
					bos.write(bits.get(i) ? '1' : '0');
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				// ignore
			}
		}
		long end = System.currentTimeMillis();
		LOG.debug("Finish write {} take {} seconds", file.getAbsolutePath(),
				(end - start) / 1000);
	}

	public void load(File file) {
		if (!file.exists()) {
			return;
		}
		LOG.debug("Loading {} for bloomfilter", file.getAbsolutePath());
		long start = System.currentTimeMillis();
		BufferedInputStream bis = null;
		try {
			bits = new BitSet(DEFAULT_SIZE);
			bis = new BufferedInputStream(new FileInputStream(file));
			int b = 0;
			int i = 0;
			while ((b = bis.read()) != -1) {
				bits.set(i++, b == 49);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
			} catch (IOException e) {
				// ignore
			}
		}
		long end = System.currentTimeMillis();
		LOG.debug("Finish load {} take {} seconds", file.getAbsolutePath(),
				(end - start) / 1000);
	}

	private class PersistThread extends Thread {

		@Override
		public void run() {
			while (true) {
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
