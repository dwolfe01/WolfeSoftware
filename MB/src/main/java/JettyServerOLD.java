//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.eclipse.jetty.server.Handler;
//import org.eclipse.jetty.server.NCSARequestLog;
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.server.handler.DefaultHandler;
//import org.eclipse.jetty.server.handler.HandlerCollection;
//import org.eclipse.jetty.server.handler.RequestLogHandler;
//import org.eclipse.jetty.servlet.ServletHandler;
//import org.eclipse.jetty.servlet.ServletHolder;
//
//public class JettyServerOLD {
//
//	Server server;
//
//	public static void main(String[] args) throws Exception {
//		new JettyServerOLD().go();
//	}
//
//	public void go() throws Exception {
//		server = new Server(8099);
//		ServletHandler servletHandler = new ServletHandler();
//		servletHandler.addServletWithMapping(new ServletHolder(new HelloWorld()), "/helloworld");
//		servletHandler.addServletWithMapping(new ServletHolder(new StatsServlet()), "/stats");
//		
//		// Handler Structure
//        HandlerCollection handlers = new HandlerCollection();
//        handlers.setHandlers(new Handler[] { servletHandler, new DefaultHandler(), getLoggingHandler()  });
//		
//		server.setHandler(handlers);
//		server.start();
//	}
//	
//	public RequestLogHandler getLoggingHandler(){
//		// === jetty-requestlog.xml ===
//        NCSARequestLog requestLog = new NCSARequestLog();
//        requestLog.setFilename("yyyy_mm_dd.request.log");
//        requestLog.setFilenameDateFormat("yyyy_MM_dd");
//        requestLog.setRetainDays(90);
//        requestLog.setAppend(true);
//        requestLog.setExtended(true);
//        requestLog.setLogCookies(false);
//        requestLog.setLogTimeZone("GMT");
//        RequestLogHandler requestLogHandler = new RequestLogHandler();
//        requestLogHandler.setRequestLog(requestLog);
//        return requestLogHandler;
//	}
//
//	public void stop() throws Exception {
//		server.stop();
//	}
//
//}
//
//@SuppressWarnings("serial")
//class StatsServlet extends HttpServlet {
//
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		response.setContentType("text/html");
//		response.setStatus(HttpServletResponse.SC_OK);
//		response.getWriter().println("<html><body>");
//		response.getWriter().println("These are the stats");
//		response.getWriter().println("</body></html>");
//	}
//}
//
//
//
