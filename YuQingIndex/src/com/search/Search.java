package com.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import com.model.Item;
/**
 * 从索引中查询数据
 * <p>类说明</p> 
 * <p>Copyright: 版权所有 (c) 2010 - 2030</p>
 * <p>Company: Travelsky</p>
 * @author  fangxia722
 * @version 1.0
 * @since   May 2, 2012
 */
public class Search {

    private IndexSearcher indexSearcher;

    public void open(String indexDir) throws Exception {
        Directory dir = FSDirectory.open(new File(indexDir));
        indexSearcher = new IndexSearcher(dir, true);
    }

    public List<Item> search(Query query, int num) throws Exception {
        List<Item> result = new ArrayList<Item>();
        Sort s = new Sort(new SortField("pubTime", SortField.LONG));
        TopDocs hits = indexSearcher.search(query, num, s);
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = indexSearcher.doc(scoreDoc.doc);
            Item model = new Item();
            model.setTitle(doc.get("title"));
            model.setContent(doc.get("content"));
            // model.setPubTime(doc.get("pubtime"));
            result.add(model);
        }
        return result;
    }

    public void close() throws Exception {
        indexSearcher.close();
    }

    public IndexSearcher getIndexSearcher() {
        return indexSearcher;
    }

    public void setIndexSearcher(IndexSearcher indexSearcher) {
        this.indexSearcher = indexSearcher;
    }
}
