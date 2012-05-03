package com.index;


import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;



public class MemIndexImpl extends AbstractIndex {

    @Override
    protected Directory getDirectory(String dir) throws Exception {
        return new RAMDirectory();
    }
}
