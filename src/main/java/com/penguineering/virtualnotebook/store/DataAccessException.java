package com.penguineering.virtualnotebook.store;

public class DataAccessException extends RuntimeException {
	private static final long serialVersionUID = 5443629753227914238L;

	public DataAccessException() {
	}

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(Throwable cause) {
		super(cause);
	}

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}
}
