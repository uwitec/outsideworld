package com.index;

import java.util.Date;
import java.util.List;
import net.paoding.analysis.analyzer.PaodingAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.NoDeletionPolicy;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import com.model.Item;


public abstract class AbstractIndex {
    protected static Analyzer analyzer = new PaodingAnalyzer();
    private IndexWriter writer;
    private Directory dir;
    /**
     * 打开索引文件，默认
     * @param dir
     * @throws Exception
     */
    public void open(String dirString) throws Exception{
        dir = getDirectory(dirString);
         //设置config
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, analyzer);
        // 创建模式，
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        // 不删除
        config.setIndexDeletionPolicy(NoDeletionPolicy.INSTANCE);
        writer = new IndexWriter(dir, config);
    }
    /**
     * 对一批item建立索引
     * 
     * @param items
     * @throws Exception
     */
    public void index(Item o) throws Exception {
        String id = o.getId();
        Field idFiled = new Field("id", id, Field.Store.YES, Field.Index.NO);
        String title = o.getTitle();
        Field titleFiled = new Field("title", title, Field.Store.YES, Field.Index.ANALYZED);
        String content = o.getContent();
        Field contentFiled = new Field("content", content, Field.Store.YES, Field.Index.ANALYZED);
        // 查询时指定这个字段为查询字段，不做索引
        Date pubTime = o.getPubTime();
        if (pubTime == null) {
            pubTime = new Date();
        }
        Field pubTimeField = new Field("pubtime", Long.toString(pubTime.getTime()),
                Field.Store.YES, Field.Index.NO);
        Document doc = new Document();
        doc.add(idFiled);
        doc.add(titleFiled);
        doc.add(contentFiled);
        doc.add(pubTimeField);
        writer.addDocument(doc);
    }

    /**
     * 关闭writer
     * 
     * @throws Exception
     */
    public void close() throws Exception {
        if (writer != null) {
            writer.close();
        }
    }
    
    /**
     * 提交索引
     * @throws Exception
     */
    public void commit() throws Exception {
        writer.optimize();
        writer.commit();
    }
    /**
     * 对一个列表建立索引
     * @param objs
     * @throws Exception
     */
    public void index(List<Item> objs) throws Exception {
        for(Item o:objs){           
            index(o);                
         }          
         commit();
    }
    
    abstract protected Directory getDirectory(String dir) throws Exception;
    
    public IndexWriter getWriter() {
        return writer;
    }
    
    public void setWriter(IndexWriter writer) {
        this.writer = writer;
    }
    
    public Directory getDir() {
        return dir;
    }
}
