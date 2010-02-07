package net.purevirtual.fullrss.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Enumerated;

@Entity
public class Feed {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key id;
	private String name;
	private Link url;
	private List<String> contentInclude;
	private List<String> contentExclude;
	@Enumerated
	private RuleType contentRuleType;

	public Feed() {
		this.contentExclude = new ArrayList<String>();
		this.contentInclude = new ArrayList<String>();
	}
	/**
	 * @return the id
	 */
	public Key getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url.getValue();
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = new Link(url);
	}

	/**
	 * @return the contentInclude
	 */
	public List<String> getContentInclude() {
		return contentInclude;
	}

	/**
	 * @param contentInclude the contentInclude to set
	 */
	public void setContentInclude(List<String> contentInclude) {
		this.contentInclude = contentInclude;
	}

	/**
	 * @return the contentExclude
	 */
	public List<String> getContentExclude() {
		return contentExclude;
	}

	/**
	 * @param contentExclude the contentExclude to set
	 */
	public void setContentExclude(List<String> contentExclude) {
		this.contentExclude = contentExclude;
	}

	/**
	 * @return the contentRuleType
	 */
	public RuleType getContentRuleType() {
		return contentRuleType;
	}

	/**
	 * @param contentRuleType the contentRuleType to set
	 */
	public void setContentRuleType(RuleType contentRuleType) {
		this.contentRuleType = contentRuleType;
	}

	

	public enum RuleType {

		CSS, XPath, JS, XSLT;
	}

	
	/*public Feed(URL url, String content) {
	this.url = url.toString();
	setContent(content);
	fetchedAt = Calendar.getInstance().getTime();
	}*/
}
