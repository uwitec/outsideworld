package com.pss.dao.system;

import com.pss.dao.BaseMapper;
import com.pss.domain.model.entity.sys.User;

public interface UserMapper extends BaseMapper<User> {

	public void updateLastLoginTime(User user);

}
