package com.pss.domain.repository.system;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pss.dao.system.SequenceMapper;

public class BaseRepository {

	@Autowired
	private SequenceMapper sequenceMapper;

	protected int generateSequence(String seqName) {
		int tryCount = 0;

		while (true) {
			tryCount++;

			Object seqValue = sequenceMapper.getSeqByName(seqName);
			int originValue;
			int newValue;

			if (seqValue == null)
				originValue = 0;
			else if (seqValue instanceof Integer)
				originValue = ((Integer) seqValue).intValue();
			else if (seqValue instanceof Long)
				originValue = ((Long) seqValue).intValue();
			else if (seqValue instanceof BigInteger)
				originValue = ((BigInteger) seqValue).intValue();
			else if (seqValue instanceof BigDecimal)
				originValue = ((BigDecimal) seqValue).intValue();
			else
				throw new IllegalArgumentException("Unknown number format.");

			newValue = originValue + 1;
			try {
				int count = 0;
				if (seqValue == null) {
					Map<String, Object> paramMap = new HashMap<String, Object>(
							3);
					paramMap.put("seqName", seqName);
					paramMap.put("originValue", originValue);
					paramMap.put("newValue", newValue);
					count = sequenceMapper.insertSeq(paramMap);
				} else {
					Map<String, Object> paramMap = new HashMap<String, Object>(
							3);
					paramMap.put("seqName", seqName);
					paramMap.put("originValue", originValue);
					paramMap.put("newValue", newValue);
					count = sequenceMapper.updateSeq(paramMap);
				}
				if (count > 0)
					return newValue;
			} catch (Throwable err) {
				if (tryCount > 10)
					throw new RuntimeException(err);
				else {
					try {
						Thread.sleep(5);
					} catch (InterruptedException err2) {
					}
				}
			}
		}
	}

	protected String generateSequenceAsStr(String seedName, int fixedLength) {
		return generateSequenceAsStr(seedName, fixedLength, '0');
	}

	protected String generateSequenceAsStr(String seedName, int fixedLength,
			char ch) {
		int newValue = generateSequence(seedName);
		String temp = String.valueOf(newValue);
		if (temp.length() >= fixedLength)
			return temp;
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < (fixedLength - temp.length()); i++)
			buffer.append(ch);
		buffer.append(temp);

		return buffer.toString();
	}
}