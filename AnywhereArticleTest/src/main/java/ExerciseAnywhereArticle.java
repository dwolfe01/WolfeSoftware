import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;

public class ExerciseAnywhereArticle {

	private static String[] doi = { "10.1046/j.1563-2571.2001.01024.x",
			"10.1111/acel.12163", "10.1111/acel.12160", "10.1111/acel.12164" };

	private static final int numberOfThreads = 10;

	public static void main(String[] args) throws Exception {
		Random random = new Random();
		ExecutorService executor = Executors
				.newFixedThreadPool(numberOfThreads);
		for (int x = 0; x < numberOfThreads; x++) {
			String url = String.format(
					args[0],
					doi[random.nextInt(doi.length)]);
			executor.execute(new Request().setUrl(url));
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		System.out.println("Finished all threads");
	}
}

class Request implements Runnable {

	private String url;

	public Request setUrl(String url) {
		this.url = url;
		return this;
	}

	@Override
	public void run() {
		while (true) {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(
					ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
			HttpResponse response;
			try {
				long startTime = System.currentTimeMillis();
				response = httpClient.execute(new HttpGet(url));
				System.out.println(response.getStatusLine() + " " + url
						+ " took " + (System.currentTimeMillis() - startTime));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
