package igra;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.util.ArrayList;

import igra.Polje.Status;

public class Istorija extends Panel {

	private ArrayList<TextArea> stavke = new ArrayList<>();
	private int kapacitet;
	private int brojac = 0;
	
	public Istorija(int kapacitet) {
		super();
		this.kapacitet = kapacitet;
		setLayout(new GridLayout(kapacitet, 1));
	}
	
	public void dodajStavku(int runda, Polje p, double ulog, double dobitak) {
		TextArea novi = new TextArea("", 1, 4, TextArea.SCROLLBARS_NONE);
		novi.append("Round: " + runda + "\n");
		novi.append("Number: " + p.getVrednost() + "\n");
		novi.append("S: " + ulog + "\n");
		novi.append("P: " + dobitak + "\n");
		if(p.getStatus() == Status.IZABRANO)
			novi.setBackground(Color.GREEN);
		else
			novi.setBackground(Color.RED);
		brojac++;
		stavke.add(novi);
		if(brojac <= kapacitet)
			add(novi);
		else {
			stavke.remove(0);
			removeAll();
			for(TextArea t: stavke) {
				add(t);
			}
		}
		revalidate();
	}
	
	public void isprazni() {
		removeAll();
	    stavke.clear();
	    brojac = 0;
	}
	
}
