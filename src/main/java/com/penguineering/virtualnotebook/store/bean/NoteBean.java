package com.penguineering.virtualnotebook.store.bean;

import java.util.List;

import net.jcip.annotations.Immutable;

import com.penguineering.virtualnotebook.model.Bullet;
import com.penguineering.virtualnotebook.model.Note;

@Immutable
public class NoteBean extends EntryBean implements Note {
	private final String title;
	private final List<Bullet> bullets;

	NoteBean(Note template) {
		super(template);
		this.title = template.getTitle();
		this.bullets = template.getBullets();
	}

	@Override
	public String getType() {
		return "note";
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public List<Bullet> getBullets() {
		return this.bullets;
	}

}
