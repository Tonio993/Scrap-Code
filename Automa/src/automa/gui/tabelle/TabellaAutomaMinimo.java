package automa.gui.tabelle;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import automa.Automa;
import automa.automaMinimo.AutomaMinimoImpl;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;
import automa.util.GBC;
import automa.util.Insieme;

public class TabellaAutomaMinimo extends JPanel{

	private static final long serialVersionUID = -9011090781303224294L;
	
	public TabellaAutomaMinimo(Automa automa) {
		
		Insieme<Stato> statiEquivalenti = new AutomaMinimoImpl(automa).statiEquivalenti();

		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		LinkedList<Stato> stati = new LinkedList<>(automa.listaStati());
		boolean equivalente;
		LinkedList<Stato> dipendenze = new LinkedList<>();
		for (int i=0; i<stati.size() - 1; i++) {
			JPanel pStato1= new JPanel(new GridBagLayout());
			pStato1.setBackground(Color.white);
			pStato1.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED));
			JLabel lStato1 = new JLabel(stati.get(i).getNome(), JLabel.CENTER);
			lStato1.setForeground(Color.black);
			pStato1.add(lStato1, new GBC(0,0).setWeight(1,1).setFill(GBC.BOTH));
			add(pStato1, new GBC(1 + i, stati.size() - 1).setWeight(1,1).setFill(GBC.BOTH));
			JPanel pStato2= new JPanel(new GridBagLayout());
			pStato2.setBackground(Color.white);
			pStato2.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED));
			JLabel lStato2 = new JLabel(stati.get(i + 1).getNome(), JLabel.CENTER);
			lStato2.setForeground(Color.black);
			pStato2.add(lStato2, new GBC(0,0).setWeight(1,1).setFill(GBC.BOTH));
			add(pStato2, new GBC(0, i).setWeight(1,1).setFill(GBC.BOTH));
			
		}
		boolean flag;
		for (int r=0; r<stati.size() - 1; r++) {
			for (int c=r+1; c<stati.size(); c++) {
				equivalente = true;
				dipendenze.clear();
				for (Transizione tr1 : automa.listaTransizioni(stati.get(r))) {
					for (Transizione tr2 : automa.listaTransizioni(stati.get(c))) {
						if (tr1.in.equals(tr2.in)) {
							if (!tr1.out.equals(tr2.out)) {
								equivalente = false;
								break;
							} else if (!tr1.statoFinale.equals(tr2.statoFinale)) {
								if (tr1.statoFinale.compareTo(tr2.statoFinale) < 0) {
									dipendenze.add(tr1.statoFinale);
									dipendenze.add(tr2.statoFinale);
								} else {
									dipendenze.add(tr2.statoFinale);
									dipendenze.add(tr1.statoFinale);
								}
							}
						}
					}
					if (!equivalente) {
						break;
					}
				}
				JPanel pEquivalenza = new JPanel(new GridBagLayout());
				pEquivalenza.setBackground(Color.white);
				pEquivalenza.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED));
				
				JLabel lEquivalenza = new JLabel("", JLabel.CENTER);
				lEquivalenza.setForeground(Color.black);
				
				pEquivalenza.add(lEquivalenza, new GBC(0,0).setWeight(1,1).setFill(GBC.BOTH));
				add(pEquivalenza, new GBC(1 + r, c - 1).setWeight(1,1).setFill(GBC.BOTH));
				
				if (!equivalente) {
					lEquivalenza.setText("X");
				} else {
					StringBuilder sb = new StringBuilder();
					sb.append("<html>");
					flag = dipendenze.isEmpty();
					while (!dipendenze.isEmpty()) {
						Stato stato1 = dipendenze.removeFirst();
						Stato stato2 = dipendenze.removeFirst();
						String colore = statiEquivalenti.insiemeDiAppartenenza(stato1).contains(stato2) ? "red" : "black";
						sb.append("<font color='" + colore + "'>");
						sb.append(stato1 + ", " + stato2);
						sb.append("</font>");
						if (!dipendenze.isEmpty()) {
							sb.append("<br>");
						}
					}
					if (flag) {
						sb.append("<font color='red'>");
						sb.append("EQ");
						sb.append("</font>");
					}
					lEquivalenza.setText(sb.toString());
				}
			}
		}
	}

}
