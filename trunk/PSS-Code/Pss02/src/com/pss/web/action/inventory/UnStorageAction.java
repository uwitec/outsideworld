package com.pss.web.action.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.pss.domain.model.entity.inventory.Storage;
import com.pss.service.IBusinessService;
import com.pss.service.IStorageService;
import com.pss.web.action.PaginationAction;

/**
 * 
 * @author Aries Zhao
 * 
 */
@Controller
public class UnStorageAction extends PaginationAction<Storage> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IStorageService unStorageService;

	@Override
	public IBusinessService<Storage> service() {
		return unStorageService;
	}
}
