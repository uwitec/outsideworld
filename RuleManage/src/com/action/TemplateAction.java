package com.action;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.entity.Element;
import com.entity.Template;

@ManagedBean(name = "templateAction")
@SessionScoped
public class TemplateAction extends AbstractAction<Template> {
	@ManagedProperty(value = "#{template}")
	private Template template;
	@ManagedProperty(value = "#{templates}")
	private List<Template> templates;

	private Element element;

	@Override
	protected Template getModel() {
		return template;
	}

	@Override
	protected void setQueryResults(List<Template> results) {
		templates = results;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public List<Template> getTemplates() {
		templates = commonDao.getAll(Template.class);
		return templates;
	}

	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}

	@Override
	protected Class<Template> getModelClass() {
		// TODO Auto-generated method stub
		return Template.class;
	}

	@Override
	protected List<Template> getModels() {
		// TODO Auto-generated method stub
		return templates;
	}

	@Override
	protected void setModel(Template t) {
		template = t;
	}

	@Override
	public void selectById() {
		String id = getRequestParam("id");
		template = commonDao.get(getModelClass(), Integer.parseInt(id));
		setModel(template);
		element = null;
	}

	public void selectElement() {
		String id = getRequestParam("id");
		element = commonDao.get(Element.class, Integer.parseInt(id));
	}

	public void saveElement() {
		if (element.getId() > 0) {
			commonDao.update(element);
		} else {
			commonDao.save(element);
			setModel(null);
		}
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}
}
