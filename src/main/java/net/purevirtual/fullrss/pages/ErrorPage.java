package net.purevirtual.fullrss.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.annotation.mount.MountPath;
import org.wicketstuff.annotation.strategy.MountMixedParam;

@MountPath(path = "/error")
@MountMixedParam(parameterNames = {"page"})
public class ErrorPage extends Page {

	public ErrorPage(PageParameters pageParameters) {
		super();
		add(new Label("message", "strona:"+pageParameters.getString("page")));
	}
}
