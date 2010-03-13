package net.purevirtual.fullrss.pages;

import com.sun.syndication.io.FeedException;
import javax.xml.xpath.XPathExpressionException;
import org.apache.wicket.markup.html.basic.Label;


public class Hello extends Page {

	public Hello() throws XPathExpressionException, FeedException {
		super();
		add(new Label("message", "Hello World!"));
	}
}
