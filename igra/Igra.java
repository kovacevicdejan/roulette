package igra;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashSet;

public class Igra extends Frame {
	
	private Label broj = new Label();
	private Label balans = new Label("0");
	Label kvota = new Label("0.00");
	Label dobitak = new Label("0.00");
	TextField ulog = new TextField(2);
    Button igraj = new Button("Play");
    TextField brojRundi = new TextField(1);
	Mreza mreza;
	private Panel statusnaTraka = new Panel();
	Izvlacenje izvlacenje;
	private int moze = 1;
	Istorija istorija = new Istorija(3);

	public Igra() {
		setBounds(400, 150, 600, 400);
		setResizable(true);
		
		setTitle("Roulette");
		
		namestiProzor();
		pack();
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		setVisible(true);
	}
	
	public void namestiProzor() {
		statusnaTraka.setBackground(Color.GRAY);
		broj.setFont(new Font("Arial", Font.BOLD, 20));
		statusnaTraka.add(broj);
		add(statusnaTraka, BorderLayout.SOUTH);
		
		MenuItem par = new MenuItem("Even");
		MenuItem nepar = new MenuItem("Odd");
		MenuItem pola = new MenuItem("Half");
		MenuItem brisi = new MenuItem("Delete");
		
		Menu izbor = new Menu("Choise");
		
		par.addActionListener((ae) -> {
			if(moze == 1)
			    mreza.izaberiParne();
		});
		izbor.add(par);
		
		nepar.addActionListener((ae) -> {
			if(moze == 1)
			    mreza.izaberiNeparne();
		});
		izbor.add(nepar);
		
		pola.addActionListener((ae) -> {
			if(moze == 1) {
				ArrayList<Integer> lista = new ArrayList<>();
				HashSet<Integer> set = 
						GeneratorJedinstvenih.generisiJedinstvene(mreza.brojPolja() / 2, 0, mreza.brojPolja() - 1);
				for(int br: set) {
					lista.add(br);
				}
				mreza.izaberiIzListe(lista);
			}
		});
		izbor.add(pola);
		
		brisi.addActionListener((ae) -> {
			if(moze == 1)
			    mreza.brisi();
		});
		izbor.add(brisi);
		
		MenuBar bar = new MenuBar();
		bar.add(izbor);
		setMenuBar(bar);
		
		Panel upravljackiPanel = new Panel(new GridLayout(6, 1));
		
		Panel prvi = new Panel(new FlowLayout(FlowLayout.LEFT));
		Label b = new Label("Balance: ");
		prvi.add(b);
		prvi.add(balans);
		upravljackiPanel.add(prvi);
		
		Panel drugi = new Panel(new FlowLayout(FlowLayout.LEFT));
		Label u = new Label("Stake: ");
		drugi.add(u);
		ulog.setText("100");
		ulog.addTextListener((tl) -> {
			if(ulog.getText().isBlank())
				dobitak.setText("0.00");
			else
			    dobitak.setText(String.format("%.2f", 
					    Double.parseDouble(kvota.getText()) * Double.parseDouble(ulog.getText())));
			revalidate();
		});
		drugi.add(ulog);
		upravljackiPanel.add(drugi);
		
		Panel dodatni = new Panel(new FlowLayout(FlowLayout.LEFT));
		Label br = new Label("Number of rounds: ");
		dodatni.add(br);
		brojRundi.setText("4");
		brojRundi.addTextListener((tl) -> {
			izvlacenje.setBrojRundi(Integer.parseInt(brojRundi.getText()));
		});
		dodatni.add(brojRundi);
		upravljackiPanel.add(dodatni);
		
		Panel treci = new Panel(new FlowLayout(FlowLayout.LEFT));
		Label k = new Label("Odds: ");
		treci.add(k);
		treci.add(kvota);
		upravljackiPanel.add(treci);
		
		Panel cetvrti = new Panel(new FlowLayout(FlowLayout.LEFT));
		Label d = new Label("Prize: ");
		cetvrti.add(d);
		cetvrti.add(dobitak);
		upravljackiPanel.add(cetvrti);
		
		Panel peti = new Panel(new FlowLayout(FlowLayout.RIGHT));
		igraj.setEnabled(false);
		igraj.addActionListener((ae) -> {
			igraj();
		});
		peti.add(igraj);
		upravljackiPanel.add(peti);
		
		upravljackiPanel.setBackground(Color.LIGHT_GRAY);
		add(upravljackiPanel, BorderLayout.EAST);
		
		mreza = new Mreza(this);
		mreza.setPreferredSize(new Dimension(375, 300));
		add(mreza, BorderLayout.CENTER);
		
		izvlacenje = new Izvlacenje(this, Integer.parseInt(brojRundi.getText()));
		
		istorija.setPreferredSize(new Dimension(100, 300));
		add(istorija, BorderLayout.WEST);
	}
	
	private void igraj() {
		izvlacenje.pokreni();
	}
	
	public void azuriraj(int slucajan) {
		HashSet<Integer> skup = mreza.skupIzabranih();
		if(skup.contains(slucajan)) {
			broj.setText(slucajan + "");
			broj.setBackground(Color.GREEN);
			statusnaTraka.setBackground(Color.GREEN);
			balans.setText(String.format("%.2f", Double.parseDouble(balans.getText()) +
					Double.parseDouble(dobitak.getText()) - Double.parseDouble(ulog.getText())));
			revalidate();
		}
		else {
			broj.setText(slucajan + "");
			broj.setBackground(Color.RED);
			statusnaTraka.setBackground(Color.RED);
			balans.setText(String.format("%.2f", Double.parseDouble(balans.getText())
					- Double.parseDouble(ulog.getText())));
			revalidate();
		}
	}
	
	public void zakljucaj() {
		moze = 0;
	}
	
	public void otkljucaj() {
		moze = 1;
	}
	
	public static void main(String[] args) {
		new Igra();
	}

}
