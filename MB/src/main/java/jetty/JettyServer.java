package jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServer {

	public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        WebAppContext webapp = getWebAppContextHandler();
        HandlerCollection handlers = new HandlerCollection();
        handlers.setHandlers(new Handler[] { webapp, getLoggingHandler()  });
        server.setHandler(handlers);
        server.start();
        server.join();
	}

	private static WebAppContext getWebAppContextHandler() {
		WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setWar("target/MB.war");
		return webapp;
	}
	
	public static RequestLogHandler getLoggingHandler(){
		// === jetty-requestlog.xml ===
        NCSARequestLog requestLog = new NCSARequestLog();
        requestLog.setFilename("yyyy_mm_dd.request.log");
        requestLog.setFilenameDateFormat("yyyy_MM_dd");
        requestLog.setRetainDays(90);
        requestLog.setAppend(true);
        requestLog.setExtended(true);
        requestLog.setLogCookies(false);
        requestLog.setLogTimeZone("GMT");
        RequestLogHandler requestLogHandler = new RequestLogHandler();
        requestLogHandler.setRequestLog(requestLog);
        return requestLogHandler;
	}


}
