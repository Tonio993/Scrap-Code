package automa.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import automa.Automa;
import automa.gui.componenteGrafico.StatoGUI;
import automa.gui.componenteGrafico.TransizioneGUI;
import automa.util.GBC;
import automa.util.Util;

public class PannelloProprieta extends JPanel {

	
	private static final long serialVersionUID = -2622929211046162013L;
	private static final int INSETS = 4;
	
	MediatoreGUI mediator;
	
	JPanel pStato, pTransizioni, pTransizioniScroll, pTransizioniIn, pVerificaAutoma;
	JLabel lNome;
	JTextField tfNome;
	JScrollPane scTransizioni;
	JRadioButton rbStatoIniziale;
	JButton bVerificaAutoma;
	
	StatoGUI stato;
	
	public PannelloProprieta(MediatoreGUI mediator) {
		Util.checkNull(mediator);
		this.mediator = mediator;
		mediator.setPannelloProprieta(this);
		
		struttura();
		
	}
	
	private void struttura() {
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.setPreferredSize(new Dimension(300,0));
		
		pStato = new JPanel(new GridBagLayout());
		pStato.setBorder(BorderFactory.createTitledBorder("Stato"));
		
		lNome = new JLabel("Nome:");
		tfNome = new JTextField();
		tfNome.setEnabled(false);
		tfNome.getDocument().addDocumentListener(listenerNome);
		
		rbStatoIniziale = new JRadioButton("Stato iniziale");
		rbStatoIniziale.setEnabled(false);
		rbStatoIniziale.addActionListener(listenerStatoIniziale);
		
		pStato.add(lNome, new GBC(0,0).setWeight(0, 0).setFill(GBC.BOTH).setInsets(INSETS));
		pStato.add(tfNome, new GBC(1,0).setWeight(1, 0).setFill(GBC.HORIZONTAL).setInsets(INSETS));
		pStato.add(rbStatoIniziale, new GBC(2,0).setWeight(0, 0).setFill(GBC.BOTH).setInsets(INSETS));
		
		pTransizioni = new JPanel(new GridBagLayout());
		pTransizioni.setBorder(BorderFactory.createTitledBorder("Transizioni"));
		
		pTransizioniIn = new JPanel(new GridBagLayout());
		
		pTransizioniScroll = new JPanel(new BorderLayout());
		pTransizioniScroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		pTransizioniScroll.add(pTransizioniIn, BorderLayout.NORTH);
		
		scTransizioni = new JScrollPane(pTransizioniScroll);
		scTransizioni.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scTransizioni.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		pTransizioni.add(scTransizioni, new GBC(0, 0).setAnchor(GBC.NORTH).setFill(GBC.BOTH).setWeight(1, 1).setInsets(INSETS));

		bVerificaAutoma = new JButton("Verifica");
		bVerificaAutoma.addActionListener(listenerVerifica);
		
		pVerificaAutoma = new JPanel(new GridBagLayout());
		pVerificaAutoma.setBorder(BorderFactory.createTitledBorder("Verifica automa"));
		pVerificaAutoma.add(bVerificaAutoma, new GBC(0,0).setWeight(1, 1).setInsets(INSETS));
		
		add(pStato, new GBC(0,0).setWeight(1, 0).setFill(GBC.BOTH).setInsets(INSETS));
		add(pTransizioni, new GBC(0,1).setWeight(1, 1).setFill(GBC.BOTH).setInsets(INSETS));
		add(pVerificaAutoma, new GBC(0,2).setWeight(1, 0).setFill(GBC.BOTH).setInsets(INSETS));
	}
	
	public void setStato(StatoGUI stato) {
		this.stato = stato;
		if (stato != null) {
			boolean edit = mediator.pannelloGrafica.registroStati.esiste(stato);
			tfNome.setEnabled(edit);
			tfNome.getDocument().removeDocumentListener(listenerNome);
			tfNome.setText(stato.getNome());
			tfNome.getDocument().addDocumentListener(listenerNome);
			rbStatoIniziale.setEnabled(edit);
			rbStatoIniziale.setSelected(stato.isStatoIniziale());
		} else {
			tfNome.setEnabled(false);
			tfNome.setText("");
			rbStatoIniziale.setEnabled(false);
			rbStatoIniziale.setSelected(false);
		}
		aggiornaTransizioni(stato);
	}
	
	public void aggiornaTransizioni(StatoGUI stato) {
		pTransizioniIn.removeAll();
		if (stato != null) {
			LinkedList<TransizioneGUI> lista = mediator.listaTransizioni(stato);
			for (TransizioneGUI transizione : lista) {
				pTransizioniIn.add(new ElementoTransizione(transizione), new GBC(0,GBC.RELATIVE).setWeight(1, 0).setFill(GBC.BOTH));
			}
		}
		revalidate();
		repaint();
	}
	

	class ElementoTransizione extends JPanel {
		
		private static final long serialVersionUID = 7515902104951168388L;
		
		TransizioneGUI transizione;
		
		JPanel pVerso;
		JLabel lIngresso, lVerso, lUscita, lArco;
		JSpinner sArco;
		JButton bElimina;
		JTextField tfIngresso, tfUscita;
		JComboBox<StatoGUI> cbVerso;
		
		String ingresso, uscita;
		
		public ElementoTransizione(TransizioneGUI transizione) {
			super(new GridBagLayout());
			Util.checkNull(transizione);
			this.transizione = transizione;
			ingresso = uscita = "";
			boolean edit = mediator.pannelloGrafica.registroStati.esiste(stato);
			this.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED));
			
			lVerso = new JLabel("Verso:");
			cbVerso = new JComboBox<>();
			cbVerso.setEnabled(edit);
			for (StatoGUI stato : mediator.listaStati()) {
				cbVerso.addItem(stato);
			}
			cbVerso.setSelectedItem(transizione.getStatoEntrante());
			cbVerso.addItemListener(listenerComboBox);
			
			pVerso = new JPanel(new GridBagLayout());
			pVerso.add(lVerso, new GBC(0,0).setWeight(0,1).setFill(GBC.BOTH).setInsets(INSETS));
			pVerso.add(cbVerso, new GBC(1,0).setWeight(1,1).setFill(GBC.HORIZONTAL).setInsets(INSETS));
			
			lIngresso = new JLabel("In:");
			lUscita = new JLabel("Out:");
			
			tfIngresso = new JTextField();
			tfIngresso.setEnabled(edit);
			tfIngresso.setText(transizione.getIngresso());
			tfIngresso.getDocument().addDocumentListener(listenerPorte);
			
			tfUscita = new JTextField();
			tfUscita.setEnabled(edit);
			tfUscita.setText(transizione.getUscita());
			tfUscita.getDocument().addDocumentListener(listenerPorte);
			
			lArco = new JLabel("Arco:");
			sArco = new JSpinner();
			sArco.setValue(transizione.getArco());
			sArco.addChangeListener(listenerSpinner);
			
			bElimina = new JButton("Elimina");
			bElimina.setEnabled(edit);
			bElimina.addActionListener(listenerElimina);
			
			add(pVerso, new GBC(0,0).setSpan(4,1).setWeight(1, 1).setFill(GBC.BOTH).setInsets(INSETS));
			add(lIngresso, new GBC(0,1).setWeight(0, 1).setFill(GBC.BOTH).setInsets(INSETS));
			add(tfIngresso, new GBC(1,1).setWeight(1, 1).setFill(GBC.HORIZONTAL).setInsets(INSETS));
			add(lUscita, new GBC(2,1).setWeight(0, 1).setFill(GBC.BOTH).setInsets(INSETS));
			add(tfUscita, new GBC(3,1).setWeight(1, 1).setFill(GBC.HORIZONTAL).setInsets(INSETS));
			add(lArco, new GBC(0,2).setWeight(0,1).setFill(GBC.BOTH).setInsets(INSETS));
			add(sArco, new GBC(1,2).setWeight(1, 1).setFill(GBC.HORIZONTAL).setInsets(INSETS));
			add(bElimina, new GBC(3,2).setSpan(1, 1).setInsets(INSETS));
		}
		
		ItemListener listenerComboBox = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				StatoGUI stato = (StatoGUI) cbVerso.getSelectedItem();
				transizione.setStatoEntrante(stato);
				mediator.setAutoma(null);
				mediator.notifica();
			}
		};
		
		ChangeListener listenerSpinner = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				transizione.setArco((int) sArco.getValue());
				mediator.notifica();
			}
		};
		
		DocumentListener listenerPorte = new DocumentListener() {
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
		
		ActionListener listenerElimina = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediator.pannelloGrafica.eliminaTransizione(transizione);
				aggiornaTransizioni(stato);
			}
			
		};
		
		private void setSegnale() {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (!tfIngresso.getText().matches("[01]*")) {
						tfIngresso.setText(ingresso);
					} else {
						ingresso = tfIngresso.getText();
						transizione.setIngresso(ingresso);
					}
					if (!tfUscita.getText().matches("[01]*")) {
						tfUscita.setText(uscita);
					} else {
						uscita = tfUscita.getText();
						transizione.setUscita(uscita);
					}
					mediator.setAutoma(null);
					mediator.notifica();
				}
			});
		}
		
	}
	
	DocumentListener listenerNome = new DocumentListener() {
		public void insertUpdate(DocumentEvent e) {
			setNome();
		}
		public void removeUpdate(DocumentEvent e) {
			setNome();
		}
		public void changedUpdate(DocumentEvent e) {
			setNome();
		}
		
	};
	
	ActionListener listenerStatoIniziale = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			mediator.setStatoIniziale(stato);
			mediator.setAutoma(null);
			mediator.notifica();
		}
	};
	
	private void setNome() {
		if (stato!= null) {
			stato.setNome(tfNome.getText());
			mediator.setAutoma(null);
			mediator.notifica();
		}
	}
	
	ActionListener listenerVerifica = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Automa automa = creaAutoma();
			JOptionPane.showMessageDialog(mediator.finestraPrincipale, automa != null ? "Automa corretto" : "Automa non corretto, possono essere presenti i seguenti problemi:\n - Non sono state inserite tutte le transizioni possibili;\n - Sono presenti più stati con lo stesso nome;\n - Gli ingressi e/o le uscite delle transizioni presentano dimensioni differenti;\n - Non è stato inserito uno stato come stato iniziale.");
			mediator.setAutoma(automa);
		}
	};
	
	private Automa creaAutoma() {
		try {
			int ingressi = mediator.pannelloGrafica.registroTransizioni.iterator().next().getIngresso().length();
			int uscite = mediator.pannelloGrafica.registroTransizioni.iterator().next().getUscita().length();
			return AutomaDaGUI.getAutoma(mediator.pannelloGrafica, ingressi, uscite);
		} catch (Exception e) {
			return null;
		}
	}
	
}

