package com.search;

import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import com.model.Story;


public class SearchService {
    private Analyzer analyzer;
    private Search search;
    public List<Story> search(String query,int from,int num) throws Exception{
        Query q = new TermQuery(new Term(query));
        List<Story> result = search.search(q, from,num);
        return result;
    }
    
    public Analyzer getAnalyzer() {
        return analyzer;
    }
    
    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }
    
    
}
