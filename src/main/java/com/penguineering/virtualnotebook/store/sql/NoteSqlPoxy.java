package com.penguineering.virtualnotebook.store.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import com.penguineering.virtualnotebook.model.Bullet;
import com.penguineering.virtualnotebook.model.Note;
import com.penguineering.virtualnotebook.model.builder.NoteBuilder;
import com.penguineering.virtualnotebook.store.bean.BeanModelFactory;

public class NoteSqlPoxy extends SqlProxy<Note> implements Note {

	public NoteSqlPoxy(int id, DataSource ds) {
		super(id, ds);
	}

	@Override
	public int getId() {
		// do not load when just the id is needed
		return getInternalId();
	}

	@Override
	public Date getTimestamp() {
		assertDataLoaded();
		return getData().getTimestamp();
	}

	@Override
	public String getType() {
		assertDataLoaded();
		return getData().getType();
	}

	@Override
	public String getTitle() {
		assertDataLoaded();
		return getData().getTitle();
	}

	@Override
	public List<Bullet> getBullets() {
		assertDataLoaded();
		return getData().getBullets();
	}

	@Override
	protected Note load(int id, DataSource ds) throws SQLException,
			NoSuchElementException {
		Connection con = ds.getConnection();

		final NoteBuilder note = new NoteBuilder();

		try {
			loadCore(con, id, note);
			loadBullets(ds, con, id, note);
		} finally {
			con.close();
		}

		return BeanModelFactory.INSTANCE.createNote(note);
	}

	private void loadCore(Connection con, int id, NoteBuilder builder)
			throws SQLException {
		// TODO das ist eigentlich ein JOIN
		final PreparedStatement ps = con
				.prepareStatement("SELECT entries.timestamp, notes.title "
						+ "FROM entries, notes "
						+ "WHERE entries.id=? AND notes.entry_id=entries.id AND entries.type='note'");
		ps.setInt(1, id);

		final ResultSet rs = ps.executeQuery();

		if (!rs.next())
			throw new NoSuchElementException("A bullet with id " + id
					+ " could not be found!");

		builder.setId(id);
		builder.setTimestamp(new Date(rs.getLong(1)*1000));
		builder.setTitle(rs.getString(2));

		rs.close();
		ps.close();
	}

	private void loadBullets(DataSource ds, Connection con, int id,
			NoteBuilder builder) throws SQLException {
		final PreparedStatement ps = con.prepareStatement("SELECT id "
				+ "FROM bullets WHERE note_id=? AND parent_id IS NULL");
		ps.setInt(1, id);

		final ResultSet rs = ps.executeQuery();

		while (rs.next())
			builder.addBullet(new BulletSqlProxy(rs.getInt(1), ds));

		rs.close();
		ps.close();

	}

}
