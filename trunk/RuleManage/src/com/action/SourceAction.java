package com.action;

import java.util.ArrayList;
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
		sources = new ArrayList<Source>();
		Source s = new Source();
		s.setName("test");
		sources.add(s);
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

	public Source getModel() {
		return source;
	}

	@Override
	protected void setQueryResults(List<Source> results) {
		sources = results;

	}
}
