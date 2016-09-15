package org.eclipse.jetty.embedded;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
public class JettyServer {

	public static void main(String[] args) throws Exception {
		new JettyServer().go();
	}
	
	public void go() throws Exception{
		Server server = new Server(8099);
		ServletHandler handler = new ServletHandler();
		server.setHandler(handler);
		handler.addServletWithMapping(new ServletHolder(new SleepyServlet(10)), "/wait1");
		handler.addServletWithMapping(new ServletHolder(new SleepyServlet(20)), "/wait2");
		handler.addServletWithMapping(new ServletHolder(new SleepyServlet(30)), "/wait3");
		server.start();
	}

}

@SuppressWarnings("serial")
class SleepyServlet extends HttpServlet
{
	private int sleepTime;

	public SleepyServlet(int sleepTime){
		this.sleepTime = sleepTime;
	}
	
    @Override
    protected void doGet( HttpServletRequest request,
                          HttpServletResponse response ) throws ServletException,
                                                        IOException
    {
    	try {
			TimeUnit.MILLISECONDS.sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>"+ "This page waited" + sleepTime +  " millisecond(s)"  + "</h1>");
    }
}
