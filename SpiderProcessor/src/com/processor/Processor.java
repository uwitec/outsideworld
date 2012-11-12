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

		// process
		int errors = 0;
		while (true) {
			// inject data
			Map<String, Object> data = null;
			try {
				data = injector.next();
			} catch (Exception e) {
				errors++;
				log.error("injector " + injector.getName() + " errors "
						+ errors + " times", e);
				if (errors < 5) {
					continue;
				} else {
					log.error("injector errors too many times, exit processor "
							+ processorType.getName());
					break;
				}

			}
			context = new Context(data);
			if (processorType.getFailsafe() != null) {
				context.setFailsafe(processorType.getFailsafe());
			}

			// initialize
			errors = 0;

			// process data
			for (AbstractHandler handler : handlers) {
				try {
					handler.process(context);
				} catch (Exception e) {
					if (context.isFailsafe()) {
						log.warn(
								"processor error at handler "
										+ handler.getName(), e);
						continue;
					} else {
						log.error(
								"processor error at handler"
										+ handler.getName(), e);
						errors++;
						break;
					}
				}
			}

			// check fail safe
			if (!context.isFailsafe() && errors > 0) {
				continue;
			}

			// collect data
			for (CollectorHandler collectorHandler : collector) {
				try {
					collectorHandler.collect(context);
				} catch (Exception e) {
					if (context.isFailsafe()) {
						log.warn("processor error at collector "
								+ collectorHandler.getName(), e);
						continue;
					} else {
						log.error("processor error at collector "
								+ collectorHandler.getName(), e);
						errors++;
						break;
					}
				}
			}

			// log
			if (errors > 0) {
				log.warn(errors + " errors happened when processing "
						+ context.toString());
			} else {
				log.warn("process " + context.toString() + " successfully");
			}

			// initialize
			errors = 0;
		}
	}
}
