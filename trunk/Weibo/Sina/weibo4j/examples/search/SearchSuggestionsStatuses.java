package weibo4j.examples.search;

import weibo4j.Search;
import weibo4j.Weibo;
import weibo4j.model.WeiboException;

public class SearchSuggestionsStatuses {

	public static void main(String[] args) {
		Weibo weibo = new Weibo();
		weibo.setToken("2.00RnubuBBytk1B9dacb6b73bGmVreB");
		Search search = new Search();
		try {
			search.searchSuggestionsStatuses("Aries");
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
