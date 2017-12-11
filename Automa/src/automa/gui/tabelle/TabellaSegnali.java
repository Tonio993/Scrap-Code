package automa.gui.tabelle;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import automa.Automa;
import automa.Automi;
import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;
import automa.util.GBC;
import automa.util.Util;

public class TabellaSegnali extends JPanel {

	private static final long serialVersionUID = -6781397120683423009L;
	
	public TabellaSegnali(Automa automa) {
		setLayout(new GridBagLayout());
		this.setOpaque(false);
		
		JLabel lVoid = new JLabel();
		add(lVoid, new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
		
		List<Stato> stati = automa.listaStati();
		TreeMap<String,Integer> segnali = new TreeMap<>();
		TreeMap<Stato, Segnale> mappaSegnali= Automi.daStatoASegnale(automa);
		for (Stato stato : stati) {
			JLabel lStato = new JLabel(stato.getNome(), JLabel.CENTER);
			lStato.setForeground(Color.BLACK);
			JLabel lSegnale = new JLabel(mappaSegnali.get(stato).toString(), JLabel.CENTER);
			lSegnale.setForeground(Color.black);
			JPanel pStato = new JPanel(new GridBagLayout());
			pStato.setBackground(Color.white);
			pStato.add(lStato, new GBC(0,0).setInsets(5).setFill(GBC.BOTH));
			pStato.add(lSegnale, new GBC(1,0).setInsets(5).setFill(GBC.BOTH));
			pStato.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED));
			add(pStato, new GBC(0, GBC.RELATIVE).setWeight(1, 1).setFill(GBC.BOTH));
		}
		for (Segnale segnale : Util.setBinario(automa.dimensioneInput())) {
			JLabel lSegnale = new JLabel(segnale.toString(), JLabel.CENTER);
			lSegnale.setForeground(Color.BLACK);
			JPanel pSegnale = new JPanel(new GridBagLayout());
			pSegnale.add(lSegnale, new GBC(0,0).setInsets(5).setFill(GBC.BOTH));
			pSegnale.setBackground(Color.white);
			pSegnale.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED));
			add(pSegnale, new GBC(GBC.RELATIVE, 0).setWeight(1, 1).setFill(GBC.BOTH));
			segnali.put(segnale.toString(), segnali.size() + 1);
		}
		for (Transizione transizione : automa.listaTransizioni()) {
			JLabel lTransizione = new JLabel(mappaSegnali.get(transizione.statoFinale).toString() + transizione.out, JLabel.CENTER);
			lTransizione.setForeground(Color.BLACK);
			JPanel pRisposta = new JPanel(new GridBagLayout());
			pRisposta.setBackground(Color.WHITE);
			pRisposta.add(lTransizione, new GBC(0,0).setInsets(5).setAnchor(GBC.EAST).setFill(GBC.BOTH));
			pRisposta.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED));
			add(pRisposta, new GBC(segnali.get(transizione.in.toString()), 1 + stati.indexOf(transizione.statoIniziale)).setFill(GBC.BOTH));
		}
	}

}
