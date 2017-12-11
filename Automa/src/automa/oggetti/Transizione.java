package automa.oggetti;

import automa.util.Util;

public class Transizione implements Comparable<Transizione>{
	
	public final Stato statoIniziale;
	public final Stato statoFinale;
	public final Segnale in;
	public final Segnale out;
	
	public Transizione(Stato statoIniziale, Stato statoFinale, Segnale in, Segnale out) {
		Util.checkNull(statoIniziale, statoFinale, in, out);
		this.statoIniziale = statoIniziale;
		this.statoFinale = statoFinale;
		this.in = in;
		this.out = out;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((in == null) ? 0 : in.hashCode());
		result = prime * result + ((out == null) ? 0 : out.hashCode());
		result = prime * result
				+ ((statoFinale == null) ? 0 : statoFinale.hashCode());
		result = prime * result
				+ ((statoIniziale == null) ? 0 : statoIniziale.hashCode());
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
		Transizione other = (Transizione) obj;
		if (in == null) {
			if (other.in != null)
				return false;
		} else if (!in.equals(other.in))
			return false;
		if (out == null) {
			if (other.out != null)
				return false;
		} else if (!out.equals(other.out))
			return false;
		if (statoFinale == null) {
			if (other.statoFinale != null)
				return false;
		} else if (!statoFinale.equals(other.statoFinale))
			return false;
		if (statoIniziale == null) {
			if (other.statoIniziale != null)
				return false;
		} else if (!statoIniziale.equals(other.statoIniziale))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return statoIniziale.getNome() + " > " + in.toString() + " > " + statoFinale.getNome() + " > " + out.toString();
	}

	@Override
	public int compareTo(Transizione o) {
		int cmp = statoIniziale.compareTo(o.statoIniziale);
		if (cmp != 0) return cmp;
		cmp = in.compareTo(o.in);
		if (cmp != 0) return cmp;
		cmp = statoFinale.compareTo(o.statoFinale);
		if (cmp != 0) return cmp;
		return out.compareTo(o.out);
	}

}
