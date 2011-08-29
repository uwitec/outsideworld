/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.service.impl.IdService.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Aug 29, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.service.impl;

import com.pss.domain.repository.system.IdRepository;
import com.pss.service.IIdGeneratorService;

/**
 * <p>类说明</p> 
 * <p>Copyright: 版权所有 (c) 2010 - 2030</p>
 * <p>Company: Travelsky</p>
 * @author  Travelsky
 * @version 1.0
 * @since   Aug 29, 2011
 */
public class IdService implements IIdGeneratorService {

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
