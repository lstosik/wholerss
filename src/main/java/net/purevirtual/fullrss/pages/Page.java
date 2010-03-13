package net.purevirtual.fullrss.pages;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;
import java.util.List;
import java.util.logging.Logger;
import net.purevirtual.fullrss.dao.FeedDao;
import net.purevirtual.fullrss.entity.Feed;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class Page extends WebPage {
	@Inject
	FeedDao feedDao;
	public Page() {
		UserService userService = UserServiceFactory.getUserService();
		if (!userService.isUserLoggedIn()) {
			add(new ExternalLink("loginoutLink", userService.createLoginURL("/"), "Login"));
		} else {
			add(new ExternalLink("loginoutLink", userService.createLogoutURL("/"), "Logout"));
		}
		List<Feed> feedsList = feedDao.getAll();
		add(new ListView<Feed>("feedsList", feedsList) {
			@Override
			protected void populateItem(final ListItem<Feed> listItem) {
				final Feed feed = (Feed) listItem.getModelObject();
				listItem.add(new Label("name", feed.getName()));
				listItem.add(new Label("url", feed.getUrl()));
			}
		});

	}

	protected Logger getLogger() {
		return Logger.getLogger(this.getClass().getName());
	}

	@Override
	public final String getMarkupType() {
		return "xhtml";
	}

	@Override
	protected final void configureResponse() {
		super.configureResponse();
		getResponse().setContentType("text/html");
	}
}
