package automa.util;

import java.util.Comparator;

public class ComparatoreBinario implements Comparator<String>{
	public int compare(String o1, String o2) {
		if (!o1.matches("[01\\-]*") || !o2.matches("[01\\-]*")) {
			throw new IllegalArgumentException();
		}
		o1 = o1.replace("-", "");
		o2 = o2.replace("-", "");
		return Integer.parseInt(o1, 2) - Integer.parseInt(o2, 2);
	}
}
