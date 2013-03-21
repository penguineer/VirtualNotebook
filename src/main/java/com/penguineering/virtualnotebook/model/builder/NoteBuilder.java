package com.penguineering.virtualnotebook.model.builder;

import java.util.ArrayList;
import java.util.List;

import com.penguineering.virtualnotebook.model.Bullet;
import com.penguineering.virtualnotebook.model.Note;

public class NoteBuilder extends EntryBuilder implements Note {
	private String title;
	private List<Bullet> bullets;

	public NoteBuilder() {
		this.bullets = new ArrayList<Bullet>();
	}

	@Override
	public String getType() {
		return "note";
	}

	@Override
	public String getTitle() {
		return title;
	}

	public NoteBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	@Override
	public List<Bullet> getBullets() {
		return bullets;
	}

	public NoteBuilder addBullet(Bullet bullet) {
		this.bullets.add(bullet);
		return this;
	}
}
