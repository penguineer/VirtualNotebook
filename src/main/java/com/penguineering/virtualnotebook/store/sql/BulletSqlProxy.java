package com.penguineering.virtualnotebook.store.sql;

import java.util.List;

import javax.sql.DataSource;

import com.penguineering.virtualnotebook.model.Bullet;

public class BulletSqlProxy extends SqlProxy<Bullet> implements Bullet {

	public BulletSqlProxy(int id, DataSource ds) {
		super(id, ds);
	}

	@Override
	public int getId() {
		assertDataLoaded();
		return getData().getId();
	}

	@Override
	public String getType() {
		assertDataLoaded();
		return getData().getType();
	}

	@Override
	public int getPosition() {
		assertDataLoaded();
		return getData().getPosition();
	}

	@Override
	public String getContent() {
		assertDataLoaded();
		return getData().getContent();
	}

	@Override
	public List<Bullet> getBullets() {
		assertDataLoaded();
		return getData().getBullets();
	}

	@Override
	protected Bullet load(int id, DataSource ds) {
		// TODO Auto-generated method stub
		return null;
	}

}
