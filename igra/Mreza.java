package igra;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;

import igra.Polje.Status;

public class Mreza extends Panel {
    
	private ArrayList<Polje> izabranaPolja = new ArrayList<Polje>();
	private Igra igra;
	private int brojVrsta = 4;
	private int brojKolona = 5;
	private boolean moze = true;
	private Polje[][] polja;
	
	public Mreza(Igra igra) {
		super();
		this.igra = igra;
		polja = new Polje[4][5];
		ubaciPolja(brojVrsta, brojKolona);
	}
	
	public Mreza(int brojVrsta, int brojKolona, Igra igra) {
		super();
		this.igra = igra;
		this.brojVrsta = brojVrsta;
		this.brojKolona = brojKolona;
		polja = new Polje[brojVrsta][brojKolona];
		ubaciPolja(brojVrsta, brojKolona);
	}
	
	public void ubaciPolja(int brojVrsta, int brojkolona) {
		int brojac = 0;
		this.setLayout(new GridLayout(brojVrsta, brojKolona, 3, 3));
		this.setBackground(Color.BLACK);
		for(int i = 0; i < brojVrsta; i++) {
			for(int j = 0; j < brojKolona; j++) {
				Polje polje = new Polje(this, brojac++);
				polja[i][j] = polje;
				polje.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						if(moze) {
							if(polje.getStatus() == Polje.Status.SLOBODNO)
							    polje.setStatus(Polje.Status.IZABRANO);
							else if(polje.getStatus() == Polje.Status.IZABRANO)
							    polje.setStatus(Polje.Status.SLOBODNO);
						}
					}
					
				});
			    add(polje);
			    revalidate();
			}
		}
	}
	
	public void ubaciIzabranoPolje(Polje p) {
		if(!izabranaPolja.contains(p))
		    izabranaPolja.add(p);
		igra.kvota.setText(String.format("%.2f", brojVrsta * brojKolona * 1.0 / izabranaPolja.size()));
		igra.dobitak.setText(String.format("%.2f", 
				Double.parseDouble(igra.kvota.getText()) * Double.parseDouble(igra.ulog.getText())));
		revalidate();
		if(izabranaPolja.size() == 1)
			igra.igraj.setEnabled(true);
	}
	
	public void izbaciPoljeIzListe(Polje p) {
		izabranaPolja.remove(p);
		if(izabranaPolja.isEmpty()) {
			igra.kvota.setText("0.00");
			igra.igraj.setEnabled(false);
		}
		else
			igra.kvota.setText(String.format("%.2f", brojVrsta * brojKolona * 1.0 / izabranaPolja.size()));
		igra.dobitak.setText(String.format("%.2f", 
				Double.parseDouble(igra.kvota.getText()) * Double.parseDouble(igra.ulog.getText())));
		revalidate();
	}
	
	public HashSet<Integer> skupIzabranih() {
		HashSet<Integer> skup = new HashSet<Integer>();
		for(Polje p: izabranaPolja) {
			skup.add(p.getVrednost());
		}
		return skup;
	}
	
	public int brojPolja() {
		return brojVrsta * brojKolona;
	}
	
	public void zakljucaj() {
		moze = false;
	}
	
	public void otkljucaj() {
		moze = true;
	}
	
	public void brisi() {
		for(int i = 0; i < brojVrsta; i++) {
			for(int j = 0; j < brojKolona; j++) {
				polja[i][j].setStatus(Status.SLOBODNO);
			}
		}
	}
	
	public void izaberiParne() {
		for(int i = 0; i < brojVrsta; i++) {
			for(int j = 0; j < brojKolona; j++) {
				if(polja[i][j].getVrednost() % 2 == 0)
				    polja[i][j].setStatus(Status.IZABRANO);
				else
					polja[i][j].setStatus(Status.SLOBODNO);
			}
		}
	}
	
	public void izaberiNeparne() {
		for(int i = 0; i < brojVrsta; i++) {
			for(int j = 0; j < brojKolona; j++) {
				if(polja[i][j].getVrednost() % 2 == 1)
				    polja[i][j].setStatus(Status.IZABRANO);
				else
					polja[i][j].setStatus(Status.SLOBODNO);
			}
		}
	}
	
	public void izaberiIzListe(ArrayList<Integer> lista) {
		for(int i = 0; i < brojVrsta; i++) {
			for(int j = 0; j < brojKolona; j++) {
				if(lista.contains(polja[i][j].getVrednost()))
				    polja[i][j].setStatus(Status.IZABRANO);
				else
					polja[i][j].setStatus(Status.SLOBODNO);
			}
		}
	}
	
	public Polje uzmiPolje(int i, int j) {
		return polja[i][j];
	}
	
	public int getBrojKolona() {
		return brojKolona;
	}
	
}
