package com.action;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import com.entity.Template;

@ManagedBean(name = "templateAction")
@SessionScoped
public class TemplateAction extends AbstractAction<Template> {
	@ManagedProperty(value = "#{template}")
	private Template template;
	@ManagedProperty(value = "#{templates}")
	private List<Template> templates;

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

	public void create() {
		template = new Template();
	}

	public void save() {
		if (template.getId() > 0) {
			commonDao.update(template);
		} else {
			commonDao.save(template);
			template = null;
		}
	}

	public void cancel() {
		template = null;
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

	public void select() {
		String templateId = getRequestParam("templateId");
		template = commonDao.get(Template.class, Integer.parseInt(templateId));
	}
}
