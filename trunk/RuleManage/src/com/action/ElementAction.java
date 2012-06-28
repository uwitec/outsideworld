package com.action;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import com.entity.Element;

@ManagedBean(name="elementAction")
@SessionScoped
public class ElementAction extends AbstractAction<Element> {
    @ManagedProperty(value = "#{element}")
    private Element element;
    @ManagedProperty(value = "#{elements}")
    private List<Element> elements;

    @Override
    protected Element getModel() {
        return element;
    }

    @Override
    protected void setQueryResults(List<Element> results) {
        elements = results;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    @Override
    protected Class<Element> getModelClass() {
        // TODO Auto-generated method stub
        return Element.class;
    }
}
