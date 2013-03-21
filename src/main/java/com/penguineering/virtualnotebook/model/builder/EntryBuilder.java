package com.penguineering.virtualnotebook.model.builder;

import java.util.Date;

import com.penguineering.virtualnotebook.model.Entry;

public abstract class EntryBuilder implements Entry {
	private String id;
	private Date timestamp;

	public EntryBuilder() {
	}

	@Override
	public String getId() {
		return id;
	}

	public EntryBuilder setId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}

	public EntryBuilder setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
		return this;
	}

}
