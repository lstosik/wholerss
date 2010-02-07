package net.purevirtual.fullrss;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;



public class Util {
	static public XPathExpression compileXPath(String expression) throws XPathExpressionException  {
		return getXPath().compile(expression);
	}
	static protected XPath	getXPath() {
		XPathFactory factory = XPathFactory.newInstance();
		return factory.newXPath();
	}
}
