package automa.gui;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import automa.Automa;
import automa.automaMinimo.AutomaMinimo;
import automa.automaMinimo.AutomaMinimoImpl;
import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;
import automa.util.GBC;
import automa.util.Util;

public class PannelloAutoma extends JPanel {
	
	private static final long serialVersionUID = 4468677964639139803L;
	private static final int INSETS = 2;
	private DefaultListModel<Transizione> lmTransizioni;
	private DefaultListModel<Set<Stato>> lmStatiEquivalenti;
	private JList<Transizione> lTransizioni;
	private JList<Set<Stato>> lStatiEquivalenti;
	private JPanel pTransizioniContent, pSegnale, pSimulazione, pStatiEquivalenti, pStatiEquivalentiContent;
	private JScrollPane spTransizioni, spStatiEquivalenti;
	private JTextField tfSegnale;
	private JButton bInvia;
	
	private MediatoreGUI mediator;
	private Automa automa;
	private boolean minimo;
	
	private String segnale;
	
	public PannelloAutoma(MediatoreGUI mediator, boolean minimo) {
		Util.checkNull(mediator);
		this.minimo = minimo;
		this.mediator = mediator;
		if (!minimo) {
			mediator.setPannelloAutoma(this);
		} else {
			mediator.setPannelloAutomaMinimo(this);
		}
		segnale = "";
		
		struttura();
	}
	
	private void struttura() {
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		lmTransizioni = new DefaultListModel<Transizione>();
		lTransizioni = new JList<Transizione>(lmTransizioni);
		lTransizioni.addListSelectionListener(listenerTransizioni);
		
		pTransizioniContent = new JPanel(new GridBagLayout());
		pTransizioniContent.add(lTransizioni, new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
		
		spTransizioni = new JScrollPane(pTransizioniContent);
		spTransizioni.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		tfSegnale = new JTextField();
		tfSegnale.addKeyListener(listenerInvio);
		tfSegnale.getDocument().addDocumentListener(listenerSegnale);
		
		bInvia = new JButton("Invia");
		bInvia.addActionListener(listenerInvia);
		
		pSegnale = new JPanel(new GridBagLayout());
		pSegnale.add(tfSegnale, new GBC(0,0).setWeight(1, 1).setFill(GBC.HORIZONTAL).setInsets(INSETS));
		pSegnale.add(bInvia, new GBC(1,0).setWeight(0, 0).setInsets(INSETS));
		
		pSimulazione = new JPanel(new GridBagLayout());
		pSimulazione.setBorder(BorderFactory.createTitledBorder("Simulazione"));
		pSimulazione.setPreferredSize(new Dimension(150,0));
		
		pSimulazione.add(spTransizioni, new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH).setInsets(INSETS));
		pSimulazione.add(pSegnale, new GBC(0,1).setWeight(1, 0).setFill(GBC.HORIZONTAL).setInsets(INSETS));
		
		lmStatiEquivalenti = new DefaultListModel<Set<Stato>>();
		lStatiEquivalenti = new JList<Set<Stato>>(lmStatiEquivalenti);
		lStatiEquivalenti.addListSelectionListener(listenerStatiEquivalenti);
		
		pStatiEquivalentiContent = new JPanel(new GridBagLayout());
		pStatiEquivalentiContent.add(lStatiEquivalenti, new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
		
		spStatiEquivalenti = new JScrollPane(pStatiEquivalentiContent);
		spStatiEquivalenti.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		pStatiEquivalenti = new JPanel(new GridBagLayout());
		pStatiEquivalenti.setBorder(BorderFactory.createTitledBorder("Stati equivalenti"));
		
		pStatiEquivalenti.add(spStatiEquivalenti, new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH).setInsets(INSETS));
		
		add(pSimulazione, new GBC(0,GBC.RELATIVE).setWeight(1, 1).setFill(GBC.BOTH));
		if (!minimo) add(pStatiEquivalenti, new GBC(0,GBC.RELATIVE).setWeight(1, 0.5).setFill(GBC.BOTH));
		
		setAutoma(null);
	}
	
	ActionListener listenerInvia = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			inviaSegnale();
			tfSegnale.requestFocus();
		}
	};
	
	private void inviaSegnale() {
		if (tfSegnale.getText().length() == 0) {
			return;
		}
		if (tfSegnale.getText().length() < automa.dimensioneInput()) {
			JOptionPane.showMessageDialog(mediator.finestraPrincipale, "Il segnale d'ingresso inserito ha dimensione diversa rispetto all'automa corrente.");
		} else {
			new Thread(new Runnable() {
				public void run() {
					tfSegnale.setEnabled(false);
					String segnale = tfSegnale.getText();
					while (segnale.length() >= automa.dimensioneInput()) {
						lmTransizioni.addElement(automa.inviaSegnale(Segnale.String2Sig(segnale.substring(0, automa.dimensioneInput()))));
						tfSegnale.setText(segnale.substring(automa.dimensioneInput()));
						lTransizioni.setSelectedIndex(lmTransizioni.size() - 1);
						if (tfSegnale.getText().length() >= automa.dimensioneInput()) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						segnale = tfSegnale.getText();
					}
					tfSegnale.setEnabled(true);
					tfSegnale.requestFocus();
				}
			}).start();
		}
	}
	
	public void setAutoma(Automa automa) {
		this.automa = automa;
		boolean flag = automa != null;
		lmTransizioni.clear();
		lTransizioni.setEnabled(flag);
		tfSegnale.setEnabled(flag);
		bInvia.setEnabled(flag);
		
		lmStatiEquivalenti.clear();
		lStatiEquivalenti.setEnabled(!minimo);
		if (automa != null && !minimo) {
			AutomaMinimo automaMinimo = new AutomaMinimoImpl(automa);
			for (Set<Stato> insieme : automaMinimo.statiEquivalenti().insiemi()) {
				lmStatiEquivalenti.addElement(insieme);
			}
		} else {
			mediator.setEvidenziati(null, null, minimo);
		}
	}
	
	private void setSegnale() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (!tfSegnale.getText().matches("[01]*")) {
					tfSegnale.setText(segnale);
				} else {
					segnale = tfSegnale.getText();
				}
			}
		});
	}
	
	DocumentListener listenerSegnale = new DocumentListener() {
		public void insertUpdate(DocumentEvent e) {
			setSegnale();
		}
		public void removeUpdate(DocumentEvent e) {
			setSegnale();
		}
		public void changedUpdate(DocumentEvent e) {
			setSegnale();
		}
	};
	
	ListSelectionListener listenerTransizioni = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
			if (lTransizioni.getSelectedValue() == null) {
				return;
			}
			TreeSet<Stato> stati = new TreeSet<>();
			stati.add(lTransizioni.getSelectedValue().statoIniziale);
			stati.add(lTransizioni.getSelectedValue().statoIniziale);
			stati.add(lTransizioni.getSelectedValue().statoFinale);
			TreeSet<Transizione> transizioni = new TreeSet<>();
			transizioni.add(lTransizioni.getSelectedValue());
			mediator.setEvidenziati(stati, transizioni, minimo);
		}
	};
	
	ListSelectionListener listenerStatiEquivalenti = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
			if (lStatiEquivalenti.getSelectedValue() == null) {
				return;
			}
			TreeSet<Stato> stati = new TreeSet<>(lStatiEquivalenti.getSelectedValue());
			mediator.setEvidenziati(stati, null, minimo);
		}
	};
	
	KeyListener listenerInvio = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				inviaSegnale();
				lTransizioni.setSelectedIndex(lmTransizioni.size() - 1);
			}
		}
	};
	
}
