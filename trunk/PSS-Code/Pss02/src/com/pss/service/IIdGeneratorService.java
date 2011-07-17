package com.pss.service;

public interface IIdGeneratorService {
	/**
	 *  获取下一个整形Id号
	 * @param seqName
	 * @return
	 */
	public Integer next(String seqName);
	/**
	 * 获取固定长度的id，不足长度的部分以0填充
	 * @param seedName
	 * @param fixedLength
	 * @return
	 */
	public String nextStr(String seedName, int fixedLength);
	/**
	 * 获取下一个字符型Id号
	 * @param seedName id的名称
	 * @param fixedLength 固定的长度
	 * @param ch 填充的值
	 * @return
	 */
	public String nextStr(String seedName, int fixedLength, char ch);
}
