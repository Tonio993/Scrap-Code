package dp.observer.prova1;

public class Prova1Main {
	
	public static void main(String[] args) {
		DataContainer dc = new DataContainer();
		DataObserver do1 = new DataObserver();
		DataObserver do2 = new DataObserver();
		System.out.println("Set value A to 60");
		dc.setValueA(60);
		System.out.println("Add do1 as observer");
		do1.setSubject(dc);
		System.out.println("Set value B to 40");
		dc.setValueB(40);
		System.out.println("Add do2 as observer");
		do2.setSubject(dc);
		System.out.println("Set value C to 55");
		dc.setValueC(55);
		System.out.println("Remove do1 and do2 from observer list");
		do1.setSubject(null);
		do2.setSubject(null);
		System.out.println("Set value A to 3");
		dc.setValueA(3);
		
		
	}

}
