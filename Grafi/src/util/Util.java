package util;

public class Util {
	
	private Util() {}
	
	public static void checkNull(Object obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
	}

}
