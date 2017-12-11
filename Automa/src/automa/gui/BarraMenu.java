package automa.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import automa.gui.componenteGrafico.StatoGUI;
import automa.gui.componenteGrafico.TransizioneGUI;
import automa.util.Util;

public class BarraMenu extends JMenuBar {

	private static final long serialVersionUID = 2973239490103952375L;
	private static final FileNameExtensionFilter filtroFile = new FileNameExtensionFilter("Automa", "aut");
	
	private MediatoreGUI mediator;
	
	JMenu mFile;
	JMenuItem miNuovo;
	JMenuItem miApri;
	JMenuItem miSalva;
	
	public BarraMenu(MediatoreGUI mediator) {
		Util.checkNull(mediator);
		this.mediator = mediator;
		mediator.setBarraMenu(this);
		struttura();
	}
	
	private void struttura() {
		miNuovo = new JMenuItem("Nuovo automa");
		miNuovo.addActionListener(listenerNuovo);
		miNuovo.setMnemonic('N');
		
		miApri = new JMenuItem("Apri automa...");
		miApri.addActionListener(ListenerApri);
		miApri.setMnemonic('O');
		
		miSalva = new JMenuItem("Salva automa...");
		miSalva.addActionListener(ListenerSalva);
		miSalva.setMnemonic('S');
		
		mFile = new JMenu("File");
		mFile.add(miNuovo);
		mFile.add(miApri);
		mFile.add(miSalva);
		
		add(mFile);
	}
	
	ActionListener listenerNuovo = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			nuovo();
		}
	};
	
	ActionListener ListenerApri = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileDialog = new JFileChooser();
			fileDialog.setFileFilter(filtroFile);
			int choose = fileDialog.showOpenDialog(mediator.finestraPrincipale);
			if (choose == JFileChooser.APPROVE_OPTION) {
				try {
					apri(fileDialog.getSelectedFile());
				} catch (IOException | ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(mediator.finestraPrincipale, "Errore l'apertura del file");
				}
			}
		}
	};
	
	ActionListener ListenerSalva = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileDialog = new JFileChooser();
			fileDialog.setFileFilter(filtroFile);
			int choose = fileDialog.showSaveDialog(mediator.finestraPrincipale);
			if (choose == JFileChooser.APPROVE_OPTION) {
				try {
					File file = fileDialog.getSelectedFile();
					if (file == null) {
						return;
					}
					if (!file.getName().endsWith(".aut")) {
						file = new File(file.getPath() + ".aut");
					}
					salva(file);
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(mediator.finestraPrincipale, "Errore durante il salvataggio del file");
				}
			}
		}
	};
	
	ActionListener ListenerIstruzioni = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(mediator.finestraPrincipale, "Guida all'utilizzo del simulatore:\n - Per creare un nuovo stato fai doppio click sulla finestra principale (sfondo nero);\n - Per creare una transizione tra due stati clicca col tasto destro sullo stato di partenza e trascina fino allo stato finale, dopodichè rilascia il tasto destro;\n - Una volta realizzato l'automa clicca sul pulsante \"Verifica automa\" in basso, se l'automa è stato realizzato correttamente sarà possibile accedere alle funzioni sul pannello laterale a destra.");
		}
	};
	
	private void nuovo() {
		mediator.pannelloGrafica.contatoreStati = 1;
		mediator.pannelloGrafica.registroStati = new RegistroComponentiGraficiLL<StatoGUI>();
		mediator.pannelloGrafica.registroTransizioni = new RegistroComponentiGraficiLL<TransizioneGUI>();
		mediator.notifica();
		mediator.setAutoma(null);
	}
	
	@SuppressWarnings("unchecked")
	private void apri(File file) throws IOException, ClassNotFoundException {
		if (file == null) {
			return;
		}
		ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
		mediator.pannelloGrafica.registroStati = (RegistroComponentiGrafici<StatoGUI>) in.readObject();
		mediator.pannelloGrafica.registroTransizioni= (RegistroComponentiGrafici<TransizioneGUI>) in.readObject();
		for (StatoGUI stato : mediator.pannelloGrafica.registroStati) {
			stato.setEvidenziato(false);
			stato.setSelezionato(false);
			if (stato.isStatoIniziale()) {
				mediator.pannelloGrafica.statoIniziale = stato;
			}
		}
		for (TransizioneGUI transizione : mediator.pannelloGrafica.registroTransizioni) {
			transizione.setEvidenziati(false);
		}
		mediator.notifica();
		in.close();
		mediator.setAutoma(null);
		mediator.setStato(null);
	}
	
	private void salva(File file) throws IOException {
		file.createNewFile();
		ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
		out.writeObject(mediator.pannelloGrafica.registroStati);
		out.writeObject(mediator.pannelloGrafica.registroTransizioni);
		out.close();
		mediator.setAutoma(null);
	}
	
}
