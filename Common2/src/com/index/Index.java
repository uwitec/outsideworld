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
import org.hibernate.cache.ReadWriteCache.Item;
import com.model.Story;


public class Index {
    protected static Analyzer analyzer = new PaodingAnalyzer();
    private IndexWriter writer;
    private Directory dir;

    /**
     * 打开索引文件，默认
     * 
     * @param dir
     * @throws Exception
     */
    public void open(String dirString) throws Exception {
        dir = FSDirectory.open(new File(dirString));
        // 设置config
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36,analyzer);
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
        String category = o.getCategory();
        if(!StringUtils.isBlank(category)){
            Field categoryField = new Field("category",category,Field.Store.YES,Field.Index.);
        }
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
     * 
     * @throws Exception
     */
    public void commit() throws Exception {
        writer.optimize();
        writer.commit();
    }

    /**
     * 对一个列表建立索引
     * 
     * @param objs
     * @throws Exception
     */
    public void index(List<Story> objs) throws Exception {
        for (Story o : objs) {
            index(o);
        }
        commit();
    }
}
