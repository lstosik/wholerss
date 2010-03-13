package net.purevirtual.fullrss.entity;

import com.google.appengine.api.datastore.Blob;
import java.net.URL;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Basic;

import com.google.appengine.api.datastore.Text;
import java.util.Calendar;
import java.util.Date;

@Entity
public class FetchUrlCache {

	@Id
	private String url;
	private Blob content;
	private Date fetchedAt;

	public FetchUrlCache() {
		
	}
	public FetchUrlCache(URL url, byte[] content) {
		this.url = url.toString();
		setContent(content);
		fetchedAt = Calendar.getInstance().getTime();
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the fetchedAt
	 */
	public Date getFetchedAt() {
		return fetchedAt;
	}

	/**
	 * @param fetchedAt the fetchedAt to set
	 */
	public void setFetchedAt(Date fetchedAt) {
		this.fetchedAt = fetchedAt;
	}

	/**
	 * @return the content
	 */
	public byte[] getContent() {
		return content.getBytes();
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(byte[] content) {
		this.content = new Blob(content);
	}
}
