package com.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import com.model.FieldConstant;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


public class Search {
    private IndexSearcher indexSearcher;

    public void open(String indexDir) throws Exception {
        Directory dir = FSDirectory.open(new File(indexDir));
        indexSearcher = new IndexSearcher(dir, true);
    }

    public List<DBObject> search(Query query, int from,int num) throws Exception {
        List<DBObject> result = new ArrayList<DBObject>();        
        TopDocs hits = indexSearcher.search(query, from*num);
        for (int i=(from-1)*num;i<from*num;i++) {
            Document doc =indexSearcher.doc(hits.scoreDocs[i].doc);
            DBObject model = new BasicDBObject();
            model.put(FieldConstant.CATEGORY,doc.get(FieldConstant.CATEGORY));
            model.put(FieldConstant.ID,doc.get(FieldConstant.ID));
            model.put(FieldConstant.PATH,doc.get(FieldConstant.PATH));
            model.put(FieldConstant.PREFERER,doc.get(FieldConstant.PREFERER));
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
