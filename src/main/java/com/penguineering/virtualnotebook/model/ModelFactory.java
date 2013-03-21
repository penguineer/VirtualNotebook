package com.penguineering.virtualnotebook.model;


public interface ModelFactory {
	public Note createNote(Note template);

	public Bullet createbullet(Bullet template);
}
