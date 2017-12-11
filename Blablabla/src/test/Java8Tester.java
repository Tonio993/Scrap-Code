package test;

public class Java8Tester {
	
	interface MathOperation {
		int operation(int a, int b);
	}
	
	public static void main(String[] args) {
		MathOperation addition = (a, b) -> a + b;
		MathOperation subtraction = (a, b) -> a - b;
		MathOperation multiplication = (a, b) -> a * b;
		MathOperation division = (a, b) -> a / b;
		
		System.out.println();
	}
	
	private int operate(int a, int b, MathOperation mathOperation){
	      return mathOperation.operation(a, b);
	   }
	
}
