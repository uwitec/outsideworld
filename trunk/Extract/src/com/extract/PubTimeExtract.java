package com.extract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.model.Item;

public class PubTimeExtract extends AExtract {
	private Pattern regx1 = Pattern
			.compile("[0-9]{2,4}-[0-9]{1,2}-[0-9]{1,2}[\\s|\\S]*[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}");
	private Pattern regx2 = Pattern.compile("[0-9]{2,4}-[0-9]{1,2}-[0-9]{1,2}");
	private Pattern regx3 = Pattern.compile("[\\d]年[\\d]月[\\d]日");
	private SimpleDateFormat format1 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

	@SuppressWarnings("deprecation")
	@Override
	public void process(Item item) throws Exception {
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		if (item.getTemplate() == null) {
			item.setStatus(false);
			return;
		}
		String pubTime = extract("pubTime", item);
		String tmp = "";
		Date result = new Date();
		Matcher matcher1 = regx1.matcher(pubTime);
		Matcher matcher2 = regx2.matcher(pubTime);
		Matcher matcher3 = regx3.matcher(pubTime);
		if (StringUtils.isBlank(pubTime)) {
			// 如果没有抽取到，则使用默认的抽取策略
		} else if (matcher1.find()) {
			tmp = matcher1.group();
			String[] tmps = tmp.split("-|\\s");
			tmps[0] = year;
			tmps[1] = (tmps[1].length() == 1 ? "0" + tmps[1] : tmps[1]);
			tmps[2] = (tmps[2].length() == 1 ? "0" + tmps[2] : tmps[2]);
			result = format1.parse(tmps[0] + "-" + tmps[1] + "-" + tmps[2]
					+ " " + tmps[3]);
		} else if (matcher2.find()) {
			tmp = matcher2.group();
			String[] tmps = tmp.split("-");
			tmps[0] = year;
			tmps[1] = (tmps[1].length() == 1 ? "0" + tmps[1] : tmps[1]);
			tmps[2] = (tmps[2].length() == 1 ? "0" + tmps[2] : tmps[2]);

			result = format2.parse(tmps[0] + "-" + tmps[1] + "-" + tmps[2]);
		} else if (matcher3.find()) {
			String temp = matcher3.group();
			temp.replace("年", "-");
			temp.replace("月", "-");
			temp.replace("日", "-");
			result = new Date(temp);
		}
		item.setPubTime(result);
	}

}
