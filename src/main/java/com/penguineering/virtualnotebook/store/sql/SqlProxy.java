package com.penguineering.virtualnotebook.store.sql;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import com.penguineering.virtualnotebook.store.DataAccessException;

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
			try {
				data = load(id, ds);
			} catch (SQLException e) {
				throw new DataAccessException("Exception during SQL access: "
						+ e.getMessage(), e);
			}

		if (getData() == null)
			throw new DataAccessException(
					"The internal data representation could not be loaded!");
	}

	protected abstract T load(int id, DataSource ds) throws SQLException,
			NoSuchElementException;
}
