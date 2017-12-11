package automa.test;

import automa.oggetti.Stato;
import automa.oggetti.Transizione;
import util.SegnaleUtil;

public class Test1 {
	
	public static void main(String[] args) {
		Stato statoA = new Stato.Builder().setNome("Stato A").build();
		Stato statoB = new Stato.Builder().setNome("Stato B").build();
		Transizione transizioneA = new Transizione.Builder().setStatoFrom(statoA).setStatoTo(statoB).setSegnaleIn(SegnaleUtil.fromString("0")).setSegnaleOut(SegnaleUtil.fromString("0")).build();
		Transizione transizioneB = new Transizione.Builder().setStatoFrom(statoA).setStatoTo(statoB).setSegnaleIn(SegnaleUtil.fromString("0")).setSegnaleOut(SegnaleUtil.fromString("0")).build();
		System.out.println("equals: " + transizioneA.statoFrom.equals(transizioneB.statoFrom));
		System.out.println("equals: " + transizioneA.segnaleIn.equals(transizioneB.segnaleIn));
		System.out.println("equals: " + transizioneA.equals(transizioneB));
	}

}