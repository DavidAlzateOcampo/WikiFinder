package co.edu.uniandes.wiki.filter;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateFilter {

	public boolean filter(Date inicio, Date fin, String texto) {
		Calendar cal = Calendar.getInstance();
		String fecha = "";
		// Date Pattern 30/12/2015
		Pattern p = Pattern.compile("(\\d|\\d\\d)(\\p{Blank}*)[- /.]"
				+ "(\\p{Blank}*)(\\d|\\d\\d)(\\p{Blank}*)[- /.]"
				+ "(\\p{Blank}*)(\\d\\d\\d\\d)");

		Matcher m = p.matcher(texto);
		while (m.find()) {
			fecha = m.group();
			fecha = fecha.replaceAll("\\p{Blank}*", "");
			String[] fechaSplit = fecha.split("[- /.]");
			int date = new Integer(fechaSplit[0]);
			int month = new Integer(fechaSplit[1]);
			int year = new Integer(fechaSplit[2]);
			if (month < 12) {
				cal.set(year, month, date);
				if (inicio.getTime() <= cal.getTimeInMillis()
						&& fin.getTime() >= cal.getTimeInMillis()) {
					return true;
				}
			}
		}

		// Date Pattern 2015/12/31
		p = Pattern.compile("(\\d\\d\\d\\d)(\\p{Blank}*)[- /.]"
				+ "(\\p{Blank}*)(\\d|\\d\\d)(\\p{Blank}*)[- /.]"
				+ "(\\p{Blank}*)(\\d|\\d\\d)");
		m = p.matcher(texto);
		while (m.find()) {
			fecha = m.group();
			fecha = fecha.replaceAll("\\p{Blank}*", "");
			String[] fechaSplit = fecha.split("[- /.]");
			int date = new Integer(fechaSplit[2]);
			int month = new Integer(fechaSplit[1]);
			int year = new Integer(fechaSplit[0]);
			if (month < 12) {
				cal.set(year, month, date);
				if (inicio.getTime() <= cal.getTimeInMillis()
						&& fin.getTime() >= cal.getTimeInMillis()) {
					return true;
				}
			}
		}
		// Date Pattern 12/31/2015
		p = Pattern.compile("(\\d|\\d\\d)(\\p{Blank}*)[- /.]"
				+ "(\\p{Blank}*)(\\d|\\d\\d)(\\p{Blank}*)[- /.]"
				+ "(\\p{Blank}*)(\\d\\d\\d\\d)");
		m = p.matcher(texto);
		while (m.find()) {
			fecha = m.group();
			fecha = fecha.replaceAll("\\p{Blank}*", "");
			String[] fechaSplit = fecha.split("[- /.]");
			int date = new Integer(fechaSplit[1]);
			int month = new Integer(fechaSplit[0]);
			int year = new Integer(fechaSplit[2]);
			if (month < 12) {
				cal.set(year, month, date);
				if (inicio.getTime() <= cal.getTimeInMillis()
						&& fin.getTime() >= cal.getTimeInMillis()) {
					return true;
				}
			}
		}
		// Date Pattern 12 January 2009
		p = Pattern.compile("(\\d|\\d\\d)(\\p{Blank}*)[- /.]"
				+ "(\\p{Blank}*)(\\w{3,9})(\\p{Blank}*)[- /.]"
				+ "(\\p{Blank}*)(\\d\\d\\d\\d)");
		m = p.matcher(texto);
		while (m.find()) {
			fecha = m.group();
			fecha = fecha.replaceAll("\\p{Blank}+", "");
			String[] fechaSplit = fecha.split("(\\D+)");
			int date = new Integer(fechaSplit[0]);
			int year = new Integer(fechaSplit[1]);
			String[] meses = fecha.split("(\\d+)");
			String mes = meses[1];
			int month = 0;
			mes = mes.toLowerCase();
			if (mes.startsWith("jan")) {
				month = Calendar.JANUARY;
			} else if (mes.startsWith("feb")) {
				month = Calendar.FEBRUARY;
			} else if (mes.startsWith("mar")) {
				month = Calendar.MARCH;
			} else if (mes.startsWith("apr")) {
				month = Calendar.APRIL;
			} else if (mes.startsWith("may")) {
				month = Calendar.MAY;
			} else if (mes.startsWith("jun")) {
				month = Calendar.JUNE;
			} else if (mes.startsWith("jul")) {
				month = Calendar.JULY;
			} else if (mes.startsWith("aug")) {
				month = Calendar.AUGUST;
			} else if (mes.startsWith("sep")) {
				month = Calendar.SEPTEMBER;
			} else if (mes.startsWith("oct")) {
				month = Calendar.OCTOBER;
			} else if (mes.startsWith("nov")) {
				month = Calendar.NOVEMBER;
			} else if (mes.startsWith("dec")) {
				month = Calendar.DECEMBER;
			} else {
				month = 13;
			}

			if (month < 12) {
				cal.set(year, month, date);
				if (inicio.getTime() <= cal.getTimeInMillis()
						&& fin.getTime() >= cal.getTimeInMillis()) {
					return true;
				}
			}
		}

		return false;
	}

}
