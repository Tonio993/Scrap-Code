package automa.gui.tabelle;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import automa.Automa;
import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;
import automa.util.GBC;
import automa.util.Util;

public class TabellaStati extends JDialog {

	private static final long serialVersionUID = -6781397120683423009L;
	
	JPanel pDialog;
	
	public TabellaStati() {
		struttura();
	}
	
	private void struttura() {
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		pDialog = new JPanel(new GridBagLayout());
		pDialog.setBackground(Color.white);
		this.setContentPane(pDialog);
	}
	
	public void setAutoma(Automa automa) {
		pDialog.removeAll();
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		add(panel, new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
		List<Stato> stati = automa.listaStati();
		HashMap<String,Integer> segnali = new HashMap<>();
		stati.sort(new Comparator<Stato>() {
			public int compare(Stato o1, Stato o2) {
				return o1.getNome().compareTo(o2.getNome());
			}
			
		});
		for (Stato stato : stati) {
			JLabel lStato = new JLabel(stato.getNome(), JLabel.CENTER);
			lStato.setForeground(Color.BLACK);
			JPanel pStato = new JPanel(new GridBagLayout());
			pStato.setBackground(Color.white);
			pStato.add(lStato, new GBC(0,0).setInsets(5).setFill(GBC.BOTH));
			pStato.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			pDialog.add(pStato, new GBC(0, GBC.RELATIVE).setWeight(1, 1).setFill(GBC.BOTH));
		}
		for (Segnale segnale : Util.setBinario(automa.dimensioneInput())) {
			JLabel lSegnale = new JLabel(segnale.toString(), JLabel.CENTER);
			lSegnale.setForeground(Color.BLACK);
			JPanel pSegnale = new JPanel(new GridBagLayout());
			pSegnale.add(lSegnale, new GBC(0,0).setInsets(5).setFill(GBC.BOTH));
			pSegnale.setBackground(Color.white);
			pSegnale.setBorder(BorderFactory.createLineBorder(Color.black));
			pDialog.add(pSegnale, new GBC(GBC.RELATIVE, 0).setWeight(1, 1).setFill(GBC.BOTH));
			segnali.put(segnale.toString(), segnali.size() + 1);
		}
		for (Transizione transizione : automa.listaTransizioni()) {
			JLabel lTransizione = new JLabel(transizione.statoFinale.getNome(), JLabel.CENTER);
			lTransizione.setForeground(Color.BLACK);
			JLabel lUscita = new JLabel(transizione.out.toString(), JLabel.CENTER);
			lUscita.setForeground(Color.BLACK);
			JPanel pRisposta = new JPanel(new GridBagLayout());
			pRisposta.setBackground(Color.WHITE);
			pRisposta.add(lTransizione, new GBC(0,0).setInsets(5).setAnchor(GBC.EAST).setFill(GBC.BOTH));
			pRisposta.add(lUscita, new GBC(1,0).setInsets(5).setAnchor(GBC.WEST).setFill(GBC.BOTH));
			pRisposta.setBorder(BorderFactory.createLineBorder(Color.black));
			pDialog.add(pRisposta, new GBC(segnali.get(transizione.in.toString()), 1 + stati.indexOf(transizione.statoIniziale)).setFill(GBC.BOTH));
		}
		this.pack();
	}

}
