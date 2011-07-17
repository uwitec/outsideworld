package com.pss.dao.system;

import java.util.Map;

public interface SequenceMapper {
	Object getSeqByName(String seqName);

	int updateSeq(Map<String, Object> paramMap);

	int insertSeq(Map<String, Object> paramMap);
}