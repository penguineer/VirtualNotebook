package com.penguineering.virtualnotebook.store.sql;

import javax.sql.DataSource;

public abstract class SqlProxy<T> {
	private T data = null;

	private final int id;
	private final DataSource ds;

	public SqlProxy(int id, DataSource ds) {
		this.id = id;
		this.ds = ds;
	}

	protected T getData() {
		return data;
	}

	protected void assertDataLoaded() {
		if (data == null)
			data = load(id, ds);

		if (getData() == null)
			throw new IllegalStateException("The data could not be loaded!");
	}

	protected abstract T load(int id, DataSource ds);
}
