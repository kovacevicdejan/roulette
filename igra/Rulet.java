package igra;

import igra.Polje.Status;

public class Rulet implements Runnable {

	private Mreza mreza;
	private Integer[] brojevi;
	private int n;
	private Thread nit = null;

	public Rulet(Mreza mreza) {
		super();
		this.mreza = mreza;
	}
	
	public synchronized void generisiBrojeve() {
		n = Generator.generisiSlucajanBroj((int)(mreza.brojPolja() * 0.3),
				(int)(mreza.brojPolja() * 0.7));
		brojevi = Generator.generisiNiz(n, 0, mreza.brojPolja() - 1);
	}

	@Override
	public void run() {
		int interval = 200;
		int k, j;
		try {
			for(int i = 0; i < n - 1; i++) {
			    k = brojevi[i] / mreza.getBrojKolona();
			    j = brojevi[i] % mreza.getBrojKolona();
			    Polje p = mreza.uzmiPolje(k, j);
			    Status prethodni = p.getStatus();
			    p.setStatus(Status.OZNACENO);
			    Thread.sleep(interval);
			    p.setStatus(prethodni);
			    interval = (int)(interval * 1.1);
			}
		} catch(InterruptedException e) {}
	}
	
	public synchronized Polje dohvatiPoslednji() {
		int k = brojevi[n - 1] / mreza.getBrojKolona();
	    int j = brojevi[n - 1] % mreza.getBrojKolona();
		return mreza.uzmiPolje(k, j);
	}
	
	public synchronized void pokreni() {
		nit = new Thread(this);
		nit.start();
	}
	
	public void cekaj() throws InterruptedException{
		nit.join();
	}
	
}
