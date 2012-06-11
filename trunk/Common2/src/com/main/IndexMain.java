package com.main;

import com.index.Engine;
import com.util.SpringFactory;

public class IndexMain {
    public static void main(String[] args) throws Exception{
    	Engine engine = SpringFactory.getBean("indexEngine");
		engine.excute();
    }
}
