package reyes.melanio.portfolio.rssdigester.data.couchbase;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;

public class CouchbaseClientUtil {

	private String[] uris;
	private CouchbaseClient client = null;

	public CouchbaseClientUtil() {

	}

	public void setUris(String[] uris) {
		this.uris = uris;
	}

	private void connect() {
		try {
			List<URI> baseURIs = new ArrayList<URI>();
			for (String uri : uris) {
				URI base = new URI(String.format("http://%s:8091/pools", uri));
				baseURIs.add(base);
			}
			CouchbaseConnectionFactory cf = new CouchbaseConnectionFactory(
					baseURIs, "default", "");
			client = new CouchbaseClient((CouchbaseConnectionFactory) cf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CouchbaseClient getClient() {
		if (client == null) {
			connect();
		}
		return client;
	}

}
