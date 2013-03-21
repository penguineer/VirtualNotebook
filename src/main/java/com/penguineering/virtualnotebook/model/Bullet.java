package com.penguineering.virtualnotebook.model;

import java.util.List;

import net.jcip.annotations.Immutable;

@Immutable
public interface Bullet {
	public enum Marking {
		PENDING, DONE, ABANDONED
	}
	public int getId();

	public String getType();

	public int getPosition();

	public String getContent();
	
	public Marking getMarking();

	public List<Bullet> getBullets();
}
