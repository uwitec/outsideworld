package com.pss.web.action.inventory;

import com.pss.domain.model.entity.inventory.Storage;
import com.pss.service.IBusinessService;
import com.pss.web.action.PaginationAction;

public class StorageAction extends PaginationAction<Storage> {

	private static final long serialVersionUID = 1L;

	@Override
	public IBusinessService<Storage> service() {
		return null;
	}

}
