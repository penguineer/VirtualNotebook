package com.penguineering.virtualnotebook.store.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import com.penguineering.virtualnotebook.model.Bullet;
import com.penguineering.virtualnotebook.model.builder.BulletBuilder;
import com.penguineering.virtualnotebook.store.bean.BeanModelFactory;

public class BulletSqlProxy extends SqlProxy<Bullet> implements Bullet {

	public BulletSqlProxy(int id, DataSource ds) {
		super(id, ds);
	}

	@Override
	public int getId() {
		// do not load when just the id is needed
		return getInternalId();
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
	public Marking getMarking() {
		assertDataLoaded();
		return getData().getMarking();
	}

	@Override
	public List<Bullet> getBullets() {
		assertDataLoaded();
		return getData().getBullets();
	}

	@Override
	protected Bullet load(int id, DataSource ds) throws SQLException,
			NoSuchElementException {
		Connection con = ds.getConnection();

		final BulletBuilder bullet = new BulletBuilder();

		try {
			loadCore(con, id, bullet);
			loadChildren(ds, con, id, bullet);
		} finally {
			con.close();
		}

		return BeanModelFactory.INSTANCE.createbullet(bullet);
	}

	private void loadCore(Connection con, int id, BulletBuilder builder)
			throws SQLException {
		final PreparedStatement ps = con
				.prepareStatement("SELECT type, position, content, marking "
						+ "FROM bullets WHERE id=?");
		ps.setInt(1, id);

		final ResultSet rs = ps.executeQuery();

		if (!rs.next())
			throw new NoSuchElementException("A bullet with id " + id
					+ " could not be found!");

		builder.setId(id);
		builder.setType(rs.getString(1));
		builder.setPosition(rs.getInt(2));
		builder.setContent(rs.getString(3));
		builder.setMarking(Bullet.Marking
				.valueOf(rs.getString(4).toUpperCase()));

		rs.close();
		ps.close();
	}

	private void loadChildren(DataSource ds, Connection con, int id,
			BulletBuilder builder) throws SQLException {
		final PreparedStatement ps = con.prepareStatement("SELECT id "
				+ "FROM bullets WHERE parent_id=?");
		ps.setInt(1, id);

		final ResultSet rs = ps.executeQuery();

		while (rs.next())
			builder.addBullet(new BulletSqlProxy(rs.getInt(1), ds));

		rs.close();
		ps.close();
	}
}
