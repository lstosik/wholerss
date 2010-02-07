package net.purevirtual.fullrss.facade;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import net.purevirtual.fullrss.EMF;
import net.purevirtual.fullrss.entity.FetchUrlCache;
import org.xml.sax.InputSource;

public class UrlFacade {

	protected byte[] doFetch(URL url) throws IOException {
		InputStream input = url.openStream();
		int numberBytes = input.available();
		byte[] result = new byte[numberBytes];
		input.read(result);
		return result;
	}

	public byte[] fetch(URL url) throws IOException {
		EntityManager em = EMF.get().createEntityManager();

		try {
			FetchUrlCache fetchUrlCache = em.find(FetchUrlCache.class, url.toString());
			if (fetchUrlCache != null) {
				return fetchUrlCache.getContent();
			}
			byte[] result = this.doFetch(url);
			em.persist(new FetchUrlCache(url, result));
			return result;
		} finally {
			em.close();
		}
	}

	public SyndFeed fetchFeed(URL url) {
		try {
			SyndFeedInput input = new SyndFeedInput();
			return input.build(new InputSource(new ByteArrayInputStream(new UrlFacade().fetch(url))));
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
