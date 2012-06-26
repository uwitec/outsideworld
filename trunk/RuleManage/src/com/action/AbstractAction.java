package com.action;

import com.dao.CommonDAO;
import com.entity.Model;
import com.util.SpringFactory;

public abstract class AbstractAction {
	protected static final String SUCCESS="success";
	protected static final String FAIL="fail";
    protected static CommonDAO commonDao = SpringFactory.getBean("commonDAO"); 
    protected String message;
    
    protected abstract Model getModel();
    protected abstract void  setModels();
    
    public String delete(){
    	Model object = getModel();
    	if(object==null||object.getId()==-1){
    		message="传入数据为空!";
    		return FAIL;
    	}
    	commonDao.delete(object);
    	return SUCCESS;
    }
    
    public String insert(){
    	Object object = getModel();
    	if(object==null){
    		message="数据不存在!";
    		return FAIL;
    	}
    	commonDao.save(object);
    	return SUCCESS;
    }
    
    public String update(){
    	Model object = getModel();
    	if(object==null||object.getId()==-1){
    		message="传入数据为空!";
    		return FAIL;
    	}
    	Model old = commonDao.get(object.getClass(), new Integer(object.getId()));
    	if(old==null){
    		message="数据不存在!";
    		return FAIL; 
    	}
    	commonDao.save(object);
    	return SUCCESS;
    }
    
    
    public String search(){
    	Model query = getModel();
    	commonDao.find(query.getClass(), query);
    	return SUCCESS;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
