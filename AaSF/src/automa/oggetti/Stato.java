package automa.oggetti;

import java.io.Serializable;

import automa.builder.BuilderI;

public class Stato implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public final String nome;
	
	private Stato(Builder builder) {
		this.nome = builder.nome;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Stato other = (Stato) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	public static class Builder implements BuilderI<Stato> {
		
		private String nome;

		public String getNome() {
			return nome;
		}

		public Builder setNome(String nome) {
			this.nome = nome;
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
		public Stato build() {
			checkBuild();
			return new Stato(this);
		}

		private void checkBuild() {
			//TODO gestione eccezioni
			if (nome == null) {
				throw new IllegalStateException("Nessun nome inserito per lo stato");
			}
		}

	}
	
}

