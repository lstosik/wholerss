package net.purevirtual.fullrss.facade;

import com.google.inject.Inject;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.purevirtual.fullrss.dao.FetchUrlCacheDao;
import net.purevirtual.fullrss.entity.FetchUrlCache;
import org.xml.sax.InputSource;

public class UrlFacade {

	@Inject
	FetchUrlCacheDao fetchUrlCacheDao;
	@Inject
	UrlFacade urlFacade;

	protected byte[] doFetch(URL url) throws IOException {
		InputStream input = url.openStream();
		int numberBytes = input.available();
		byte[] result = new byte[numberBytes];
		input.read(result);
		return result;
	}

	public byte[] fetch(URL url) throws IOException {

		FetchUrlCache fetchUrlCache = fetchUrlCacheDao.findById(url.toString());
		if (fetchUrlCache != null) {
			Calendar now = Calendar.getInstance();
			now.add(Calendar.HOUR, -1);
			if (!fetchUrlCache.getFetchedAt().before(now.getTime())) {
				return fetchUrlCache.getContent();
			}
		}
		byte[] result = this.doFetch(url);
		fetchUrlCacheDao.persist(new FetchUrlCache(url, result));
		return result;

	}

	public SyndFeed fetchFeed(URL url) {
		try {
			SyndFeedInput input = new SyndFeedInput();
			return input.build(new InputSource(new ByteArrayInputStream(urlFacade.fetch(url))));
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(UrlFacade.class.getName()).log(Level.SEVERE, null, ex);
			throw new RuntimeException(ex);
		} catch (FeedException ex) {
			Logger.getLogger(UrlFacade.class.getName()).log(Level.SEVERE, null, ex);
			throw new RuntimeException(ex);
		} catch (IOException ex) {
			Logger.getLogger(UrlFacade.class.getName()).log(Level.SEVERE, null, ex);
			throw new RuntimeException(ex);
		}
	}
}
