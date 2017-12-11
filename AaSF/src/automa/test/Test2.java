package automa.test;

import automa.builder.BuilderAutomaHM;
import automa.builder.BuilderAutomaI;
import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;
import util.AutomaUtil;
import util.SegnaleUtil;

public class Test2 {
	
	public static void main(String[] args) {
		Stato statoA = new Stato.Builder().setNome("A").build();
		Stato statoB = new Stato.Builder().setNome("B").build();
		Stato statoC = new Stato.Builder().setNome("C").build();
		Stato statoD = new Stato.Builder().setNome("D").build();
		Stato statoE = new Stato.Builder().setNome("E").build();
		Stato statoF = new Stato.Builder().setNome("F").build();
		Stato statoG = new Stato.Builder().setNome("G").build();
		Stato statoH = new Stato.Builder().setNome("H").build();

		BuilderAutomaI builder = new BuilderAutomaHM();

		builder.aggiungiStato(statoA);
		builder.aggiungiStato(statoB);
		builder.aggiungiStato(statoC);
		builder.aggiungiStato(statoD);
		builder.aggiungiStato(statoE);
		builder.aggiungiStato(statoF);
		builder.aggiungiStato(statoG);
		builder.aggiungiStato(statoH);
		
		builder.setStatoIniziale(statoA);
		
		Segnale v0 = SegnaleUtil.fromString("0");
		Segnale v1 = SegnaleUtil.fromString("1");

		builder.aggiungiTransizione(new Transizione.Builder().setStatoFrom(statoA).setStatoTo(statoA).setSegnaleIn(v0).setSegnaleOut(v1).build());
		builder.aggiungiTransizione(new Transizione.Builder().setStatoFrom(statoA).setStatoTo(statoB).setSegnaleIn(v1).setSegnaleOut(v0).build());
		builder.aggiungiTransizione(new Transizione.Builder().setStatoFrom(statoB).setStatoTo(statoC).setSegnaleIn(v0).setSegnaleOut(v0).build());
		builder.aggiungiTransizione(new Transizione.Builder().setStatoFrom(statoB).setStatoTo(statoD).setSegnaleIn(v1).setSegnaleOut(v0).build());
		builder.aggiungiTransizione(new Transizione.Builder().setStatoFrom(statoC).setStatoTo(statoE).setSegnaleIn(v0).setSegnaleOut(v0).build());
		builder.aggiungiTransizione(new Transizione.Builder().setStatoFrom(statoC).setStatoTo(statoF).setSegnaleIn(v1).setSegnaleOut(v0).build());
		builder.aggiungiTransizione(new Transizione.Builder().setStatoFrom(statoD).setStatoTo(statoG).setSegnaleIn(v0).setSegnaleOut(v1).build());
		builder.aggiungiTransizione(new Transizione.Builder().setStatoFrom(statoD).setStatoTo(statoH).setSegnaleIn(v1).setSegnaleOut(v0).build());
		builder.aggiungiTransizione(new Transizione.Builder().setStatoFrom(statoE).setStatoTo(statoA).setSegnaleIn(v0).setSegnaleOut(v0).build());
		builder.aggiungiTransizione(new Transizione.Builder().setStatoFrom(statoE).setStatoTo(statoB).setSegnaleIn(v1).setSegnaleOut(v1).build());
		builder.aggiungiTransizione(new Transizione.Builder().setStatoFrom(statoF).setStatoTo(statoC).setSegnaleIn(v0).setSegnaleOut(v0).build());
		builder.aggiungiTransizione(new Transizione.Builder().setStatoFrom(statoF).setStatoTo(statoD).setSegnaleIn(v1).setSegnaleOut(v0).build());
		builder.aggiungiTransizione(new Transizione.Builder().setStatoFrom(statoG).setStatoTo(statoE).setSegnaleIn(v0).setSegnaleOut(v0).build());
		builder.aggiungiTransizione(new Transizione.Builder().setStatoFrom(statoG).setStatoTo(statoF).setSegnaleIn(v1).setSegnaleOut(v0).build());
		builder.aggiungiTransizione(new Transizione.Builder().setStatoFrom(statoH).setStatoTo(statoG).setSegnaleIn(v0).setSegnaleOut(v0).build());
		builder.aggiungiTransizione(new Transizione.Builder().setStatoFrom(statoH).setStatoTo(statoH).setSegnaleIn(v1).setSegnaleOut(v1).build());
		
		System.out.println("Automa " + (!builder.buildValido()? "non ":"") + "valido");
		
		Stato[] stati = {statoA, statoB, statoC, statoD, statoE, statoF, statoG, statoH};
		
		for (int i=0; i<stati.length - 1; i++) {
			for (int j=i+1; j<stati.length; j++) {
				System.out.println(AutomaUtil.statiEquivalenti(builder, stati[i], stati[j]));
			}
		}
	}
}
