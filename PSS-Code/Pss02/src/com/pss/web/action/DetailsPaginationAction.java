package com.pss.web.action;

import java.util.Map;

import com.pss.domain.model.entity.Entity;

/**
 * 
 * @author Aries Zhao
 * @param <T>
 * 
 * @param <T>
 */
public abstract class DetailsPaginationAction<T extends Entity> extends
		PaginationAction<T> {

	private static final long serialVersionUID = 1L;

	/* 主表ID */
	private String masterId;

	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	@Override
	protected Map<String, Object> getQuery() {
		query.put("tenant", getTenantId());
		query.put("offset", getOffset());
		query.put("limit", getPageSize());
		query.put("masterId", masterId);
		return query;
	}
}
