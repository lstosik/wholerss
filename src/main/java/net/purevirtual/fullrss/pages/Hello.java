package net.purevirtual.fullrss.pages;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import net.purevirtual.fullrss.Page;
import net.purevirtual.fullrss.Util;
import net.purevirtual.fullrss.facade.FeedFacade;
import net.purevirtual.fullrss.facade.UrlFacade;
import org.apache.wicket.markup.html.basic.Label;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Hello extends Page {

	public Hello() throws XPathExpressionException, FeedException {

		add(new Label("message", "Hello World!"));
	}
}
