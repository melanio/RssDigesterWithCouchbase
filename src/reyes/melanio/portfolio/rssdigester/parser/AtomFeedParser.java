package reyes.melanio.portfolio.rssdigester.parser;


import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import reyes.melanio.portfolio.rssdigester.data.FeedItem;
import reyes.melanio.portfolio.rssdigester.util.DateParser;

public class AtomFeedParser implements IFeedParser {

	private DateParser dateParser;
	private String format = "atom";
	private static final String ATOM_NAMESPACE = "http://www.w3.org/2005/Atom";

	private AtomFeedParser() {

	}

	public String getFormat() {
		return format;
	}

	public boolean checkForamt(Document document) {
		boolean result = false;
		String namespace = document.getRootElement().getNamespace().getURI();
		if (ATOM_NAMESPACE.equals(namespace)) {
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
		List<Element> children = root.getChildren();
		List<FeedItem> result = new ArrayList<FeedItem>();
		for (Element child : children) {
			String name = child.getName().toLowerCase();
			if ("entry".equals(name)) {
				result.add(parseEntryElement(child));
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private FeedItem parseEntryElement(Element entry) {
		FeedItem atomEntry = new FeedItem();
		List<Element> atomEntryFields = entry.getChildren();
		for (Element child : atomEntryFields) {
			String name = child.getName().toLowerCase();
			if ("title".equals(name)) {
				atomEntry.setTitle(child.getText());
			} else if ("link".equals(name)) {
				atomEntry.setLink(child.getAttributeValue("href"));
			} else if ("summary".equals(name) || "content".equals(name)) {
				atomEntry.setContent(parseContent(child));
			} else if ("updated".equals(name)) {
				atomEntry.setPubDate(dateParser.parse(child.getText()));
			}
		}
		return atomEntry;
	}

	@SuppressWarnings("unchecked")
	private String parseContent(Element content) {
		String type = content.getAttributeValue("type");
		String text = content.getText().trim();
		if ("xhtml".equals(type)) {
			List<Element> children = content.getChildren();
			for (Element contentChild : children) {
				text = contentChild.getValue().trim();
			}
		}

		return text;
	}

	public void setDateParser(DateParser dateParser) {
		this.dateParser = dateParser;
	}
}