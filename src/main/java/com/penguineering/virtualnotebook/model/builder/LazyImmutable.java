package com.penguineering.virtualnotebook.model.builder;

import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class LazyImmutable {
	private AtomicBoolean isMutable = new AtomicBoolean(true);

	protected final void assertMutability() {
		if (!isMutable.get())
			throw new ConcurrentModificationException(
					"Builder has been read from and therefore is not mutable anymore!");
	}

	protected final void revokeMutability() {
		isMutable.set(false);
	}

	public boolean isMutable() {
		return isMutable.get();
	}
}
