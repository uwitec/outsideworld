package com.pss.web.action.system;

import org.springframework.beans.factory.annotation.Autowired;

import com.pss.domain.model.entity.sys.User;
import com.pss.service.IBusinessService;
import com.pss.service.IUserService;
import com.pss.web.action.PaginationAction;

/** 
 * User的action
 * @author Aries Zhao
 */
public class UserAction extends PaginationAction<User> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IUserService userService;

	@Override
	public IBusinessService<User> service() {
		return userService;
	}
	
	
}
