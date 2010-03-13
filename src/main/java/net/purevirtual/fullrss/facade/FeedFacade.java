package net.purevirtual.fullrss.facade;

import com.google.inject.Inject;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedInput;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.purevirtual.fullrss.dao.FeedDao;
import net.purevirtual.fullrss.entity.Feed;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FeedFacade {

	@Inject
	FeedDao feedDao;
	@Inject
	UrlFacade urlFacade;
	public SyndFeed getFull(Feed f) {
		assert (f != null);
		try {

			SyndFeedInput input = new SyndFeedInput();
			SyndFeed source = urlFacade.fetchFeed(new URL(f.getUrl()));
			SyndFeed feed = new SyndFeedImpl();
			feed.setTitle(source.getTitle());
			feed.setLink(source.getLink());
			feed.setDescription(source.getDescription());
			List entries = new ArrayList();
			for (Object object : source.getEntries()) {
				try {
					final SyndEntry entry = (SyndEntry) object;
					URL link = new URL(entry.getLink());
					Document document = this.getDocument(link);
					Elements exclude = new Elements();
					for (String rule : f.getContentExclude()) {
						exclude.addAll(document.select(rule));
					}
					for (Element element : exclude) {
						element.html("");
					}
					Elements include = new Elements();
					for (String rule : f.getContentInclude()) {
						include.addAll(document.select(rule));
					}
					SyndEntry fullEntry = new SyndEntryImpl();
					fullEntry.setTitle(entry.getTitle());
					fullEntry.setLink(entry.getLink());
					fullEntry.setPublishedDate(entry.getPublishedDate());
					if (include.size() > 0) {
						SyndContent description = new SyndContentImpl();
						description.setType("text/html");
						String value = "";
						for (Element element : include) {
							value += element.outerHtml();
						}
						description.setValue(value);
						fullEntry.setDescription(description);
					} else {
						fullEntry.setDescription(entry.getDescription());
					}
					entries.add(fullEntry);
				} catch (MalformedURLException ex) {
					Logger.getLogger(FeedFacade.class.getName()).log(Level.SEVERE, null, ex);
				} catch (IOException ex) {
					Logger.getLogger(FeedFacade.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			feed.setEntries(entries);
			return feed;
		} catch (MalformedURLException ex) {
			Logger.getLogger(FeedFacade.class.getName()).log(Level.SEVERE, null, ex);
			throw new RuntimeException(ex);
		}

	}

	private Document getDocument(URL url) {
		try {
			byte[] content;
			try {
				content = urlFacade.fetch(url);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			String html = new String(content, "ISO-8859-1");
			Document document = Jsoup.parse(html);
			//HACK:
			String parts = "ISO-8859-1";
			Elements contentTypeElements = document.select("meta[http-equiv=Content-Type]");
			if (contentTypeElements.size() > 0) {
				String contentType = contentTypeElements.last().attr("content");
				parts = contentType.substring(contentType.indexOf("=") + 1).toUpperCase();
			}
			if (!"ISO-8859-1".equals(parts)) {
				html = new String(content, parts);
				document = Jsoup.parse(html);
			}
			return document;
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}
	}

	public void addDefaultFeeds() {
		Feed f = new Feed();
		f.setContentRuleType(Feed.RuleType.CSS);
		List exclude = new ArrayList();
		List include = new ArrayList();
		exclude.add("div.bx_opt");
		exclude.add("script");
		exclude.add("p.tag");
		exclude.add("div.bxSowa");
		include.add("#intertext1");
		f.setContentInclude(include);
		f.setContentExclude(exclude);
		f.setUrl("http://finanse.wp.pl/rss.xml");
		f.setId(1L);
		feedDao.persist(f);

		f = new Feed();
		f.setContentRuleType(Feed.RuleType.CSS);
		exclude = new ArrayList();
		include = new ArrayList();
		include.add("img[src^=http://www.explosm.net/db/files/Comics/]");
		f.setContentInclude(include);
		f.setContentExclude(exclude);
		f.setUrl("http://feeds.feedburner.com/Explosm");
		f.setId(2L);
		feedDao.persist(f);

		f = new Feed();
		f.setContentRuleType(Feed.RuleType.CSS);
		exclude = new ArrayList();
		include = new ArrayList();
		include.add("img[src^=http://buttersafe.com/comics/]");
		f.setContentInclude(include);
		f.setContentExclude(exclude);
		f.setUrl("http://feeds.feedburner.com/Buttersafe");
		f.setId(3L);
		feedDao.persist(f);

	}
}
