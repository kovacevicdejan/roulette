package igra;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Label;

public class Polje extends Canvas {

	enum Status{SLOBODNO, IZABRANO, OZNACENO, POGODJENO, PROMASENO};
	
	private Mreza mreza;
	private int vrednost;
	private Label broj;
	private Status status;
	
	public Polje(Mreza mreza, int vrednost) {
		super();
		this.mreza = mreza;
		this.vrednost = vrednost;
		broj = new Label(vrednost + "");
		status = Status.SLOBODNO;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
		if(status == Status.SLOBODNO)
			mreza.izbaciPoljeIzListe(this);
		else if(status == Status.IZABRANO)
			mreza.ubaciIzabranoPolje(this);
		repaint();
	}

	public int getVrednost() {
		return vrednost;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if(status == Status.IZABRANO) {
			g.setColor(Color.BLUE);
			g.fillOval(0, 0, getWidth(), getHeight());
		}
		else if(status == Status.OZNACENO) {
			g.setColor(Color.WHITE);
			g.fillOval(0, 0, getWidth(), getHeight());
		}
		else if(status == Status.POGODJENO) {
			g.setColor(Color.GREEN);
			g.fillOval(0, 0, getWidth(), getHeight());
		}
		else if(status == Status.PROMASENO) {
			g.setColor(Color.RED);
			g.fillOval(0, 0, getWidth(), getHeight());
		}
		
		String s = broj.getText();
		
		if(status == Status.IZABRANO)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.BLACK);
		centriraniTekst(g, s, new Font("Comic Sans MS", Font.BOLD,
				((getHeight() > getWidth()) ? getWidth() : getHeight()) / 3));
	}
	
	private void centriraniTekst(Graphics g, String tekst, Font font) {
	    FontMetrics metrics = g.getFontMetrics(font);
	    int x = (getWidth() - metrics.stringWidth(tekst)) / 2;
	    int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
	    g.setFont(font);
	    g.drawString(tekst, x, y);
	}
	
}
