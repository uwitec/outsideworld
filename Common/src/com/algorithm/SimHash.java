package com.algorithm;

import java.util.List;

import com.model.Word;
import com.util.MD5;

public class SimHash {
	private MD5 md5 = new MD5();

	/**
	 * 使用汉明距离计算a和b的差距
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public int distance(int[] a, int[] b) {
		int result = 0;
		for (int i = 0; i < a.length; i++) {
			result += (a[i] - b[i]) == 0 ? 0 : 1;
		}
		return result;
	}

	/**
	 * 使用simHash
	 * 
	 * @param words
	 * @return
	 */
	public int[] simHash(List<Word> words) {
		// 1将一个words.size()维度的V初始化为0
		double[] V = new double[128];
		// 2、对每一个特征，用传统的hash算法对该特征产生一个f位的签名b。
		// 对1到f：如果b的第i位为1，则V的第i个元素加上该特征的权重，
		// 否则，V的第i个权重减去该特征的权重
		for (int i = 0; i < words.size(); i++) {
			Word w = words.get(i);
			for (int j = 0; j < 128; j++) {
				if (hash(w)[j] == 1) {
					V[j] += w.getWeight();
				} else {
					V[j] -= w.getWeight();
				}
			}
		}
		// 3、如果V的第i个元素大于0。则S的第i位为1，否则为0
		int[] S = new int[V.length];
		for (int i = 0; i < V.length; i++) {
			if (V[i] > 0) {
				S[i] = 1;
			} else {
				S[i] = 0;
			}
		}
		return S;
	}

	/**
	 * 使用hash算法对字符串进行加密，返回一个固定的0，1数组int[]，
	 * 
	 * @param w
	 * @param len
	 * @return
	 */
	private int[] hash(Word w) {
		int[] result = new int[128];
		// 计算32位的MD5码，使用16进制
		String md5Code = md5.calcMD5(w.getWord());
		for (int i = 0; i < 32; i++) {
			char c = md5Code.charAt(i);
			int[] bit = toBit(c);
			for(int j=i*4;j<(i+1)*4;j++){
				result[j]=bit[j-i*4];
			}
		}
		return result;
	}
	
	private int[] toBit(char c){
		switch(c){
		case '0':
			return new int[]{0,0,0,0};
		case '1':
			return new int[]{0,0,0,1};
		case '2':
			return new int[]{0,0,1,0};
		case '3':
			return new int[]{0,0,1,1};
		case '4':
			return new int[]{0,1,0,0};
		case '5':
			return new int[]{0,1,0,1};
		case '6':
			return new int[]{0,1,1,0};
		case '7':
			return new int[]{0,1,1,1};
		case '8':
			return new int[]{1,0,0,0};
		case '9':
			return new int[]{1,0,0,1};
		case 'A':
			return new int[]{1,0,1,0};
		case 'B':
			return new int[]{1,0,1,1};
		case 'C':
			return new int[]{1,1,0,0};
		case 'D':
			return new int[]{1,1,0,1};
		case 'E':
			return new int[]{1,1,1,0};
		case 'F':
			return new int[]{1,1,1,1};
		default:
			return null;
		}
	}
}
