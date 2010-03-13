package net.purevirtual.fullrss;

import java.util.Calendar;
import java.util.Date;
import net.purevirtual.fullrss.entity.Feed;

public class RSSFullViewServlet extends AbstractFullViewServlet {

	@Override
	protected String getFeedType() {
		return "rss_2.0";
	}

	@Override
	protected void updateFeedEntity(Feed f, byte[] bytes) {
		f.setCachedRSS(bytes);
		f.setCachedRSSAt(new Date());
		feedDao.merge(f);
	}

	@Override
	protected byte[] getCachedResponse(Feed feed) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR, -1);
		if (!feed.getCachedRSSAt().before(now.getTime())) {
			return	feed.getCachedRSS();
		} else {
			return null;
		}
	}
}
