package reyes.melanio.portfolio.rssdigester;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.protocol.DefaultProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

import reyes.melanio.portfolio.rssdigester.data.FeedItem;
import reyes.melanio.portfolio.rssdigester.data.couchbase.FeedItemDao;
import reyes.melanio.portfolio.rssdigester.parser.IFeedParser;
import reyes.melanio.portfolio.rssdigester.data.Feed;

public class RssDigester {

	private static Logger logger = Logger.getLogger(RssDigester.class);
	private IFeedParser[] parsers;
	private FeedItemDao feedItemDao;
	private Feed[] feeds;

	private RssDigester() {

	}

	public void setParsers(IFeedParser[] parsers) {
		this.parsers = parsers;
	}

	public void setFeedItemDao(FeedItemDao feedItemDao) {
		this.feedItemDao = feedItemDao;
	}

	public void setFeeds(Feed[] feeds) {
		this.feeds = feeds;
	}

	public void digestFeeds() {
		for (Feed feed : feeds) {
			Document document = getDocument(feed);
			IFeedParser parser = getParser(document);
			if (checkParser(parser)) {
				ArrayList<FeedItem> feedItems = (ArrayList<FeedItem>) parser
						.parse(document);
				if ( feedItems.size() > 0) {
					feedItemDao.saveFeedItems(feedItems);
				}
			}
			
		}

	}

	private Document getDocument(Feed feed) {
		Document document = null;
		int responseCode = 0;
		try {
			HttpClient client = new HttpClient();
			URI uri = feed.getURL().toURI();
			if ("feed".equals(uri.getScheme())) {
				Protocol feedProtocol = new Protocol("feed",
						new DefaultProtocolSocketFactory(), 80);
				Protocol.registerProtocol("feed", feedProtocol);
				client.getHostConfiguration().setHost(uri.getHost(), 80,
						feedProtocol);
			}

			HttpMethod getMethod = new GetMethod(uri.toString());

			responseCode = client.executeMethod(getMethod);

			if (responseCode != 404) {
				InputStream inputStream = getMethod.getResponseBodyAsStream();
				BufferedReader bufferedinStream = new BufferedReader(
						new InputStreamReader(inputStream));

				SAXBuilder builder = new SAXBuilder();
				document = builder.build(bufferedinStream);
			}

		} catch (Exception e) {
			logger.error("Failed to get feed document", e);
		}
		return document;
	}

	public IFeedParser getParser(Document document) {
		IFeedParser result = null;
		for (IFeedParser parser : parsers) {
			if (parser.checkForamt(document)) {
				result = parser;
				break;
			}
		}
		return result;
	}
	
	private boolean checkParser(IFeedParser parser) {
		boolean result = true;
		if (parser == null) {
			result = false;
			logger.error("No suitable parser found");
		}
		return result;
	}
	
	public void shutdownClient()
	{
		feedItemDao.shutdownClient();
	}

}
