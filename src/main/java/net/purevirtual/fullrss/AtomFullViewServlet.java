package net.purevirtual.fullrss;

import java.util.Calendar;
import java.util.Date;
import net.purevirtual.fullrss.entity.Feed;

public class AtomFullViewServlet extends AbstractFullViewServlet {


	@Override
	protected String getFeedType() {
		return "atom_1.0";
	}

	@Override
	protected void updateFeedEntity(Feed f, byte[] bytes) {
		f.setCachedAtom(bytes);
		f.setCachedAtomAt(new Date());
		feedDao.merge(f);
	}

	@Override
	protected byte[] getCachedResponse(Feed feed) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR, -1);
		if (!feed.getCachedAtomAt().before(now.getTime())) {
			return	feed.getCachedAtom();
		} else {
			return null;
		}
	}
}
