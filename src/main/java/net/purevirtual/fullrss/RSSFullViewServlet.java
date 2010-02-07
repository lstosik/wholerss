package net.purevirtual.fullrss;

import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedOutput;
import java.io.*;
// DOM classes.
import java.util.logging.Level;
import java.util.logging.Logger;
//JAXP 1.1
import javax.xml.transform.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.*;
import net.purevirtual.fullrss.entity.Feed;
import net.purevirtual.fullrss.facade.FeedFacade;
import net.purevirtual.fullrss.facade.UrlFacade;
import org.xml.sax.InputSource;

public class RSSFullViewServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			response.setContentType("application/xhtml+xml");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			Feed f =new Feed();
			f.setContentRuleType(Feed.RuleType.CSS);
			List<String> exclude = new ArrayList<String>();
			/*
			exclude.add("div.bx_opt");
			exclude.add("script");
			exclude.add("p.tag");
			exclude.add("div.bxSowa");

			f.setContentExclude(exclude);

			List<String> include = new ArrayList<String>();
			include.add("#intertext1");
			f.setContentInclude(include);

			f.setUrl("http://finanse.wp.pl/rss.xml");*/

			/*exclude.add("div.bx_opt");
			exclude.add("script");
			exclude.add("p.tag");
			exclude.add("div.bxSowa");

			f.setContentExclude(exclude);*/

			List<String> include = new ArrayList<String>();
			
			include.add("img[src^=http://www.explosm.net/db/files/Comics/]");
			

			f.setContentInclude(include);

			f.setUrl("http://feeds.feedburner.com/Explosm");




			
			//System.out.println(feed);
			SyndFeed full = new FeedFacade().getFull(f);
			//System.out.println(full);
			full.setFeedType("rss_2.0");
			full.setEncoding("utf-8");
			//full.setFeedType("atom_1.0");
			new SyndFeedOutput().output(full, out);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(RSSFullViewServlet.class.getName()).log(Level.SEVERE, null, ex);
		} catch (FeedException ex) {
			Logger.getLogger(RSSFullViewServlet.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

/*
 * <rss version="2.0">
<channel>
<title>Finanse - Wirtualna Polska</title>
<link>http://finanse.wp.pl</link>
<description>Finanse - najwa?niejsze i najnowsze informacje ekonomiczne. Wiadomo?ci, komentarze, raporty i analizy, wideo. Prasa. Kalkulatory. Informacje z rynk?w. Po godzinach</description>
<language>pl</language>
<copyright>Copyright (c) 1995-2010 Wirtualna Polska</copyright>
<ttl>5</ttl>
<pubDate>Sun, 7 Feb 2010 17:49:29 +0100</pubDate>

<lastBuildDate>Sun, 7 Feb 2010 17:49:29 +0100</lastBuildDate>
<generator>Wirtualna Polska S.A.</generator>
<docs>http://finanse.wp.pl</docs>
<webMaster>Webmaster WP </webMaster>
<managingEditor>ManagingEditor WP </managingEditor>
<image>
<title>Wirtualna Polska S.A. - wp.pl</title>
<url>http://i.wp.pl/a/i/finanse/logozr/WP.gif</url>
<link>http://www.wp.pl</link>

<width>70</width>
<height>28</height>
</image>
<item>

</item>

 */
