package automa.automaMinimo;

import automa.Automa;
import automa.util.Util;

public abstract class AutomaMinimoAbs implements AutomaMinimo {
	
	protected final int dimensioneInput;
	protected final int dimensioneOutput;
	protected final Automa automa;
	
	public AutomaMinimoAbs(Automa automa) {
		Util.checkNull(automa);
		dimensioneInput = automa.dimensioneInput();
		dimensioneOutput = automa.dimensioneOutput();
		this.automa = automa;
	}
	
	@Override
	public int dimensioneInput() {
		return dimensioneInput;
	}
	
	@Override
	public int dimensioneOutput() {
		return dimensioneOutput;
	}

}
