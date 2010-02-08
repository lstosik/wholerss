package net.purevirtual.fullrss;

import com.sun.syndication.io.FeedException;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedOutput;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.*;
import net.purevirtual.fullrss.entity.Feed;
import net.purevirtual.fullrss.facade.FeedFacade;

public class AtomFullViewServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		//PrintWriter out = response.getWriter();
		try {
			String pathInfo = request.getPathInfo();
			if (pathInfo == null) {
			}
			Long feedId = Long.parseLong("0" + pathInfo.replaceAll("[^0-9]", ""));
			Feed f = new FeedFacade().getFeedById(feedId);
			response.setContentType("application/xhtml+xml");
			response.setCharacterEncoding("utf-8");
			Calendar now = Calendar.getInstance();
			now.add(Calendar.HOUR, -1);
			if (!f.getCachedAtomAt().before(now.getTime())) {
				response.getOutputStream().write(f.getCachedAtom());
			} else {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				SyndFeed full = new FeedFacade().getFull(f);
				full.setFeedType("atom_1.0");
				full.setEncoding("utf-8");
				
				new SyndFeedOutput().output(full, new OutputStreamWriter(byteArrayOutputStream, "utf-8"));
				EntityManager em = EMF.get().createEntityManager();
				EntityTransaction transaction = em.getTransaction();
				transaction.begin();
				f.setCachedAtom(byteArrayOutputStream.toByteArray());
				f.setCachedAtomAt(new Date());
				em.merge(f);
				transaction.commit();
				response.getOutputStream().write(byteArrayOutputStream.toByteArray());
			}
			
			
			
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(AtomFullViewServlet.class.getName()).log(Level.SEVERE, null, ex);
		} catch (FeedException ex) {
			Logger.getLogger(AtomFullViewServlet.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
