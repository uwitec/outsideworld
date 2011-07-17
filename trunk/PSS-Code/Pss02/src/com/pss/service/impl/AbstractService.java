package com.pss.service.impl;

import com.pss.domain.repository.system.IdRepository;
import com.pss.service.IIdGeneratorService;

public abstract class AbstractService implements IIdGeneratorService {
	private IdRepository idRepository;

	public IdRepository getIdRepository() {
		return idRepository;
	}

	public void setIdRepository(IdRepository idRepository) {
		this.idRepository = idRepository;
	}
	
	
	public Integer next(String seqName) {
		return idRepository.generateSequence(seqName);
	}

	public String nextStr(String seedName, int fixedLength) {
		return nextStr(seedName, fixedLength, '0');
	}

	public String nextStr(String seedName, int fixedLength, char ch) {
		int newValue = next(seedName);
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
