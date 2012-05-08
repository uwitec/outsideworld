package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import com.index.AbstractIndex;
import com.model.Item;
import com.model.policy.Topic;

/**
 * 在内存中建立索引，针对所有生效的topic做查询，如果有一个topic命中，则保留item，否则删除 <p>类说明</p> <p>Copyright: 版权所有 (c) 2010 -
 * 2030</p> <p>Company: Travelsky</p>
 * 
 * @author fangxia722
 * @version 1.0
 * @since May 3, 2012
 */
public class ItemSelector {

    private AbstractIndex index;
    private IndexSearcher searcher;

    public List<Item> select(List<Item> items, List<Topic> topics) throws Exception {
        // 定义一个Map，将所有的items定义在map中
        Map<String, Item> map = new HashMap<String, Item>();
        for (Item item : items) {
            map.put(item.getUrl(), item);
        }
        // 定义set结果
        Set<Item> setResult = new HashSet<Item>();
        // 打开RAMDirectory
        index.open("");
        // 建立内存索引
        index.index(items);
        index.commit();
        index.close();
        searcher = new IndexSearcher(index.getDir());
        // 对每个topic建立一次查询
        for (Topic topic : topics) {
            BooleanQuery query = new BooleanQuery();
            String[] musts = topic.getInclude().split(";");
            for (String must : musts) {
                BooleanClause.Occur b = BooleanClause.Occur.MUST;
                query.add(new TermQuery(new Term("title", must)), b);
                query.add(new TermQuery(new Term("content", must)), b);
            }
            String[] mustNots = topic.getExclude().split(";");
            for (String mustNot : mustNots) {
                BooleanClause.Occur b = BooleanClause.Occur.MUST_NOT;
                query.add(new TermQuery(new Term("title", mustNot)), b);
                query.add(new TermQuery(new Term("content", mustNot)), b);
            }
            TopDocs hits = searcher.search(query, items.size());
            for (ScoreDoc scoreDoc : hits.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                String id = doc.get("id");
                setResult.add(map.get(id));
            }
        }
        //将内存释放
        index.getDir().close();        
        List<Item> result = new ArrayList<Item>();
        result.addAll(setResult);
        return result;
    }

    public AbstractIndex getIndex() {
        return index;
    }

    public void setIndex(AbstractIndex index) {
        this.index = index;
    }
}
