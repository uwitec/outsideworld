package com.index;

import java.io.File;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.NoDeletionPolicy;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import com.model.FieldConstant;
import com.mongodb.DBObject;


public class Index {
    private Analyzer analyzer;
    private IndexWriter writer;
    private Directory dir;

    /**
     * 打开dirString 
     * @param dir
     * @throws Exception
     */
    public void open(String dirString) throws Exception {
        dir = FSDirectory.open(new File(dirString));
        //配置config
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36,analyzer);
        //配置config模式     
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        //配置删除策略    
        config.setIndexDeletionPolicy(NoDeletionPolicy.INSTANCE);
        writer = new IndexWriter(dir, config);
    }

    /**
     * 对object建立索引
     * @param items
     * @throws Exception
     */
    public void index(DBObject o) throws Exception {
        Document doc = new Document();
        String id = o.get(FieldConstant.ID).toString();
        if (!StringUtils.isBlank(id)) {
            Field idFiled = new Field(FieldConstant.ID, id, Field.Store.YES, Field.Index.NO);
            doc.add(idFiled);
        }
        String description = (String)o.get(FieldConstant.DESCRIPTION);
        if (!StringUtils.isBlank(description)) {
            Field descriptionField = new Field(FieldConstant.DESCRIPTION, description, Field.Store.YES,
                    Field.Index.ANALYZED);
            doc.add(descriptionField);
        }
        String downloadUrl = (String)o.get(FieldConstant.DOWNLOAD);
        if (!StringUtils.isBlank(downloadUrl)) {
            Field downloadUrlFiled = new Field(FieldConstant.DOWNLOAD, downloadUrl, Field.Store.YES,
                    Field.Index.NO);
            doc.add(downloadUrlFiled);
        }
        String path = (String)o.get(FieldConstant.PATH);
        if (!StringUtils.isBlank(downloadUrl)) {
            Field pathField = new Field(FieldConstant.PATH, path, Field.Store.YES,
                    Field.Index.NO);
            doc.add(pathField);
        }
        String category = (String)o.get(FieldConstant.CATEGORY);
        if(!StringUtils.isBlank(category)){
            Field categoryField = new Field(FieldConstant.CATEGORY,category,Field.Store.YES,Field.Index.NOT_ANALYZED);
            doc.add(categoryField);
        }
        String preferer = (String)o.get(FieldConstant.PREFERER);
        if(!StringUtils.isBlank(preferer)){
            Field prefererField = new Field(FieldConstant.PREFERER,preferer,Field.Store.YES,Field.Index.NOT_ANALYZED);
            doc.add(prefererField);
        }
        writer.addDocument(doc);
    }

    /**
     * 关闭索引
     * @throws Exception
     */
    public void close() throws Exception {
        if (writer != null) {
        	writer.forceMerge(1);
            writer.close();
        }
    }

    /**
     *提交
     * @throws Exception
     */
    public void commit() throws Exception {
        writer.optimize();
        writer.commit();
    }

    /**
     * index
     * @param objs
     * @throws Exception
     */
    public void index(List<DBObject> objs) throws Exception {
        for (DBObject o : objs) {
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
