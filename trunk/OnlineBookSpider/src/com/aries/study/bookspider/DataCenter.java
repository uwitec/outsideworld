package com.aries.study.bookspider;

import java.io.File;

import javax.sql.DataSource;

import com.aries.util.db.SimpleDataBase;

public class DataCenter {
	private static DataSource dataSource;

	private static File storePath = new File("D:/book/");

	static {
		String url = "jdbc:mysql://localhost:3306/book_reader?useUnicode=true&amp;characterEncoding=utf8";
		String driverClass = "com.mysql.jdbc.Driver";
		String user = "root";
		String password = "root";
		dataSource = SimpleDataBase.setupDataSource(url, driverClass, user,
				password);
	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static File getStorePath() {
		return storePath;
	}
}
