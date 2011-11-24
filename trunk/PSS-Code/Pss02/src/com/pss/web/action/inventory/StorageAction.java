package com.pss.web.action.inventory;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.pss.domain.model.entity.inventory.Storage;
import com.pss.exception.BusinessHandleException;
import com.pss.service.IBusinessService;
import com.pss.service.IStorageDetailService;
import com.pss.service.IStorageService;
import com.pss.web.action.PaginationAction;
import com.pss.web.util.WebUtil;

/**
 * 
 * @author Aries Zhao
 * 
 */
@Controller
public class StorageAction extends PaginationAction<Storage> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IStorageService storageService;

	@Resource(name = "StorageDetailService")
	private IStorageDetailService detailService;

	@Override
	public IBusinessService<Storage> service() {
		return storageService;
	}

	@Override
	public String deleteEntity() {
		try {
			List<String> masterIds = WebUtil.split(selectedIds, ",");
			if (masterIds != null) {
				for (String masterId : masterIds) {
					detailService.deleteByMasterId(masterId);
				}
				storageService.delete(masterIds);
			}
		} catch (BusinessHandleException e) {
			setCorrect(false);
			return ERROR;
		}
		setCorrect(true);
		return SUCCESS;
	}
}
