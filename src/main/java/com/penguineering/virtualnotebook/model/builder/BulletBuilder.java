package com.penguineering.virtualnotebook.model.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.penguineering.virtualnotebook.model.Bullet;

public class BulletBuilder extends LazyImmutable implements Bullet {
	private int id;
	private String type;
	private int position;
	private String content;
	private Marking marking;
	private List<Bullet> bullets;

	public BulletBuilder() {
		this.bullets = new ArrayList<Bullet>();
	}

	@Override
	public int getId() {
		revokeMutability();
		return id;
	}

	public BulletBuilder setId(int id) {
		assertMutability();
		this.id = id;
		return this;
	}

	@Override
	public String getType() {
		revokeMutability();
		return type;
	}

	public BulletBuilder setType(String type) {
		assertMutability();
		this.type = type;
		return this;
	}

	@Override
	public int getPosition() {
		revokeMutability();
		return position;
	}

	public BulletBuilder setPosition(int position) {
		assertMutability();
		this.position = position;
		return this;
	}

	@Override
	public String getContent() {
		revokeMutability();
		return content;
	}

	public BulletBuilder setContent(String content) {
		assertMutability();
		this.content = content;
		return this;
	}

	@Override
	public Marking getMarking() {
		revokeMutability();
		return this.marking;
	}

	public BulletBuilder setMarking(Marking marking) {
		assertMutability();
		this.marking = marking;
		return this;
	}

	@Override
	public List<Bullet> getBullets() {
		revokeMutability();
		return Collections.unmodifiableList(bullets);
	}

	public BulletBuilder addBullet(Bullet bullet) {
		assertMutability();
		this.bullets.add(bullet);
		return this;
	}
}
