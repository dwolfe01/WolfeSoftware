import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SimpleElasticSearchClient {

	private final static String URL = "http://172.20.72.78:9200/_search";
	private final static String startTimestamp = "2014-08-05T10:00:00";
	private final static String endTimestamp = "2014-08-05T10:15:00";

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		SimpleElasticSearchClient client = new SimpleElasticSearchClient();
		String query = FileUtils
				.readFileToString(new File(
						"/Users/dwolfe/development/git/WolfeSoftware/AnywhereArticleTest/src/main/resources/query.json"));
		String queryWithParams = String.format(query, startTimestamp,
				endTimestamp);
		System.out.println(queryWithParams);
		ObjectNode resultSet = client
				.executeFieldTermSearchWithTimeStampFilter(queryWithParams);
		System.out.println(resultSet.toString());
		outputResultSet(resultSet);
	}

	private static void outputResultSet(ObjectNode resultSet)
			throws IOException {
		JsonNode hitsNode = resultSet.get("hits").get("hits");
		System.out.println(hitsNode.size());
		Iterator<JsonNode> i = hitsNode.iterator();
		File output = new File(
				"/Users/dwolfe/development/git/WolfeSoftware/AnywhereArticleTest/src/main/resources/requests.log");
		List<String> requests = new ArrayList<String>();
		while (i.hasNext()) {
			// get the fields from this result
			JsonNode fields = i.next().get("fields");
			String requestString = fields.get("request").toString();
			String request = requestString.substring(2,
					requestString.length() - 2);
			System.out.println(request);
			requests.add("http://10.6.2.190:9090" + request);
		}
		FileUtils.writeLines(output, requests);
	}

	public ObjectNode executeFieldTermSearchWithTimeStampFilter(String query)
			throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(URL);
		post.setEntity(new StringEntity(query));
		HttpResponse response = httpClient.execute(post);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode root = (ObjectNode) mapper.readTree(response.getEntity()
				.getContent());
		return root;
	}
}
