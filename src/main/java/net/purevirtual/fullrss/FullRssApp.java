package net.purevirtual.fullrss;
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
      getResourceSettings().setResourcePollFrequency(null);
    }

    @Override
    protected ISessionStore newSessionStore() {
      return new HttpSessionStore(this);
    }

}
