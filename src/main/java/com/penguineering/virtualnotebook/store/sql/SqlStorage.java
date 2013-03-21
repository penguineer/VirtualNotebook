package com.penguineering.virtualnotebook.store.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.penguineering.virtualnotebook.model.Bullet;
import com.penguineering.virtualnotebook.model.Note;
import com.penguineering.virtualnotebook.store.DataAccessException;
import com.penguineering.virtualnotebook.store.Storage;

public class SqlStorage implements Storage {
	final DataSource ds;

	public SqlStorage(DataSource ds) {
		this.ds = ds;
	}

	private void _closeCon(Connection con) {
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				throw new DataAccessException("Error closing SQL connection: "
						+ e.getMessage(), e);
			}
	}

	private void _rollbackCon(Connection con) {
		try {
			if (con != null && !con.getAutoCommit())
				con.rollback();
		} catch (SQLException e) {
			throw new DataAccessException("Error closing SQL connection: "
					+ e.getMessage(), e);
		}
	}

	@Override
	public Note persistNote(Note note) {
		final int id;
		final Note n;

		Connection con = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);

			// store entry
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO entries (timestamp, type) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, note.getTimestamp().getTime() / 1000);
			ps.setString(2, note.getType());
			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();
			if (!rs.next())
				throw new DataAccessException(
						"Could not retrieve generated key!");
			id = rs.getInt(1);
			n = new NoteSqlPoxy(id, ds);
			rs.close();
			ps.close();

			// store note
			ps = con.prepareStatement("INSERT INTO notes (entry_id, title) VALUES (?, ?)");
			ps.setInt(1, id);
			ps.setString(2, note.getTitle());
			ps.execute();
			ps.close();

			// store attached bullet points
			for (final Bullet b : note.getBullets()) {
				_bullet2note(con, id, b);
			}

			con.commit();
		} catch (SQLException e) {
			_rollbackCon(con);
			throw new DataAccessException("SQL exception on storing note: "
					+ e.getMessage(), e);
		} finally {
			_closeCon(con);
		}

		return n;
	}

	@Override
	public Bullet attachBulletToNote(Note target, Bullet bullet) {
		if (target.getId() < 1)
			throw new IllegalStateException("Target note is not persistent. "
					+ "Store the note first!");

		final int id;
		Connection con = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);

			id = _bullet2bullet(con, target.getId(), bullet);

			con.commit();
		} catch (SQLException e) {
			_rollbackCon(con);
			throw new DataAccessException("SQL exception on attaching bullet: "
					+ e.getMessage(), e);
		} finally {
			_closeCon(con);
		}

		return new BulletSqlProxy(id, ds);
	}

	private int _bullet2note(Connection con, int note, Bullet bullet)
			throws SQLException {
		final PreparedStatement ps = con.prepareStatement(
				"INSERT INTO bullets (note_id, type, position, content, marking) "
						+ "VALUES (?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, note);
		ps.setString(2, bullet.getType());
		ps.setInt(3, bullet.getPosition());
		ps.setString(4, bullet.getContent());
		Bullet.Marking marking = bullet.getMarking();
		if (marking == null)
			marking = Bullet.Marking.PENDING;
		ps.setString(5, marking.toString().toLowerCase());
		ps.execute();
		ResultSet rs = ps.getGeneratedKeys();
		if (!rs.next())
			throw new DataAccessException("Could not retrieve generated key!");
		final int id = rs.getInt(1);
		rs.close();
		ps.close();

		// store sub-bullets
		for (final Bullet b : bullet.getBullets())
			_bullet2bullet(con, id, b);

		return id;
	}

	@Override
	public Bullet attachBulletToBullet(Bullet target, Bullet bullet) {
		if (target.getId() < 1)
			throw new IllegalStateException("Target bullet is not persistent. "
					+ "Store the parent bullet first!");

		final int id;

		Connection con = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);

			id = _bullet2bullet(con, target.getId(), bullet);

			con.commit();
		} catch (SQLException e) {
			_rollbackCon(con);
			throw new DataAccessException("SQL exception on attaching bullet: "
					+ e.getMessage(), e);
		} finally {
			_closeCon(con);
		}

		return new BulletSqlProxy(id, ds);
	}

	private int _bullet2bullet(Connection con, int parent_id, Bullet bullet)
			throws SQLException {
		final PreparedStatement ps = con
				.prepareStatement(
						"INSERT INTO bullets (note_id, parent_id, type, position, content, marking) "
								+ "SELECT note_id, ?, ?, ?, ?, ? FROM bullets WHERE id=?",
						Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, parent_id);
		ps.setString(2, bullet.getType());
		ps.setInt(3, bullet.getPosition());
		ps.setString(4, bullet.getContent());
		Bullet.Marking marking = bullet.getMarking();
		if (marking == null)
			marking = Bullet.Marking.PENDING;
		ps.setString(5, marking.toString().toLowerCase());
		ps.setInt(6, parent_id);
		ps.execute();
		ResultSet rs = ps.getGeneratedKeys();
		if (!rs.next())
			throw new DataAccessException("Could not retrieve generated key!");
		final int id = rs.getInt(1);
		rs.close();
		ps.close();

		// store sub-bullets
		for (final Bullet b : bullet.getBullets())
			_bullet2bullet(con, id, b);

		return id;
	}
}
