package com.penguineering.virtualnotebook.model.builder;

import java.util.Date;

import com.penguineering.virtualnotebook.model.Entry;

public abstract class EntryBuilder extends LazyImmutable implements Entry {
	private int id;
	private Date timestamp;

	public EntryBuilder() {
	}

	@Override
	public int getId() {
		revokeMutability();
		return id;
	}

	public EntryBuilder setId(int id) {
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
