package automa.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import automa.oggetti.Segnale;

public final class Util {
	
	private Util() {}
	
	public static void checkNull(Object... obj) {
		for (Object o : obj) {
			if (o == null) {
				throw new NullPointerException();
			}
		}
	}
	
	public static Calendar DateToCalendar(Date date ) { 
		Calendar cal = null;
		try {   
			DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			date = (Date)formatter.parse(date.toString()); 
			cal=Calendar.getInstance();
			cal.setTime(date);
		}
		catch (ParseException e) {
		}  
		return cal;
	}
	
	public static <E> Collection<E> it2collezione(Iterable<E> iter) {
	    Collection<E> list = new ArrayList<E>();
	    for (E item : iter) {
	        list.add(item);
	    }
	    return list;
	}
	
	public static List<Segnale> setBinario(int dimensione) {
		if (dimensione < 0) {
			dimensione = 1;
		}
		LinkedList<Segnale> lista = new LinkedList<>();
		char[] bin = new char[dimensione];
		Arrays.fill(bin, '0');
		int i = dimensione - 1;
		while (i >= 0) {
			lista.add(Segnale.String2Sig(new String(bin)));
			while(i >= 0 && bin[i] == '1') {
				bin[i--] = '0';
			}
			if (i >= 0) {
				bin[i] = '1';
				i = dimensione - 1;
			}
		}
		return lista;
	}
	
	public static String convertToMultiline(String orig)
	{
	    return "<html>" + orig.replaceAll("\n", "<br>");
	}
	
}
