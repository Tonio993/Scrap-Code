package automa.test;

import automa.AutomaHM;
import automa.AutomaI;
import automa.builder.BuilderAutomaHM;
import automa.builder.BuilderAutomaI;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;
import util.SegnaleUtil;

public class Test3 {
	
	public static void main(String[] args) {
		Stato statoA = new Stato.Builder().setNome("Stato A").build();
		Stato statoB = new Stato.Builder().setNome("Stato B").build();
		Transizione transizioneA = new Transizione.Builder().setStatoFrom(statoA).setStatoTo(statoB).setSegnaleIn(SegnaleUtil.fromString("0")).setSegnaleOut(SegnaleUtil.fromString("0")).build();
		Transizione transizioneB = new Transizione.Builder().setStatoFrom(statoB).setStatoTo(statoA).setSegnaleIn(SegnaleUtil.fromString("0")).setSegnaleOut(SegnaleUtil.fromString("1")).build();
		Transizione transizioneC = new Transizione.Builder().setStatoFrom(statoA).setStatoTo(statoA).setSegnaleIn(SegnaleUtil.fromString("0")).setSegnaleOut(SegnaleUtil.fromString("0")).build();
		Transizione transizioneD = new Transizione.Builder().setStatoFrom(statoB).setStatoTo(statoB).setSegnaleIn(SegnaleUtil.fromString("1")).setSegnaleOut(SegnaleUtil.fromString("1")).build();
		System.out.println("equals: " + transizioneA.equals(transizioneC));
		BuilderAutomaI builder = new BuilderAutomaHM();
		builder.aggiungiStato(statoA);
		builder.setStatoIniziale(statoA);
		builder.aggiungiStato(statoB);
		builder.aggiungiTransizione(transizioneA);
		builder.aggiungiTransizione(transizioneB);
		builder.aggiungiTransizione(transizioneC);
		builder.aggiungiTransizione(transizioneD);
		AutomaI automa = new AutomaHM(builder);
		System.out.println(automa.getRisposta(SegnaleUtil.fromString("0")).segnaleOut);
		System.out.println(automa.getRisposta(SegnaleUtil.fromString("0")).segnaleOut);
		System.out.println(automa.getRisposta(SegnaleUtil.fromString("1")).segnaleOut);
		System.out.println(automa.getRisposta(SegnaleUtil.fromString("1")).segnaleOut);
		System.out.println(automa.getRisposta(SegnaleUtil.fromString("0")).segnaleOut);
		System.out.println(automa.getRisposta(SegnaleUtil.fromString("1")).segnaleOut);
		System.out.println(automa.getRisposta(SegnaleUtil.fromString("1")).segnaleOut);
	}

}
