package com.penguineering.virtualnotebook.model.builder;

import java.util.Date;

import com.penguineering.virtualnotebook.model.Entry;

public abstract class EntryBuilder extends LazyImmutable implements Entry {
	private String id;
	private Date timestamp;

	public EntryBuilder() {
	}

	@Override
	public String getId() {
		revokeMutability();
		return id;
	}

	public EntryBuilder setId(String id) {
		assertMutability();
		this.id = id;
		return this;
	}

	@Override
	public Date getTimestamp() {
		revokeMutability();
		return timestamp;
	}

	public EntryBuilder setTimestamp(Date timestamp) {
		assertMutability();
		this.timestamp = timestamp;
		return this;
	}

}
