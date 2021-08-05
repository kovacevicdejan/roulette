package igra;

import igra.Polje.Status;

public class Izvlacenje implements Runnable {

	private Igra igra;
	private int brojRundi;
	private Rulet rulet;
	private Thread nit = null;
	
	public Izvlacenje(Igra igra, int brojRundi) {
		super();
		this.igra = igra;
		this.brojRundi = brojRundi;
		rulet = new Rulet(igra.mreza);
	}

	@Override
	public void run() {
		try {
			igra.mreza.zakljucaj();
			igra.zakljucaj();
			for(int i = 0; i < brojRundi; i++) {
				rulet.generisiBrojeve();
				rulet.pokreni();
				rulet.cekaj();
				Polje p = rulet.dohvatiPoslednji();
				Status prethodni = p.getStatus();
				igra.azuriraj(p.getVrednost());
				igra.istorija.dodajStavku(i + 1, p, Double.parseDouble(igra.ulog.getText()),
						Double.parseDouble(igra.dobitak.getText()));
				if(prethodni == Status.IZABRANO)
				    p.setStatus(Status.POGODJENO);
				else
					p.setStatus(Status.PROMASENO);
				Thread.sleep(1000);
				p.setStatus(prethodni);
			}
			igra.mreza.otkljucaj();
			igra.otkljucaj();
		} catch(InterruptedException e) {}
	}
	
	public synchronized void pokreni() {
		nit = new Thread(this);
		igra.istorija.isprazni();
		nit.start();
	}
	
	public int getBrojRundi() {
		return brojRundi;
	}
	
	public void setBrojRundi(int br) {
		brojRundi = br;
	}
}
