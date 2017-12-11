package util;

public class ObjectUtil {
	
	private ObjectUtil() {}
	
	public static void checkNull(Object... obj) {
		for (Object o : obj) {
			if (o == null) {
				throw new NullPointerException();
			}
		}
	}

}
