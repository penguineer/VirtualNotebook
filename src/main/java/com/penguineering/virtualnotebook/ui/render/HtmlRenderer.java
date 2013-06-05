package com.penguineering.virtualnotebook.ui.render;

import java.text.SimpleDateFormat;
import java.util.List;

import com.penguineering.virtualnotebook.model.Bullet;
import com.penguineering.virtualnotebook.model.Note;

public class HtmlRenderer {
	protected static final SimpleDateFormat sdf;

	static {
		sdf = new SimpleDateFormat("yyyy-MM-dd");
	}

	public HtmlRenderer() {
		// TODO Auto-generated constructor stub
	}

	public String renderNote(Note note) {
		final StringBuilder html = new StringBuilder();

		// day header
		// TODO only when first occurrence of date
		html.append("<h1 class=\"day\">");
		html.append(sdf.format(note.getTimestamp()));
		html.append("</h1>\n");

		// note header
		html.append("<article>\n");
		html.append("<h1 class=\"entry\">");
		html.append(note.getTitle());
		html.append("</h1>");

		// bullets
		addBullets(note.getBullets(), html);

		html.append("</article>\n");

		return html.toString();
	}

	private void addBullets(List<Bullet> bullets, StringBuilder html) {
		if (bullets.isEmpty())
			return;

		html.append("<ul>\n");
		for (final Bullet bullet : bullets)
			renderBullet(bullet, html);
		html.append("</ul>\n");
	}

	private void renderBullet(Bullet bullet, StringBuilder html) {
		html.append("<li");

		if (!"text".equals(bullet.getType())) {
			html.append(" class=\"");
			html.append(bullet.getMarking().name().toLowerCase());

			html.append("\"><span class=\"marking\">");
			html.append(bullet.getType());
			html.append("</span");
		}
		html.append(">\n");
		html.append(bullet.getContent());
		html.append("\n");
		addBullets(bullet.getBullets(), html);
		html.append("</li>\n");

	}
}
