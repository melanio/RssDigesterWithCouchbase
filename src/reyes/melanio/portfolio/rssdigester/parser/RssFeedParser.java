package reyes.melanio.portfolio.rssdigester.parser;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import reyes.melanio.portfolio.rssdigester.data.FeedItem;
import reyes.melanio.portfolio.rssdigester.util.DateParser;

public class RssFeedParser implements IFeedParser {

	private String format = "rss";

	private DateParser dateParser;

	private RssFeedParser() {

	}

	public String getFormat() {
		return format;
	}

	public boolean checkForamt(Document document) {
		boolean result = false;
		String format = document.getRootElement().getName().toLowerCase();
		if ("rss".equals(format)) {
			result = true;
		}
		return result;
	}

	public List<FeedItem> parse(Document document) {
		Element root = document.getRootElement();
		return parseRootElement(root);
	}

	@SuppressWarnings("unchecked")
	private List<FeedItem> parseRootElement(Element root) {
		List<FeedItem> result = new ArrayList<FeedItem>();
		List<Element> children = root.getChildren();
		for (Element child : children) {
			String name = child.getName().toLowerCase();
			if ("channel".equals(name)) {
				result.addAll(parseChannelElement(child));
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<FeedItem> parseChannelElement(Element channel) {
		List<FeedItem> result = new ArrayList<FeedItem>();
		List<Element> channelChildren = channel.getChildren();
		for (Element child : channelChildren) {
			String name = child.getName().toLowerCase();
			if ("item".equals(name)) {
				result.add(parseItemElement(child));
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private FeedItem parseItemElement(Element item) {
		FeedItem rssItem = new FeedItem();
		List<Element> rssItemFields = item.getChildren();
		for (Element child : rssItemFields) {
			String name = child.getName().toLowerCase();
			if ("title".equals(name)) {
				rssItem.setTitle(child.getText().trim());
			} else if ("link".equals(name)) {
				rssItem.setLink(child.getText().trim());
			} else if ("description".equals(name)) {
				rssItem.setContent(child.getText().trim());
			} else if ("pubdate".equals(name)) {
				rssItem.setPubDate(dateParser.parse(child.getText()));
			}
		}
		return rssItem;
	}

	public void setDateParser(DateParser dateParser) {
		this.dateParser = dateParser;
	}
}
