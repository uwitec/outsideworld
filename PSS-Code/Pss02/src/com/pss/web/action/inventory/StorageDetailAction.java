package com.pss.web.action.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.pss.domain.model.entity.inventory.StorageDetail;
import com.pss.service.IBusinessService;
import com.pss.service.IGoodService;
import com.pss.service.IStorageDetailService;
import com.pss.web.action.DetailsPaginationAction;

/**
 * 
 * @author Aries Zhao
 * 
 */
@Controller
public class StorageDetailAction extends DetailsPaginationAction<StorageDetail> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IStorageDetailService storageDetailService;

	@Override
	public IBusinessService<StorageDetail> service() {
		return storageDetailService;
	}
}
