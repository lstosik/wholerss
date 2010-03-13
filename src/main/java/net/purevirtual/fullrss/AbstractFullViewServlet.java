package net.purevirtual.fullrss;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.purevirtual.fullrss.dao.FeedDao;
import net.purevirtual.fullrss.entity.Feed;
import net.purevirtual.fullrss.facade.FeedFacade;
import org.apache.wicket.Application;

abstract public class AbstractFullViewServlet extends HttpServlet {

	@Override
	public void init() {
		String filterName = this.getServletConfig().getInitParameter("filterName");
		/**
		 * Application.get() didn't work
		 * BTW it is stored in servletContext attribute "wicket:${filterName}"
		 */
		FullRssApp app = (FullRssApp) Application.get(filterName);
		Injector injector = app.getInjector();
		injector.injectMembers(this);
	}

	public AbstractFullViewServlet() {
	}
	@Inject
	FeedDao feedDao;
	@Inject
	FeedFacade feedFacade;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String pathInfo = request.getPathInfo();
			if (pathInfo == null) {
			}
			Long feedId = Long.parseLong("0" + pathInfo.replaceAll("[^0-9]", ""));
			Feed f = feedDao.findById(feedId);
			response.setContentType("application/xhtml+xml");
			response.setCharacterEncoding("utf-8");
			byte[] bytes = this.getCachedResponse(f);
			if (bytes == null) {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				SyndFeed full = feedFacade.getFull(f);
				full.setFeedType(this.getFeedType());
				full.setEncoding("utf-8");
				new SyndFeedOutput().output(full, new OutputStreamWriter(byteArrayOutputStream, "utf-8"));
				bytes = byteArrayOutputStream.toByteArray();
				this.updateFeedEntity(f, bytes);
			}
			response.getOutputStream().write(bytes);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(AbstractFullViewServlet.class.getName()).log(Level.SEVERE, null, ex);
		} catch (FeedException ex) {
			Logger.getLogger(AbstractFullViewServlet.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	abstract protected String getFeedType();

	abstract protected void updateFeedEntity(Feed f, byte[] toByteArray);

	abstract protected byte[] getCachedResponse(Feed feed);
}
