package org.apache.nutch.crawl;

import org.apache.hadoop.io.Text;

import com.model.Template;
import com.util.TemplateCache;

public class MyFetchSchedule extends AbstractFetchSchedule {
	@Override
	public CrawlDatum setFetchSchedule(Text url, CrawlDatum datum,
			long prevFetchTime, long prevModifiedTime, long fetchTime,
			long modifiedTime, int state) {
		datum = super.setFetchSchedule(url, datum, prevFetchTime,
				prevModifiedTime, fetchTime, modifiedTime, state);
		if (datum.getFetchInterval() == 0) {
			try {
				Template t = TemplateCache.getTemplate(url.toString());
				if (t == null || t.getFetchInterval() == 0) {
					datum.setFetchInterval(defaultInterval);
				} else {
					datum.setFetchInterval(t.getFetchInterval());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		datum.setFetchTime(fetchTime + (long) datum.getFetchInterval() * 1000);
		datum.setModifiedTime(modifiedTime);
		return datum;
	}
}
