package fabrizio_maurizio;

public class AltraRoba {
	
	public static void main(String[] args) {
		Roba bora = new Roba();
		AltraRoba alt = new AltraRoba();
		
		alt.cambiaRoba(bora);
		System.out.println(bora.getCoso());
	}
	
	public void cambiaRoba(Roba robba) {
		robba.setCoso("MALAROBBAELLEROINA");
	}

}
