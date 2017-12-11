package util;

import java.util.ArrayList;
import java.util.Collections;

import automa.oggetti.Segnale;
import automa.oggetti.Valore;

public class SegnaleUtil {
	
	private SegnaleUtil() {}
	
	public static Segnale fromString(String segnale) {
		ArrayList<Valore> array = new ArrayList<>();
		for (char valore : segnale.toCharArray()) {
			switch (valore) {
				case '0':
					array.add(Valore.V0);
					break;
				case '1':
					array.add(Valore.V1);
					break;
				case '-':
					array.add(Valore.NI);
					break;
				default:
					//TODO gestione eccezioni
					throw new IllegalArgumentException("Formato segnale non valido");
			}
		}
		return new Segnale.Builder().setSegnale(array).build();
	}
	
	public static boolean segnaleDefinito(Segnale segnale) {
		for (Valore val : segnale) {
			if (val == Valore.NI) {
				return false;
			}
		}
		return true;
	}
	
	public static ArrayList<Segnale> insiemeSegnali(int dimensione) {
		ArrayList<Segnale> insiemeSegnali = new ArrayList<>((int) Math.pow(2, dimensione));
		Valore V0 = Valore.V0;
		Valore V1 = Valore.V1;
		ArrayList<Valore> valori = new ArrayList<>(Collections.<Valore>nCopies(dimensione, V0));
		
		int i = 0;
		while (i < dimensione) {
			if (valori.get(i) == V0) {
				valori.set(i, V1);
				insiemeSegnali.add(new Segnale.Builder().setSegnale(valori).build());
				i = 0;
			} else {
				valori.set(i, V0);
				i++;
			}
		}
		
		return insiemeSegnali;
	}
	
}
