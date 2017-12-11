package automa.gui;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import automa.Automa;
import automa.gui.tabelle.TabellaAutomaMinimo;
import automa.gui.tabelle.TabellaSegnali;
import automa.util.GBC;
import automa.util.Util;

public class PannelloTabelle extends JPanel {

	private static final long serialVersionUID = 7257472687329219319L;

	private static final int INSETS = 10;
	
	MediatoreGUI mediator;
	
	TabellaSegnali tSegnali;
	TabellaAutomaMinimo tAutomaMinimo;
	JPanel pSegnali, pAutomaMinimo;
	
	public PannelloTabelle(MediatoreGUI mediator) {
		Util.checkNull(mediator);
		mediator.setPannelloTabelle(this);
		
		this.setLayout(new GridBagLayout());
	}
	
	public void setAutoma(Automa automa) {
		this.removeAll();
		if (automa == null) {
			return;
		}
		pSegnali = new JPanel(new GridBagLayout());
		pSegnali.setBorder(BorderFactory.createTitledBorder("Tabella dei segnali"));
		tSegnali = new TabellaSegnali(automa);
		pSegnali.add(tSegnali, new GBC(0,0).setFill(GBC.CENTER).setInsets(INSETS));
		pAutomaMinimo= new JPanel(new GridBagLayout());
		pAutomaMinimo.setBorder(BorderFactory.createTitledBorder("Tabella a scala dell'automa minimo"));
		tAutomaMinimo = new TabellaAutomaMinimo(automa);
		pAutomaMinimo.add(tAutomaMinimo, new GBC(0,0).setFill(GBC.CENTER).setInsets(INSETS));
		
		add(pSegnali, new GBC(0,0).setWeight(1,0).setFill(GBC.BOTH).setInsets(INSETS));
		add(pAutomaMinimo, new GBC(1,0).setWeight(1,0).setFill(GBC.BOTH).setInsets(INSETS));
		
	}

}
