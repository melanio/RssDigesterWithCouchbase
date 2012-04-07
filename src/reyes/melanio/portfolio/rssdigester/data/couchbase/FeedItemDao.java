package reyes.melanio.portfolio.rssdigester.data.couchbase;

import java.util.List;
import java.util.concurrent.TimeUnit;

import reyes.melanio.portfolio.rssdigester.data.FeedItem;

public class FeedItemDao {

	private CouchbaseClientUtil clientUtil = null;

	public FeedItemDao() {

	}

	public void setClientUtil(CouchbaseClientUtil clientUtil) {
		this.clientUtil = clientUtil;
	}

	public void insertFeedItem(FeedItem feedItem) {
		clientUtil.getClient().set(feedItem.getLink(), 0, feedItem);
	}

	public void updateFeedItem(FeedItem feedItem) {
		clientUtil.getClient().replace(feedItem.getLink(), 0, feedItem);
	}

	public FeedItem getFeedItem(String key) {
		return (FeedItem) clientUtil.getClient().get(key);
	}

	public void deleteFeedItem(FeedItem feedItem) {
		clientUtil.getClient().delete(feedItem.getLink());
	}

	public void saveFeedItems(List<FeedItem> feedItems) {
		for (FeedItem feedItem : feedItems) {
			FeedItem existingFeedItem = getFeedItem(feedItem.getLink());
			if (existingFeedItem != null) {
				if (existingFeedItem.getPubDate().compareTo(
						feedItem.getPubDate()) < 0) {
					updateFeedItem(feedItem);
				}
			} else {
				insertFeedItem(feedItem);
			}

		}
	}

	public void shutdownClient() {
		this.clientUtil.getClient().shutdown(60, TimeUnit.SECONDS);
	}
}
