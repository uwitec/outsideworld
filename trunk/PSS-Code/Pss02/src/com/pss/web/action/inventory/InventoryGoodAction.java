package com.pss.web.action.inventory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.pss.domain.model.entity.purchase.Good;
import com.pss.exception.BusinessHandleException;
import com.pss.service.IGoodService;
import com.pss.web.action.AbstractAction;

@Controller
public class InventoryGoodAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private String identifier = "name";

	private List<Good> items;

	@Autowired
	private IGoodService goodService;

	public String queryGoods() {
		Good good = new Good();
		good.setTenant(getTenantId());

		try {
			items = goodService.queryByPrefix(good);
		} catch (BusinessHandleException e) {
			setCorrect(false);
		}
		setCorrect(true);
		return SUCCESS;
	}

	public List<Good> getItems() {
		return items;
	}

	public String getIdentifier() {
		return identifier;
	}
}
