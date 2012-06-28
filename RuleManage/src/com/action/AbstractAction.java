package com.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import com.dao.CommonDAO;
import com.entity.Model;
import com.util.SpringFactory;

public abstract class AbstractAction<T extends Model> {
	protected static final String SUCCESS = "success";
	protected static final String FAIL = "fail";
	protected static CommonDAO commonDao = SpringFactory.getBean("commonDAO");
	protected String message;

	protected abstract T getModel();

	protected abstract List<T> getModels();

	protected void addModel(T t) {
		List<T> result = getModels();
		if (result != null) {
			result.add(t);
		} else {
			result = new ArrayList<T>();
			result.add(t);
			setQueryResults(result);
		}
	}

	protected abstract Class<T> getModelClass();

	protected abstract void setQueryResults(List<T> results);

	public String delete() {
		T object = getModel();
		if (object == null || object.getId() == -1) {
			message = "传入数据为空!";
			return FAIL;
		}
		commonDao.delete(object);
		return SUCCESS;
	}

	public String insert() {
		T object = getModel();
		if (object == null) {
			message = "数据不存在!";
			return FAIL;
		}
		commonDao.save(object);
		addModel(object);
		return SUCCESS;
	}

	public String update() {
		T object = getModel();
		if (object == null || object.getId() == -1) {
			message = "传入数据为空!";
			return FAIL;
		}
		T old = (T) commonDao.get(object.getClass(),
				new Integer(object.getId()));
		if (old == null) {
			message = "数据不存在!";
			return FAIL;
		}
		commonDao.save(object);
		return SUCCESS;
	}

	public String search() {
		T query = getModel();
		List<T> models = (List<T>) commonDao.find(query.getClass(), query);
		setQueryResults(models);
		return SUCCESS;
	}

	public String searchAll() {
		Class<T> cla = getModelClass();
		List<T> models = (List<T>) commonDao.getAll(cla);
		setQueryResults(models);
		return SUCCESS;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	protected Map<String, String> getRequestParameterMap() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		return ctx.getExternalContext().getRequestParameterMap();
	}

	protected String getRequestParam(String name) {
		return getRequestParameterMap().get(name);
	}
}
