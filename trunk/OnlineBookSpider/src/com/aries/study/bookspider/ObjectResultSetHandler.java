package com.aries.study.bookspider;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

public class ObjectResultSetHandler implements ResultSetHandler<List<Object[]>> {
	@Override
	public List<Object[]> handle(ResultSet rs) throws SQLException {
		List<Object[]> list = new ArrayList<Object[]>();
		while (rs.next()) {
			ResultSetMetaData meta = rs.getMetaData();
			int cols = meta.getColumnCount();
			Object[] result = new Object[cols];

			for (int i = 0; i < cols; i++) {
				result[i] = rs.getObject(i + 1);
			}
			list.add(result);
		}
		return list;
	}
}