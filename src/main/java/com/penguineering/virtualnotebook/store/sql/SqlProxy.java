package com.penguineering.virtualnotebook.store.sql;

public abstract class SqlProxy<T> {
	private T data = null;

	private final String id;

	public SqlProxy(String id) {
		this.id = id;
	}

	protected T getData() {
		return data;
	}

	protected String getDatabaseId() {
		return id;
	}

	protected void assertDataLoaded() {
		if (data == null)
			data = load();

		if (getData() == null)
			throw new IllegalStateException("The data could not be loaded!");
	}

	protected abstract T load();
}
