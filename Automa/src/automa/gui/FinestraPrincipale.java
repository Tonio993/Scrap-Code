package automa.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import automa.eccezioni.EccezioneAutoma;
import automa.util.GBC;

public class FinestraPrincipale extends JFrame {

	private static final long serialVersionUID = 314432413953048903L;
	private static final String DIR_ICONA = "media\\icona.png";
	private static final String TITOLO = "Reti sequenziali";
	
	private MediatoreGUI mediator;
	private PannelloGrafica pannelloGrafica;
	private PannelloGraficaAutomaMinimo pannelloGraficaAM;
	private PannelloProprieta pannelloProprieta;
	private PannelloAutoma pannelloAutoma, pannelloAutomaMinimo;
	private PannelloTabelle pannelloTabelle;
	private BarraMenu barraMenu;
	private JScrollPane spPannelloGrafica, spPannelloGraficaAM, spPannelloTabelle;
	private JPanel pPannelloGrafica, pPannelloGraficaAM, pPannelloAutoma, pPannelloTabelle;
	private JSplitPane pSplit1, pSplit2;
	JTabbedPane tpPannelloCentrale;
	private CardLayout lPannelloAutoma;
	
	String selezionato;

	public FinestraPrincipale() throws EccezioneAutoma {
		mediator = new MediatoreGUI();
		mediator.setPannelloPrincipale(this);
		struttura();
	}
	
	private void struttura() throws EccezioneAutoma {
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addComponentListener(listenerFinestra);
		this.setTitle(TITOLO);
		this.setIconImage(new ImageIcon(DIR_ICONA).getImage());
		
		pannelloProprieta = new PannelloProprieta(mediator);

		pannelloGrafica = new PannelloGrafica(mediator);
		pPannelloGrafica = new JPanel(new GridBagLayout());
		pPannelloGrafica.add(pannelloGrafica, new GBC(0,0).setAnchor(GBC.NORTHWEST).setWeight(1, 1));
		spPannelloGrafica = new JScrollPane(pPannelloGrafica);
		spPannelloGrafica.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spPannelloGrafica.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spPannelloGrafica.getVerticalScrollBar().setUnitIncrement(10);;
		spPannelloGrafica.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width*2/3, Toolkit.getDefaultToolkit().getScreenSize().height*2/3));
		
		pannelloGraficaAM = new PannelloGraficaAutomaMinimo(mediator);
		pPannelloGraficaAM = new JPanel(new GridBagLayout());
		pPannelloGraficaAM.add(pannelloGraficaAM, new GBC(0,0).setAnchor(GBC.NORTHWEST).setWeight(1, 1));
		spPannelloGraficaAM = new JScrollPane(pPannelloGraficaAM);
		spPannelloGraficaAM.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spPannelloGraficaAM.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spPannelloGraficaAM.getVerticalScrollBar().setUnitIncrement(10);;
		spPannelloGraficaAM.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width*2/3, Toolkit.getDefaultToolkit().getScreenSize().height*2/3));

		pannelloTabelle = new PannelloTabelle(mediator);
		pPannelloTabelle = new JPanel(new GridBagLayout());
		pPannelloTabelle.add(pannelloTabelle, new GBC(0,0).setAnchor(GBC.NORTHWEST).setWeight(1, 1));
		spPannelloTabelle = new JScrollPane(pPannelloTabelle);
		spPannelloTabelle.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spPannelloTabelle.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spPannelloTabelle.getVerticalScrollBar().setUnitIncrement(10);;
		spPannelloTabelle.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width*2/3, Toolkit.getDefaultToolkit().getScreenSize().height*2/3));

		tpPannelloCentrale = new JTabbedPane();
		tpPannelloCentrale.addTab("Automa", spPannelloGrafica);
		tpPannelloCentrale.addTab("Automa minimo", spPannelloGraficaAM);
		tpPannelloCentrale.add("Tabelle", spPannelloTabelle);
		tpPannelloCentrale.addChangeListener(listenerTab);
		
		pannelloAutoma = new PannelloAutoma(mediator, false);
		pannelloAutomaMinimo = new PannelloAutoma(mediator, true);
		
		lPannelloAutoma = new CardLayout();
		pPannelloAutoma = new JPanel(lPannelloAutoma);
		pPannelloAutoma.add(pannelloAutoma, "Automa");
		pPannelloAutoma.add(pannelloAutomaMinimo, "Automa minimo");
		pPannelloAutoma.add(new JPanel(), "Tabelle");
		
		barraMenu = new BarraMenu(mediator);
		
		pSplit1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pannelloProprieta, tpPannelloCentrale);
		pSplit1.setResizeWeight(0);
		pSplit2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pSplit1, pPannelloAutoma);
		pSplit2.setResizeWeight(1);
		add(pSplit2, BorderLayout.CENTER);
		add(barraMenu, BorderLayout.NORTH);
		
		mediator.setAutoma(null);
		
		pack();
	}
	
	public Dimension getDimensioneGrafica() {
		return spPannelloGrafica.getSize();
	}
	
	ComponentListener listenerFinestra = new ComponentAdapter() {
		public void componentResized(ComponentEvent e) {
			mediator.notifica();
		}
	};
	
	public static void main(String[] args) throws EccezioneAutoma {
		FinestraPrincipale main = new FinestraPrincipale();
		main.setLocationRelativeTo(null);
		main.setVisible(true);
	}
	
	ChangeListener listenerTab = new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			selezionato = tpPannelloCentrale.getTitleAt(tpPannelloCentrale.getSelectedIndex());
			lPannelloAutoma.show(pPannelloAutoma, selezionato);
			mediator.setStato(null);
		}
	};

}
