package net.purevirtual.fullrss.entity;

import com.google.appengine.api.datastore.Blob;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;

@Entity
public class Feed {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Link url;
	@Basic
	private List<String> contentInclude;
	@Basic
	private List<String> contentExclude;
	@Enumerated
	private RuleType contentRuleType;

	private Blob cachedRSS;
	private Date cachedRSSAt;
	private Blob cachedAtom;
	private Date cachedAtomAt;

	public Feed() {
		this.contentExclude = new ArrayList<String>();
		this.contentInclude = new ArrayList<String>();
		this.cachedAtomAt = new Date();
		this.cachedAtomAt.setTime(0);
		this.cachedRSSAt = new Date();
		this.cachedRSSAt.setTime(0);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	

	public byte[] getCachedRSS() {
		return cachedRSS.getBytes();
	}

	public void setCachedRSS(byte[] cachedRSS) {
		this.cachedRSS = new Blob(cachedRSS);
	}

	public Date getCachedRSSAt() {
		return cachedRSSAt;
	}

	public void setCachedRSSAt(Date cachedRSSAt) {
		this.cachedRSSAt = cachedRSSAt;
	}

	public byte[] getCachedAtom() {
		return cachedAtom.getBytes();
	}

	public void setCachedAtom(byte[] cachedAtom) {
		this.cachedAtom = new Blob(cachedAtom);
	}

	public Date getCachedAtomAt() {
		return cachedAtomAt;
	}

	public void setCachedAtomAt(Date cachedAtomAt) {
		this.cachedAtomAt = cachedAtomAt;
	}

	

	public enum RuleType {
		CSS, XPath, JS, XSLT;
	}
}
