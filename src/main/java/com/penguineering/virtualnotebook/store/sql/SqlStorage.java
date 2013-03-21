package com.penguineering.virtualnotebook.store.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.penguineering.virtualnotebook.model.Bullet;
import com.penguineering.virtualnotebook.model.Note;
import com.penguineering.virtualnotebook.model.builder.BulletBuilder;
import com.penguineering.virtualnotebook.model.builder.NoteBuilder;
import com.penguineering.virtualnotebook.store.DataAccessException;
import com.penguineering.virtualnotebook.store.Storage;
import com.penguineering.virtualnotebook.store.bean.BeanModelFactory;

public class SqlStorage implements Storage {
	final DataSource ds;

	public SqlStorage(DataSource ds) {
		this.ds = ds;
	}

	private void closeCon(Connection con) {
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				throw new DataAccessException("Error closing SQL connection: "
						+ e.getMessage(), e);
			}
	}

	private void rollbackCon(Connection con) {
		if (con != null)
			try {
				con.rollback();
			} catch (SQLException e) {
				throw new DataAccessException("Error closing SQL connection: "
						+ e.getMessage(), e);
			}
	}

	@Override
	public Note persistNote(Note note) {
		final NoteBuilder builder = new NoteBuilder(note);

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
			final int id = rs.getInt(1);
			builder.setId(id);
			rs.close();
			ps.close();

			// store note
			ps = con.prepareStatement("INSERT INTO notes (entry_id, title) VALUES (?, ?)");
			ps.setInt(1, id);
			ps.setString(2, note.getTitle());
			ps.execute();
			ps.close();

			con.commit();
		} catch (SQLException e) {
			rollbackCon(con);
			throw new DataAccessException("SQL exception on storing note: "
					+ e.getMessage(), e);
		} finally {
			closeCon(con);
		}

		return BeanModelFactory.INSTANCE.createNote(builder);
	}

	@Override
	public Bullet attachBulletToNote(Note target, Bullet bullet) {
		if (target.getId() < 1)
			throw new IllegalStateException("Target note is not persistent. "
					+ "Store the note first!");

		BulletBuilder builder = new BulletBuilder(bullet);

		Connection con = null;

		try {
			con = ds.getConnection();

			final PreparedStatement ps = con.prepareStatement(
					"INSERT INTO bullets (note_id, type, position, content, marking) "
							+ "VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, target.getId());
			ps.setString(2, bullet.getType());
			ps.setInt(3, bullet.getPosition());
			ps.setString(4, bullet.getContent());
			Bullet.Marking marking = bullet.getMarking();
			if (marking == null)
				marking = Bullet.Marking.PENDING;
			builder.setMarking(marking);
			ps.setString(5, marking.toString().toLowerCase());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (!rs.next())
				throw new DataAccessException(
						"Could not retrieve generated key!");
			final int id = rs.getInt(1);
			builder.setId(id);
			rs.close();
			ps.close();

		} catch (SQLException e) {
			rollbackCon(con);
			throw new DataAccessException("SQL exception on attaching bullet: "
					+ e.getMessage(), e);
		} finally {
			closeCon(con);
		}

		return BeanModelFactory.INSTANCE.createbullet(builder);
	}

	@Override
	public Bullet attachBulletToBullet(Bullet target, Bullet bullet) {
		// TODO Auto-generated method stub
		return bullet;
	}
}
