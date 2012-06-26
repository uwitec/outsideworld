package com.action;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.entity.Model;
import com.entity.Source;
public class SourceAction extends AbstractAction{
    private Source source;
    private List<Source> sources;

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public List<Source> getSources() {
		return sources;
	}

	public void setSources(List<Source> sources) {
		this.sources = sources;
	}
	
	public Model getModel(){
		return source;
	}
}
