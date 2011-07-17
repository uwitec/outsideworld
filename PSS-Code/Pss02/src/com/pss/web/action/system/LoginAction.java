/*
 *
 * ========================================================================
 * 版权:   Travelsky  版权所有  (c) 2010 - 2030
 * 所含类(文件):  com.pss.web.action.system.LoginAction.java
 *
 *
 * 修改记录：
 * 日期                       作者                              内容
 * ========================================================================
 * Jun 19, 2011       Travelsky         新建文件
 * ========================================================================
 */

package com.pss.web.action.system;

import org.apache.commons.lang.StringUtils;

import com.pss.domain.model.entity.sys.User;
import com.pss.exception.BusinessHandleException;
import com.pss.service.IUserService;
import com.pss.service.LoginResult;
import com.pss.web.WebKeys;
import com.pss.web.action.AbstractAction;

/**
 * <p>类说明</p> 
 * <p>Copyright: 版权所有 (c) 2010 - 2030</p>
 * <p>Company: Travelsky</p>
 * @author  Travelsky
 * @version 1.0
 * @since   Jun 19, 2011
 */
public class LoginAction extends AbstractAction {
	private IUserService userService;
	private User user;
	private String tenantName;
    public String index(){
    	return SUCCESS;
    }
    
    public String login() {
    	try {
			LoginResult result = userService.login(user, tenantName);
			if(!StringUtils.isBlank(result.getMessage())){
				addActionError(getText(result.getMessage()));
				return LOGIN;
			}
			putDataToSession(WebKeys.USER, result.getUser());
		} catch (BusinessHandleException e) {
			return ERROR;
		}
    	return SUCCESS;
    }

	public IUserService getUserService() {
		
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
	
}
