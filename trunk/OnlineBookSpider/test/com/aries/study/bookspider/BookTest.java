package com.aries.study.bookspider;

import org.junit.Before;
import org.junit.Test;

import com.aries.htmlmodifier.exception.HtmlParseException;
import com.aries.study.bookspider.qq.QQBook;

public class BookTest {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testFillBookElement() {
		try {
			new QQBook(
					"http://bookapp.book.qq.com/origin/workintro/692/work_2255540.shtml");
		} catch (HtmlParseException e) {
			e.printStackTrace();
		}
	}
}
