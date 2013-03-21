package com.penguineering.virtualnotebook.model;

import java.util.Date;

import net.jcip.annotations.Immutable;

@Immutable
public interface Entry {
	public String getId();
	public Date getTimestamp();
	public String getType();
}
