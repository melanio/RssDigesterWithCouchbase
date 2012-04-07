package reyes.melanio.portfolio.rssdigester.data;

import java.net.MalformedURLException;
import java.net.URL;

public class Feed {

	private URL url;

	public Feed() {

	}

	public Feed(String URL) throws MalformedURLException {
		setUrlString(URL);
	}

	public void setURL(String URL) throws MalformedURLException {
		setUrlString(URL);
	}

	public URL getURL() {
		return url;
	}

	private void setUrlString(String URL) throws MalformedURLException {
		url = new URL(URL);
	}

}

