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

	public static void main(String[] args) throws Exception {
		String defaultUrl = "http://10.6.203.39:8099/enhanced/doi/%s/";
		if (args != null && args.length > 0 && args[0] != null) {
			defaultUrl = args[0];
		}
		int numberOfThreads = 10;
		if (args != null && args.length > 1 && args[1] != null) {
			numberOfThreads = Integer.parseInt(args[1]);
		}
		long waitTime = 0;
		if (args != null && args.length > 2 && args[2] != null) {
			waitTime = Long.parseLong(args[2]);
		}
		Random random = new Random();
		ExecutorService executor = Executors
				.newFixedThreadPool(numberOfThreads);
		for (int x = 0; x < numberOfThreads; x++) {
			String url = String.format(defaultUrl,
					doi[random.nextInt(doi.length)]);
			executor.execute(new Request().setUrl(url).setWaitTime(waitTime));
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		System.out.println("Finished all threads");
	}
}

class Request implements Runnable {

	private String url;
	private long waitTime;

	public Request setUrl(String url) {
		this.url = url;
		return this;
	}

	public Request setWaitTime(long waitTime) {
		this.waitTime = waitTime;
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
				Thread.sleep(waitTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
