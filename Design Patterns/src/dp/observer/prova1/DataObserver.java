package dp.observer.prova1;

public class DataObserver implements Observer<DataContainer> {
	
	DataContainer dataContainer;
	
	@Override
	public DataContainer getSubject() {
		return dataContainer;
	}
	
	@Override
	public boolean setSubject(DataContainer data) {
		if (data == dataContainer) {
			return false;
		}
		if (dataContainer != null) {
			dataContainer.removeObserver(this);
		}
		this.dataContainer = data;
		if (data != null) {
			dataContainer.addObserver(this);
		}
		return true;
	}

	@Override
	public void getNotified(ObserveRequest on) {
		String request = on.getRequest();
		switch (request) {
		case "A":
			System.out.println("Nuovo valore per A: " + dataContainer.valueA);
			break;
		case "B":
			System.out.println("Nuovo valore per B: " + dataContainer.valueB);
			break;
		case "C":
			System.out.println("Nuovo valore per C: " + dataContainer.valueC);
			break;
		}
	}

}
