package com.action;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.entity.Element;
import com.entity.Source;
import com.entity.Template;

@ManagedBean(name = "templateAction")
@SessionScoped
public class TemplateAction extends AbstractAction<Template> {

	@ManagedProperty(value = "#{template}")
	private Template template;
	@ManagedProperty(value = "#{templates}")
	private List<Template> templates;
	private Element element;
	@ManagedProperty(value = "#{sourceId}")
	private Integer sourceId;

	private List<Source> sources;

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
	public String insert() {
		Source source = commonDao.get(Source.class, sourceId);
		template.setSource(source);
		if (template.getId() < 0) {
			return super.insert();
		} else {
			return super.update();
		}
	}

	@Override
	public void selectById() {
		String id = getRequestParam("id");
		template = commonDao.get(getModelClass(), Integer.parseInt(id));
		setModel(template);
		sourceId = template.getSource().getId();
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
			if (template != null) {
				template.getElements().add(element);
				update();
			}
			// commonDao.save(element);
			// setModel(null);
		}
	}

	public void createElement() {
		element = new Element();
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public List<Source> getSources() {
		sources = commonDao.getAll(Source.class);
		return sources;
	}

	public void setSources(List<Source> sources) {
		this.sources = sources;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
}
