package com.aries.study.bookspider.qq;

import javax.sql.DataSource;

import com.aries.util.db.SimpleDataBase;

public class QQBookSpider {
	private DataSource dataSource;

	public QQBookSpider() {
		String url = "jdbc:postgresql://localhost:3306/bookreader?useUnicode=true;characterEncoding=utf8";
		String driverClass = "com.mysql.jdbc.Driver";
		String user = "root";
		String password = "root";
		dataSource = SimpleDataBase.setupDataSource(url, driverClass, user,
				password);
	}
}
