package com.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 同心串算法
 * 
 * @author fangxia722
 */
public class ConcentricString {
	// 所有的标点符号
	private static final char[] segments = new char[] { '?', '　', '、', '。',
			'·', '-', 'ˇ', '〃', '々', '—', '～', '‖', '…', '‘', '’', '“', '”',
			'〔', '〕', '〈', '〉', '《', '》', '「', '」', '『', '』', '〖', '〗', '【',
			'】', '±', '×', '÷', '∶', '∧', '∨', '∑', '∏', '∪', '∩', '∈', '∷',
			'√', '⊥', '∥', '∠', '⌒', '⊙', '∫', '∮', '≡', '≌', '≈', '∽', '∝',
			'≠', '≮', '≯', '≤', '≥', '∵', '∴', '♂', '♀', '°', '′', '″', '℃',
			'$', '¤', '￠', '￡', '‰', '§', '№', '☆', '★', '○', '●', '◎', '◇',
			'◆', '□', '■', '△', '▲', '※', '→', '←', '↑', '↓', '〓', '！', '#',
			'￥', '，', '&', '.', '：', '（', '）', '*', '+', '－', '．', '/', '；',
			'<', '=', '>', '？', '[', ']', '^', '_', '`', '{', '|', '}', '￣',
			'￥', '%', '█', '▉', '▊', '▋', '▌', '▍', '▎', '▏', '▓', '▔', '▕',
			'▼', '▽', '◢', '◣', '◤', '◥', '☉', '⊕', '〒', '〝', '〞', '╳', '╲',
			'╱', '╰', '╯', '╮', '╭', '╬', '╫', '╪', '╩', '╨', '╜', '╝', '╞',
			'╟', '╠', '╡', '╢', '╣', '╤', '╥', '╦', '╧', '≒', '≦', '≧', '⊿',
			'═', '║', '╒', '╓', '╔', '╕', '╖', '╗', '╘', '╙', '╚', '╛', 'ˊ',
			'ˋ', '˙', '–', '―', '‥', '℉', '↖', '↗', '↘', '↙', '∕', '∟', '∣',
			'〡', '〢', '〣', '〤', '〥', '〦', '〧', '〨', '〩', '㊣', '㎎', '㎏', '㎜',
			'㎝', '㎞', '㎡', '㏄', '㏎', '㏑', '㏒', '㏕', '︰', '￢', '￤', '?', '℡',
			'㈱', '゛', 'ヽ', '〆', 'ゞ', '﹊', '﹌', '﹎', '﹐', '﹒', '﹕', '﹗', '﹚',
			'﹜', '﹞', '﹠', '﹢', '﹤', '﹦', '﹩', '﹫', '﹥', '﹨', '﹡', '﹪', ':',
			'@', '"', '!', ';', '・', ',', '~', '\t', '\n', '\r' };
	private static final Map<Character, String> segSet = new HashMap<Character, String>();
	static {
		for (char c : segments) {
			segSet.put(c, "0");
		}
	}

	/**
	 * 将title转换为长度为2~concent长度的同心串
	 */
	private static final int concent = 5;

	public List<String> concentric(String title) throws Exception {
		// 根据句子之间的标点符号以及句子内的标点符号
		// 根据句子之间的标点符号以及句子内的标点符号
		char[] chars = title.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (isStop(chars[i])) {
				chars[i] = ' ';
			}
		}
		title = new String(chars);
		String[] ss = title.split(" ");
		List<String> arrays = new ArrayList<String>();
		for (String s : ss) {
			if (!StringUtils.isBlank(s)) {
				arrays.add(s);
			}
		}
		List<String> result = new ArrayList<String>();
		for (String s : arrays) {
			for (int i = 0; i < s.length(); i++) {
				for (int j = 2; j < concent && i + j <= s.length(); j++) {
					String word = s.substring(i, i + j);
					result.add(word);
				}
			}
		}
		return result;
	}

	/**
	 * 查看当前的字符c是不是句子分割符,利用GBK字库进行排除
	 * 
	 * @param c
	 * @return
	 */
	public boolean isStop(char c) {
		return segSet.get(c) != null||'a'<=c&&c<='z'||'A'<=c&&c<='Z';
	}
}
