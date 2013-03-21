package com.penguineering.virtualnotebook.model;

import java.util.List;

import net.jcip.annotations.Immutable;

@Immutable
public interface Note extends Entry {
	public String getTitle();
	public List<Bullet> getBullets();
}
