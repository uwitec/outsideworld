package com.pss.domain.repository.system;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.pss.dao.system.SequenceMapper;

public class IdRepository {
	private SequenceMapper sequenceMapper;

	/**
	 * 产生id
	 * 
	 * @param seqName
	 * @return
	 */
	public Integer generateSequence(String seqName) {
		// 循环的次数
		Integer tryCount = 0;
		// 死循环，直到获得有效id为至
		while (true) {
			// 循环次数增加
			tryCount++;
			// 从数据库获得id
			Object seqValue = sequenceMapper.getSeqByName(seqName);
			// 获得整形id
			Integer originValue = transfer(seqValue);
			// 获得下一个id
			Integer newValue = originValue + 1;
			// 更新数据库
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("seqName", seqName);
			paramMap.put("originValue", originValue);
			paramMap.put("newValue", newValue);
			int count = 0;
			//如果数据库中没有记录，则进行插入，否则根据新老记录进行更新，若没有成功，则重新取得老记录
			if (seqValue == null) {
				count = sequenceMapper.insertSeq(paramMap);
			} else {
				count = sequenceMapper.updateSeq(paramMap);
			}
			if (count > 0) {
				return newValue;
			}
		}
	}

	private Integer transfer(Object seqValue) {
		if (seqValue == null) {
			return 0;
		} else if (seqValue instanceof Integer || seqValue instanceof Long
				|| seqValue instanceof BigInteger
				|| seqValue instanceof BigDecimal) {
			return (Integer) seqValue;
		} else {
			throw new IllegalArgumentException("Unknown number format.");
		}
	}

}