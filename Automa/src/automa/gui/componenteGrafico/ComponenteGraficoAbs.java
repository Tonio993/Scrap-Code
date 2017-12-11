package automa.gui.componenteGrafico;


public abstract class ComponenteGraficoAbs implements ComponenteGrafico {
	
	private static final long serialVersionUID = -6872798353430162227L;
	protected boolean trascinabile;
	protected boolean selezionato;
	
	public ComponenteGraficoAbs(boolean trascinabile) {
		this.trascinabile = trascinabile;
		this.selezionato = false;
	}

	@Override
	public boolean isTrascinabile() {
		return trascinabile;
	}

	@Override
	public boolean isSelezionato() {
		return selezionato;
	}

	@Override
	public void setSelezionato(boolean b) {
		selezionato = b;
	}

}
