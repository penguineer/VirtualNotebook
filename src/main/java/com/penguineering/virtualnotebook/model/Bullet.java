package com.penguineering.virtualnotebook.model;

import java.util.List;

import net.jcip.annotations.Immutable;

@Immutable
public interface Bullet {
	public String getId();

	public String getType();

	public int getPosition();

	public String getContent();

	public List<Bullet> getBullets();
}
