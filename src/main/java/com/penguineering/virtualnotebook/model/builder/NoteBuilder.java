package com.penguineering.virtualnotebook.model.builder;

import java.util.ArrayList;
import java.util.Collections;
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
		revokeMutability();
		return "note";
	}

	@Override
	public String getTitle() {
		revokeMutability();
		return title;
	}

	public NoteBuilder setTitle(String title) {
		revokeMutability();
		this.title = title;
		return this;
	}

	@Override
	public List<Bullet> getBullets() {
		revokeMutability();
		return Collections.unmodifiableList(bullets);
	}

	public NoteBuilder addBullet(Bullet bullet) {
		revokeMutability();
		this.bullets.add(bullet);
		return this;
	}
}
