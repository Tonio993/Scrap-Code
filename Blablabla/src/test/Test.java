package test;

public class Test {

	public static void main(String[] args) {
		System.out.println(Test.isLong(null));
	}
	
	public static boolean isLong(String string) {
		try {
			Long.parseLong(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
