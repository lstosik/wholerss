package net.purevirtual.fullrss;

import java.util.Enumeration;
import net.purevirtual.fullrss.facade.FeedFacade;
import net.purevirtual.fullrss.pages.Hello;
import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.session.ISessionStore;

public class FullRssApp extends WebApplication {

	public FullRssApp() {
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class getHomePage() {
		return Hello.class;
	}

	@Override
	protected void init() {
		super.init();
		if (this.getServletContext().getServerInfo().contains("Development")) {
			System.setProperty("wicket.configuration", DEVELOPMENT);
		} else {
			System.setProperty("wicket.configuration", DEPLOYMENT);
		}
		getResourceSettings().setResourcePollFrequency(null);
		new FeedFacade().addDefaultFeeds();
	}

	@Override
	protected ISessionStore newSessionStore() {
		return new HttpSessionStore(this);
	}

	private void printSystemProperties() {
		Enumeration<String> attributeNames = this.getServletContext().getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			System.err.println(attributeName + " = " + this.getServletContext().getAttribute(attributeName));
		}
	}
	
}
