package reyes.melanio.portfolio.rssdigester.data;

import java.io.Serializable;
import java.util.Date;

public class FeedItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private String title;
	private String link;
	private String content;
	private Date pubdate;

	public FeedItem() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPubDate() {
		return pubdate;
	}

	public void setPubDate(Date date) {
		this.pubdate = date;
	}

}

