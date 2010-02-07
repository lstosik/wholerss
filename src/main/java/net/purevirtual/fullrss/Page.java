package net.purevirtual.fullrss;

import java.util.logging.Logger;
import org.apache.wicket.markup.html.WebPage;

public class Page extends WebPage{
	protected Logger getLogger() {
		return Logger.getLogger(this.getClass().getName());
	}
}
