package automa.oggetti;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class Segnale implements Iterable<Valore>, Comparable<Segnale> {
	
	private final ArrayList<Valore> segnale;
	public final int indefiniti;
	
	public Segnale(Collection<Valore> segnale) {
		int indefiniti = 0;
		for (Valore valore : segnale) {
			if (valore == Valore.NI) {
				indefiniti++;
			}
		}
		this.segnale = new ArrayList<>(segnale);
		this.indefiniti = indefiniti;
		
	}
	
	public Segnale(Valore... segnale) {
		this.segnale = new ArrayList<>();
		int indefiniti = 0;
		for (Valore valore : segnale) {
			if (valore == Valore.NI) {
				indefiniti++;
			}
			this.segnale.add(valore);
		}
		this.indefiniti = indefiniti;
	}
	
	public int size() {
		return segnale.size();
	}
	
	public Valore getValore(int index) {
		if (index < 0 || index >= segnale.size()) {
			throw new IndexOutOfBoundsException();
		}
		return segnale.get(index);
	}
	
	public static Segnale String2Sig(String segnale) {
		if (!segnale.matches("[01\\-]*")) {
			throw new IllegalArgumentException();
		}
		LinkedList<Valore> valori = new LinkedList<>();
		for (int i=0;i<segnale.length();i++) {
			switch (segnale.charAt(i)) {
			case '0' :
				valori.add(Valore.V0);
				break;
			case '1' :
				valori.add(Valore.V1);
				break;
			default :
				valori.add(Valore.NI);
				break;
			}
		}
		return new Segnale(valori);
	}
	
	public Segnale set(int index, Valore valore) {
		if (index < 0 || index >= segnale.size()) {
			throw new IndexOutOfBoundsException();
		}
		ArrayList<Valore> newSign = new ArrayList<>(this.segnale);
		newSign.set(index, valore);
		return new Segnale(newSign);
	}
	
	@Override
	public Iterator<Valore> iterator() {
		return segnale.iterator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + indefiniti;
		result = prime * result + ((segnale == null) ? 0 : segnale.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Segnale other = (Segnale) obj;
		if (indefiniti != other.indefiniti)
			return false;
		if (segnale == null) {
			if (other.segnale != null)
				return false;
		} else if (!segnale.equals(other.segnale))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(size());
		for (Valore valore : segnale) {
			sb.append(valore.valore);
		}
		return sb.toString();
	}
	
	@Override
	public int compareTo(Segnale s) {
		for (int i=0;i<segnale.size();i++) {
			if (!segnale.get(i).equals(s.segnale.get(i))) {
				if (segnale.get(i) == Valore.NI) {
					return 1;
				} else if (s.segnale.get(i) == Valore.NI) {
					return -1;
				} else if (segnale.get(i) == Valore.V1) {
					return 1;
				} else return -1;
			}
		}
		return 0;
	}
	
	public static void main(String[] args) {
		Segnale s = Segnale.String2Sig("101-");
		System.out.println(s.toString());
	}

}
