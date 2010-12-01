package com.aries.study.bookspider.qq;

import java.util.regex.Pattern;

import com.aries.htmlmodifier.INodeFilter;
import com.aries.htmlmodifier.dom.ITagNode;

public class QQBookConfig {

	public static INodeFilter bookList = new INodeFilter() {

		@Override
		public boolean accept(ITagNode arg0) {
			return false;
		}
	};

	public static INodeFilter title = new INodeFilter() {
		@Override
		public boolean accept(ITagNode node) {
			if (!node.getTagName().equals("a")) {
				return false;
			}

			ITagNode parentNode = node.getParent();
			if (parentNode == null) {
				return false;
			}
			if (!parentNode.getTagName().equals("h1")) {
				return false;
			}

			ITagNode grandParent = parentNode.getParent();
			if (grandParent == null) {
				return false;
			}

			if (!grandParent.getAttr("class").equals("entry-content")) {
				return false;
			}

			if (!grandParent.getAttr("id").equals("focus_book_info")) {
				return false;
			}
			return true;
		}
	};

	public static INodeFilter chapter = new INodeFilter() {
		@Override
		public boolean accept(ITagNode node) {
			// <li>
			if (!node.getTagName().equals("li")) {
				return false;
			}

			// <ol><li>
			ITagNode parentNode = node.getParent();
			if (parentNode == null) {
				return false;
			}
			if (!parentNode.getTagName().equals("ol")) {
				return false;
			}

			// <ol class="clearfix"><li>
			if (parentNode.getAttr("class").equals("clearfix")) {
				return true;
			}
			return true;
		}
	};

	public static INodeFilter content = new INodeFilter() {
		@Override
		public boolean accept(ITagNode node) {
			if (!node.getTagName().equals("div")) {
				return false;
			}

			if (node.getAttr("id") == null) {
				return false;
			}

			if (!node.getAttr("id").equals("content")) {
				return false;
			}
			return true;
		}
	};

	public static Pattern category = Pattern
			.compile("(http://bookapp.book.qq.com/origin/workintro/[0-9]+/).*");

	public static Pattern contentLink = Pattern
			.compile(".*workid=([0-9]+)&chapterid=([0-9]+)");
}
