package com.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import com.model.Story;


public class Search {
    private IndexSearcher indexSearcher;

    public void open(String indexDir) throws Exception {
        Directory dir = FSDirectory.open(new File(indexDir));
        indexSearcher = new IndexSearcher(dir, true);
    }

    public List<Story> search(Query query, int num) throws Exception {
        List<Story> result = new ArrayList<Story>();
        TopDocs hits = indexSearcher.search(query, num);
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = indexSearcher.doc(scoreDoc.doc);
            Story model = new Story();
            model.setDescription(doc.get("description"));
            model.setId(doc.get("id"));
            model.setPath(doc.get("path"));
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
