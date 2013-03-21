package com.penguineering.virtualnotebook.model.builder;

import java.util.ArrayList;
import java.util.List;

import com.penguineering.virtualnotebook.model.Bullet;

public class BulletBuilder implements Bullet {
	private String id;
	private String type;
	private int position;
	private String content;
	private List<Bullet> bullets;

	public BulletBuilder() {
		this.bullets = new ArrayList<Bullet>();
	}

	@Override
	public String getId() {
		return id;
	}

	public BulletBuilder setId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public String getType() {
		return type;
	}

	public BulletBuilder setType(String type) {
		this.type = type;
		return this;
	}

	@Override
	public int getPosition() {
		return position;
	}

	public BulletBuilder setPosition(int position) {
		this.position = position;
		return this;
	}

	@Override
	public String getContent() {
		return content;
	}

	public BulletBuilder setContent(String content) {
		this.content = content;
		return this;
	}

	@Override
	public List<Bullet> getBullets() {
		return bullets;
	}

	public BulletBuilder addBullet(Bullet bullet) {
		this.bullets.add(bullet);
		return this;
	}
}
