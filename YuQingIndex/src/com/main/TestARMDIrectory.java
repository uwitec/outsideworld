package com.main;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import com.index.MemIndexImpl;
import com.model.Item;

public class TestARMDIrectory {
   public static void main(String[] args) throws Exception{
       Item item = new Item();
       item.setContent("hello");
       item.setTitle("你好，我是谁");
       item.setUrl("0001");
       MemIndexImpl index = new MemIndexImpl();
       index.open("");
       index.index(item);
       index.commit();
       index.close();
       
       IndexSearcher searcher = new IndexSearcher(index.getDir());
       BooleanQuery query = new BooleanQuery();
       query.add(new TermQuery(new Term("title", "你好")),BooleanClause.Occur.MUST);
       TopDocs hits = searcher.search(query, 1);
       index.getDir().close();
       
   }
}
