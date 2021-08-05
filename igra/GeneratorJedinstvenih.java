package igra;

import java.util.HashSet;

public class GeneratorJedinstvenih {

	public static HashSet<Integer> generisiJedinstvene(int n, int a, int b) {
		HashSet<Integer> niz = new HashSet<>();
		int broj;
		while (niz.size() != n) {
			broj = (int)(Math.random() * (b - a) + 1) + a;
			niz.add(broj);
		}
		return niz;
	}
	
}
