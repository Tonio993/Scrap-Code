package test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test2 {
	
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
		List<Integer> squaresList = numbers.stream().distinct().sorted((i, j) -> j-i).map(i -> i*i).collect(Collectors.toList());
		System.out.println(numbers);
		System.out.println(squaresList);
	}

}
