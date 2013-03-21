package com.penguineering.virtualnotebook.store.bean;

import java.util.Date;

import com.penguineering.virtualnotebook.model.Entry;

public abstract class EntryBean implements Entry {

	private final String id;
	private final Date timestamp;

	EntryBean(Entry template) {
		this.id = template.getId();
		this.timestamp = template.getTimestamp();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}
}
