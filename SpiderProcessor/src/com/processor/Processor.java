package com.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.processor.handler.AbstractHandler;
import com.processor.handler.CollectorHandler;
import com.processor.handler.ElementHandler;
import com.processor.handler.FilterHandler;
import com.processor.handler.InjectorHandler;
import com.processor.model.CollectorType;
import com.processor.model.ElementType;
import com.processor.model.FilterType;
import com.processor.model.ProcessorType;

public class Processor implements Runnable {

	private static final Log log = LogFactory.getLog(Processor.class);

	private Context context = null;
	private ProcessorType processorType = null;
	private InjectorHandler injector = null;
	private List<AbstractHandler> handlers = new ArrayList<AbstractHandler>();
	private List<CollectorHandler> collector = new ArrayList<CollectorHandler>();

	public Processor(ProcessorType processorType) {
		this.processorType = processorType;

		injector = new InjectorHandler(processorType.getInjector());

		List<FilterType> filterList = processorType.getFilter();
		for (FilterType filter : filterList) {
			handlers.add(new FilterHandler(filter));
		}

		List<ElementType> elementList = processorType.getElement();
		for (ElementType element : elementList) {
			handlers.add(new ElementHandler(element));
		}

		List<CollectorType> collectorList = processorType.getCollector();
		for (CollectorType collectorType : collectorList) {
			collector.add(new CollectorHandler(collectorType));
		}
	}

	private void initialize() throws Exception {
		// initialize injector
		injector.initialize(context);

		// initialize collectors
		for (CollectorHandler collectorHandler : collector) {
			collectorHandler.initialize(context);
		}
	}

	@Override
	public void run() {
		// initialize
		try {
			initialize();
		} catch (Exception e) {
			log.error("initialize processor error", e);
		}

		// get context
		Context context = new Context();
		if (processorType.getFailsafe() != null) {
			context.setFailsafe(processorType.getFailsafe());
		}

		// process
		while (true) {
			// inject data
			Map<String, Object> data = null;
			try {
				data = injector.next();
			} catch (Exception e) {
				context.error(injector, e);
				if (context.getErrors() < 5) {
					continue;
				} else {
					log.error("injector errors too many times, exit processor "
							+ processorType.getName());
					break;
				}

			}
			context.setData(data);

			// process data
			for (AbstractHandler handler : handlers) {
				try {
					handler.process(context);
				} catch (Exception e) {
					if (context.isFailsafe()) {
						context.error(handler, e);
						continue;
					} else {
						context.error(handler, e);
						break;
					}
				}
			}

			// check fail safe
			if (!context.isFailsafe() && context.getErrors() > 0) {
				continue;
			}

			// collect data
			for (CollectorHandler collectorHandler : collector) {
				try {
					collectorHandler.collect(context);
				} catch (Exception e) {
					if (context.isFailsafe()) {
						context.error(collectorHandler, e);
						continue;
					} else {
						context.error(collectorHandler, e);
						break;
					}
				}
			}

			// log
			context.log();
		}
	}
}
