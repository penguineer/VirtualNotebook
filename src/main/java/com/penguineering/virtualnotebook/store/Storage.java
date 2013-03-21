package com.penguineering.virtualnotebook.store;

import com.penguineering.virtualnotebook.model.Bullet;
import com.penguineering.virtualnotebook.model.Note;

public interface Storage {
	public Note persistNote(Note note);

	public Bullet attachBulletToNote(Note target, Bullet bullet);

	public Bullet attachBulletToBullet(Bullet target, Bullet bullet);
}
