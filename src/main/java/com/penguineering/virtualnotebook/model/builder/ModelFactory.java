package com.penguineering.virtualnotebook.model.builder;

import com.penguineering.virtualnotebook.model.Bullet;
import com.penguineering.virtualnotebook.model.Note;

public interface ModelFactory {
	public Note createNote(Note template);

	public Bullet createbullet(Bullet template);
}
