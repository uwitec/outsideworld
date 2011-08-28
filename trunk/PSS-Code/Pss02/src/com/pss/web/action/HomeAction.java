package com.pss.web.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.pss.domain.model.entity.sys.Function;
import com.pss.domain.model.entity.sys.User;
import com.pss.service.IFunctionService;
import com.pss.web.WebKeys;

public class HomeAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IFunctionService functionService;

	// 所获得的系统的菜单
	private List<Function> systemFunction = new ArrayList<Function>();
	private List<Function> purchaseFunction = new ArrayList<Function>();
	private List<Function> inventoryFunction = new ArrayList<Function>();
	private List<Function> saleFunction = new ArrayList<Function>();

	@Override
	public String execute() {
		User user = getDataFromSession(WebKeys.USER);
		initFunction(user);
		return SUCCESS;
	}

	private void initFunction(User user) {
		String roleId = user.getRole().getRoleId();
		List<Function> functions = functionService.obtainFunction(roleId);
		if (functions != null) {
			for (Function function : functions) {
				if (StringUtils.equals(function.getModule(), "System")) {
					systemFunction.add(function);
				}
				if (StringUtils.equals(function.getModule(), "Purchase")) {
					purchaseFunction.add(function);
				}
				if (StringUtils.equals(function.getModule(), "Inventory")) {
					inventoryFunction.add(function);
				}
				if (StringUtils.equals(function.getModule(), "Sale")) {
					saleFunction.add(function);
				}
			}
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public IFunctionService getFunctionService() {
		return functionService;
	}

	public List<Function> getUserFunction() {
		return systemFunction;
	}

	public List<Function> getPurchaseFunction() {
		return purchaseFunction;
	}

	public List<Function> getInventoryFunction() {
		return inventoryFunction;
	}

	public List<Function> getSaleFunction() {
		return saleFunction;
	}
}
