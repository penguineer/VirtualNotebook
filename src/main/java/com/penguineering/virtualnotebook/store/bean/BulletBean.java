package com.penguineering.virtualnotebook.store.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.jcip.annotations.Immutable;

import com.penguineering.virtualnotebook.model.Bullet;

@Immutable
public class BulletBean implements Bullet {
	private final String id;
	private final int position;
	private final String type;
	private final String content;
	private final List<Bullet> bullets;

	BulletBean(Bullet template) {
		super();
		this.id = template.getId();
		this.position = template.getPosition();
		this.type = template.getType();
		this.content = template.getContent();
		this.bullets = Collections.unmodifiableList(new ArrayList<Bullet>(
				template.getBullets()));
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public List<Bullet> getBullets() {
		return bullets;
	}
}