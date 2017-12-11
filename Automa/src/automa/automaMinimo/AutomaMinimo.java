package automa.automaMinimo;

import automa.eccezioni.EccezioneAutoma;
import automa.editor.EditorAutoma;
import automa.oggetti.Stato;
import automa.util.Insieme;

public interface AutomaMinimo {
	
	public int dimensioneInput();
	
	public int dimensioneOutput();
	
	public Insieme<Stato> statiEquivalenti();
	
	public EditorAutoma getAutomaMinimo() throws EccezioneAutoma;
	
}
