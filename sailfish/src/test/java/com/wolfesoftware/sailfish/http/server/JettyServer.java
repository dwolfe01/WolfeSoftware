package com.wolfesoftware.sailfish.http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
public class JettyServer {
	
	Server server;

	public static void main(String[] args) throws Exception {
		new JettyServer().go();
	}
	
	public void go() throws Exception{
		server = new Server(8099);
		ServletHandler handler = new ServletHandler();
		server.setHandler(handler);
		handler.addServletWithMapping(new ServletHolder(new SleepyServlet(10)), "/wait1");
		handler.addServletWithMapping(new ServletHolder(new SleepyServlet(20)), "/wait2");
		handler.addServletWithMapping(new ServletHolder(new SleepyServlet(30)), "/wait3");
		handler.addServletWithMapping(new ServletHolder(new SleepyPostServlet(30)), "/post");
		server.start();
	}

	public void stop() throws Exception {
		server.stop();
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

@SuppressWarnings("serial")
class SleepyPostServlet extends HttpServlet
{
	private int sleepTime;

	public SleepyPostServlet(int sleepTime){
		this.sleepTime = sleepTime;
	}
	
    @Override
    protected void doPost( HttpServletRequest request,
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
        PrintWriter writer = response.getWriter();
        BufferedReader bufferedReader =  request.getReader() ; 
        char[] charBuffer = new char[128];
        int bytesRead;
        while ( (bytesRead = bufferedReader.read(charBuffer)) != -1 ) {
            writer.write(charBuffer, 0, bytesRead);
        }
    }
}
