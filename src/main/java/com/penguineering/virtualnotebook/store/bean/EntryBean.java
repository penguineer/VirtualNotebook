package com.penguineering.virtualnotebook.store.bean;

import java.util.Date;

import com.penguineering.virtualnotebook.model.Entry;

public abstract class EntryBean implements Entry {

	private final int id;
	private final Date timestamp;

	EntryBean(Entry template) {
		this.id = template.getId();
		this.timestamp = template.getTimestamp();
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}
}
