package igra;

import java.util.HashSet;

public class Generator {

	public static int generisiSlucajanBroj(int a, int b) {
		return (int)(Math.random() * (b - a) + 1) + a;
	}
	
	public static Integer[] generisiNiz(int n, int a, int b) {
		Integer[] niz = new Integer[n];
		int broj;
		for(int i = 0; i < n; i++) {
			broj = (int)(Math.random() * (b - a) + 1) + a;
			niz[i] = broj;
		}
		return niz;
	}
	
}
