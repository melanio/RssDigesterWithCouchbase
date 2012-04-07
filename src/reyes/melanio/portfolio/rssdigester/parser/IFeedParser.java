package reyes.melanio.portfolio.rssdigester.parser;

import java.util.List;

import org.jdom.Document;

import reyes.melanio.portfolio.rssdigester.data.FeedItem;
import reyes.melanio.portfolio.rssdigester.util.DateParser;

public interface IFeedParser {

	public String getFormat();

	public boolean checkForamt(Document document);

	public List<FeedItem> parse(Document document);

	public void setDateParser(DateParser dateParser);
}


