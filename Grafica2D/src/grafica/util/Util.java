package grafica.util;

public class Util {
	
	private Util() {}
	
	public static void checkNull(Object... objects) {
		for (Object object : objects) {
			if (object == null) {
				throw new NullPointerException();
			}
		}
	}

}
