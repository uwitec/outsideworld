package com.index;

import java.io.File;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * 使用lucene建立索引 <p>类说明</p> <p>Copyright: 版权所有 (c) 2010 - 2030</p> <p>Company: Travelsky</p>
 * 
 * @author fangxia722
 * @version 1.0
 * @since May 2, 2012
 */
public class IndexImp extends AbstractIndex{

    @Override
    protected Directory getDirectory(String dir) throws Exception {        
        return FSDirectory.open(new File(dir));
    }
    
}
