package com.penguineering.virtualnotebook.model.builder;

import java.util.Date;

import com.penguineering.virtualnotebook.model.Entry;

public abstract class EntryBuilder<T extends EntryBuilder<?>> extends
		LazyImmutable implements Entry {
	private int id;
	private Date timestamp;

	public EntryBuilder() {
	}

	public EntryBuilder(Entry template) {
		this.id = template.getId();
		this.timestamp = template.getTimestamp();
	}

	@Override
	public int getId() {
		revokeMutability();
		return id;
	}

	// TODO korrekt lösen
	@SuppressWarnings("unchecked")
	public T setId(int id) {
		assertMutability();
		this.id = id;
		return (T) this;
	}

	@Override
	public Date getTimestamp() {
		revokeMutability();
		return timestamp;
	}

	// TODO korrekt lösen
	@SuppressWarnings("unchecked")
	public T setTimestamp(Date timestamp) {
		assertMutability();
		this.timestamp = timestamp;
		return (T) this;
	}

}
