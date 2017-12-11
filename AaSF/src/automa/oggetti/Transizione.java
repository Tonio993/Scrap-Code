
package automa.oggetti;

import automa.builder.BuilderI;

public class Transizione {
	
	public final Stato statoFrom;
	public final Stato statoTo;
	public final Segnale segnaleIn;
	public final Segnale segnaleOut;
	
	private Transizione(Builder builder) {
		this.statoFrom = builder.statoFrom;
		this.statoTo = builder.statoTo;
		this.segnaleIn = builder.segnaleIn;
		this.segnaleOut = builder.segnaleOut;
	}
		
		@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((segnaleIn == null) ? 0 : segnaleIn.hashCode());
		result = prime * result + ((statoFrom == null) ? 0 : statoFrom.hashCode());
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
		if (segnaleIn == null) {
			if (other.segnaleIn != null)
				return false;
		} else if (!segnaleIn.equals(other.segnaleIn))
			return false;
		if (statoFrom == null) {
			if (other.statoFrom != null)
				return false;
		} else if (!statoFrom.equals(other.statoFrom))
			return false;
		return true;
	}

		public  static class Builder implements BuilderI<Transizione> {
			
			private Stato statoFrom;
			private Stato statoTo;
			private Segnale segnaleIn;
			private Segnale segnaleOut;
			
			public Stato getStatoFrom() {
				return statoFrom;
			}
			
			public Builder setStatoFrom(Stato statoFrom) {
				this.statoFrom = statoFrom;
				return this;
			}
			
			public Stato getStatoTo() {
				return statoTo;
			}
			
			public Builder setStatoTo(Stato statoTo) {
				this.statoTo = statoTo;
				return this;
			}
			
			public Segnale getSegnaleIn() {
				return segnaleIn;
			}
			
			public Builder setSegnaleIn(Segnale segnaleIn) {
				this.segnaleIn = segnaleIn;
				return this;
			}
			
			public Segnale getSegnaleOut() {
				return segnaleOut;
			}
			
			public Builder setSegnaleOut(Segnale segnaleOut) {
				this.segnaleOut = segnaleOut;
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
			public Transizione build() {
				checkBuild();
				return new Transizione(this);
			}

			private void checkBuild() {
				//TODO gestione eccezioni
				if (statoFrom == null) {
					throw new IllegalStateException("Stato iniziale non valorizzato");
				}
				if (statoTo == null) {
					throw new IllegalStateException("Stato finale non valorizzato");
				}
				if (segnaleIn == null) {
					throw new IllegalStateException("Segnale d'ingresso non valorizzato");
				}
				if (segnaleOut == null) {
					throw new IllegalStateException("Segnale d'uscita non valorizzato");
				}
			}
		}
}