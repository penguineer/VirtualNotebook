package com.penguineering.virtualnotebook.store.bean;

import com.penguineering.virtualnotebook.model.Bullet;
import com.penguineering.virtualnotebook.model.ModelFactory;
import com.penguineering.virtualnotebook.model.Note;

public enum BeanModelFactory implements ModelFactory {
	INSTANCE;

	@Override
	public Note createNote(Note template) {
		return new NoteBean(template);
	}

	@Override
	public Bullet createbullet(Bullet template) {
		return new BulletBean(template);
	}
}
