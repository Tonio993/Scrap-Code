package automa.oggetti;

import java.util.ArrayList;
import java.util.Iterator;

import automa.builder.BuilderI;

public class Segnale implements Iterable<Valore> {
	
	private final ArrayList<Valore> segnale;
	
	private Segnale(Builder builder) {
		this.segnale = new ArrayList<>(builder.segnale);
	}
	
	public int size() {
		return segnale.size();
	}

	@Override
	public Iterator<Valore> iterator() {
		return segnale.iterator();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((segnale == null) ? 0 : segnale.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Segnale [segnale=" + segnale + "]";
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
		if (segnale == null) {
			if (other.segnale != null)
				return false;
		} else if (!segnale.equals(other.segnale))
			return false;
		return true;
	}

	public static class Builder implements BuilderI<Segnale> {
		
		private ArrayList<Valore> segnale;
		
		public ArrayList<Valore> getSegnale() {
			return segnale;
		}
		
		public Builder setSegnale(ArrayList<Valore> segnale) {
			this.segnale = segnale;
			return this;
		}
		
		@Override
		public boolean buildValido() {
			try {
				checkBuild();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
		@Override
		public Segnale build() {
			checkBuild();
			return new Segnale(this);
		}

		private void checkBuild() {
			//TODO gestione eccezioni
			if (segnale == null || segnale.isEmpty() ) {
				throw new IllegalStateException("Segnale uguale a null o dimensione uguale a 0");
			}
		}
	}
}