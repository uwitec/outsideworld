package com.aries.study.bookspider;

public class Formater {
	public static String formateTxt(String txt) {
		return txt.replace("&nbsp;&nbsp;&nbsp;&nbsp;", "\t").replace("&nbsp;",
				" ");
	}
}
