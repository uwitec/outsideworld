package com.action;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.entity.Source;

@ManagedBean(name = "sourceAction")
@SessionScoped
public class SourceAction extends AbstractAction<Source> {
	@ManagedProperty(value = "#{source}")
	private Source source;
	@ManagedProperty(value = "#{sources}")
	private List<Source> sources;
	

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public List<Source> getSources() {
	    if(sources==null||sources.size()==0){
	        searchAll();
	    }
		return sources;
	}

	public void setSources(List<Source> sources) {
		this.sources = sources;
	}

	public Source getModel() {
		return source;
	}

	@Override
	protected void setQueryResults(List<Source> results) {
		sources = results;
	}

    @Override
    protected Class<Source> getModelClass() {
        // TODO Auto-generated method stub
        return Source.class;
    }

    @Override
    protected List<Source> getModels() {
        return sources;
    }
}
