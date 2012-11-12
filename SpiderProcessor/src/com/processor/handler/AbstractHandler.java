package com.processor.handler;

import com.processor.Context;

public abstract class AbstractHandler extends BaseHandler {

	public abstract void process(Context context) throws Exception;
}
