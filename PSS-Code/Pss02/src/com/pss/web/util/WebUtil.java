package com.pss.web.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WebUtil {

	public static List<String> split(String str, String spliter) {
		String[] idstr = str.split(spliter);
		List<String> ids = new ArrayList<String>(idstr.length);
		Collections.addAll(ids, idstr);
		return ids;
	}
}
