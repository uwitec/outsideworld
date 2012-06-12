package com.index;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MergeScheduler;
import org.apache.lucene.index.NoDeletionPolicy;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.model.Story;


public class Index {
    private Analyzer analyzer;
    private IndexWriter writer;
    private Directory dir;

    /**
     * 鎵撳紑绱㈠紩鏂囦欢锛岄粯璁�     * 
     * @param dir
     * @throws Exception
     */
    public void open(String dirString) throws Exception {
        dir = FSDirectory.open(new File(dirString));
        // 璁剧疆config
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36,analyzer);
        // 鍒涘缓妯″紡锛�        
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        // 涓嶅垹闄�      
        config.setIndexDeletionPolicy(NoDeletionPolicy.INSTANCE);
        writer = new IndexWriter(dir, config);
    }

    /**
     * 瀵逛竴鎵筰tem寤虹珛绱㈠紩
     * 
     * @param items
     * @throws Exception
     */
    public void index(Story o) throws Exception {
        Document doc = new Document();
        String id = o.getId();
        if (!StringUtils.isBlank(id)) {
            Field idFiled = new Field("id", id, Field.Store.YES, Field.Index.NO);
            doc.add(idFiled);
        }
        String description = o.getDescription();
        if (!StringUtils.isBlank(description)) {
            Field descriptionField = new Field("description", description, Field.Store.YES,
                    Field.Index.ANALYZED);
            doc.add(descriptionField);
        }
        String downloadUrl = o.getDownloadUrl();
        if (!StringUtils.isBlank(downloadUrl)) {
            Field downloadUrlFiled = new Field("downloadUrl", downloadUrl, Field.Store.YES,
                    Field.Index.NO);
            doc.add(downloadUrlFiled);
        }
        String path = o.getPath();
        if (!StringUtils.isBlank(downloadUrl)) {
            Field pathField = new Field("path", downloadUrl, Field.Store.YES,
                    Field.Index.NO);
            doc.add(pathField);
        }
        String category = o.getCategory();
        if(!StringUtils.isBlank(category)){
            Field categoryField = new Field("category",category,Field.Store.YES,Field.Index.NOT_ANALYZED);
        }
        writer.addDocument(doc);
    }

    /**
     * 鍏抽棴writer
     * 
     * @throws Exception
     */
    public void close() throws Exception {
        if (writer != null) {
        	writer.forceMerge(1);
            writer.close();
        }
    }

    /**
     * 鎻愪氦绱㈠紩
     * 
     * @throws Exception
     */
    public void commit() throws Exception {
        writer.optimize();
        writer.commit();
    }

    /**
     * 瀵逛竴涓垪琛ㄥ缓绔嬬储寮�     * 
     * @param objs
     * @throws Exception
     */
    public void index(List<Story> objs) throws Exception {
        for (Story o : objs) {
            index(o);
        }
        commit();
    }

    
    public Analyzer getAnalyzer() {
        return analyzer;
    }

    
    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }
    
    
}
