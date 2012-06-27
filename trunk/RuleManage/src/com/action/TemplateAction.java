package com.action;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import com.entity.Template;

@ManagedBean(name="templateAction")
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
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }
}
