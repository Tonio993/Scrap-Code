package automa.gui.tabelle;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import automa.karnaugh.MappaDiKarnaugh;
import automa.oggetti.Segnale;
import automa.util.GBC;

public class TabellaMappaDiKarnaugh extends JDialog {
	
	private final static String[] SEGNALE1 = {""};
	private final static String[] SEGNALE2 = {"0","1"};
	private final static String[] SEGNALE4 = {"00","01","11","10"}; 

	private static final long serialVersionUID = -7438344820425821147L;
	
	JPanel pDialog;
	
	public TabellaMappaDiKarnaugh(MappaDiKarnaugh mappaDiKarnaugh) {
		this(mappaDiKarnaugh, "");
	}
	
	private TabellaMappaDiKarnaugh(MappaDiKarnaugh mappaDiKarnaugh, String sub) {
		int righe, colonne;
		int dimensione = mappaDiKarnaugh.dimensione;
		boolean sottoTabelle = dimensione - sub.length() > 4;
		if (sub.length() == 0) {
			righe = (int) Math.pow(2, (int) ((dimensione - sub.length()) % 4 == 0 ? 4 : dimensione % 4) / 2);
			colonne = (int) Math.pow(2, (int) ((dimensione - sub.length()) % 4 == 0 ? 4 : dimensione % 4 + 1) / 2);
		} else {
			righe = 4;
			colonne = 4;
		}
		pDialog = new JPanel(new GridBagLayout());
		pDialog.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED));
		pDialog.setBackground(Color.white);
		this.setContentPane(pDialog);
		String[] segnaliRighe = righe == 1 ? SEGNALE1 : righe == 2 ? SEGNALE2 : SEGNALE4;
		String[] segnaliColonne = colonne == 2 ? SEGNALE2 : SEGNALE4;
		TreeSet<Segnale> mappa = mappaDiKarnaugh.getMappa();
		if (!sottoTabelle) {
			for (int r=0; r<righe; r++) {
				for (int c=0; c<colonne; c++) {
					JPanel pValore = new JPanel(new GridBagLayout());
					pValore.setBackground(Color.white);
					pValore.setPreferredSize(new Dimension(30,30));
					if (mappa.contains(Segnale.String2Sig(sub + segnaliRighe[r] + segnaliColonne[c]))) {
						JLabel lValore = new JLabel("1", JLabel.CENTER);
						lValore.setForeground(Color.black);
						pValore.add(lValore, new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
					}
					pValore.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED));
					pDialog.add(pValore, new GBC(c,r).setWeight(1, 1).setFill(GBC.BOTH));
				}
			}
			
		} else {
			for (int r=0; r<righe; r++) {
				for (int c=0; c<colonne; c++) {
					Container tabella = new TabellaMappaDiKarnaugh(mappaDiKarnaugh, sub + segnaliRighe[r] + segnaliColonne[c]).getContentPane();
					pDialog.add(tabella, new GBC(c,r).setWeight(1, 1).setFill(GBC.BOTH).setInsets(5));
				}
			}
		}
		this.pack();
	}
	
}
