package net.purevirtual.fullrss;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.Enumeration;
import javax.persistence.EntityManager;
import net.purevirtual.fullrss.facade.FeedFacade;
import net.purevirtual.fullrss.pages.Hello;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.session.ISessionStore;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

public class FullRssApp extends WebApplication {
	private Injector injector;
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
		System.out.println("app: "+Thread.currentThread().getId());
		if (this.getServletContext().getServerInfo().contains("Development")) {
			System.setProperty("wicket.configuration", DEVELOPMENT);
		} else {
			System.setProperty("wicket.configuration", DEPLOYMENT);
		}
		System.out.println("old:"+this.getServletContext().getAttribute("injector"));
		injector = Guice.createInjector(new GuiceModule());
		this.getServletContext().setAttribute("injector", injector);
		getResourceSettings().setResourcePollFrequency(null);

		

		EntityManager entityManager = injector.getInstance(EntityManager.class);

		addComponentInstantiationListener(new GuiceComponentInjector(this, injector));
		injector.getInstance(FeedFacade.class).addDefaultFeeds();
		new AnnotatedMountScanner().scanPackage("net.purevirtual.fullrss.pages").mount(this);
	}




    @Override
    public HttpSessionStore newSessionStore(){
       return new HttpSessionStore(this);
    }

	

	private void printSystemProperties() {
		Enumeration<String> attributeNames = this.getServletContext().getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			System.err.println(attributeName + " = " + this.getServletContext().getAttribute(attributeName));
		}
	}

	public Injector getInjector() {
		return injector;
	}

}
