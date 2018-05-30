package com.wolfesoftware.gds.sessionmanager;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolfesoftware.gds.GoogleDriveSharer;
import com.wolfesoftware.gds.configuration.Configuration;

import spark.Request;

public class SessionManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleDriveSharer.class);
	private static Factory<SecurityManager> factory = new IniSecurityManagerFactory(Configuration.get("credentials_folder") + "/shiro.ini");
	private static SecurityManager securityManager = factory.getInstance();
	static {
		SecurityUtils.setSecurityManager(securityManager);
	}

	public static Subject getSubject(Request request) {
		String shiroID = request.session(true).attribute("shiro_session_id");
		Subject subject = new Subject.Builder().sessionId(shiroID).buildSubject();
		request.session(true).attribute("shiro_session_id", subject.getSession().getId().toString());
		return subject;
	}

	public static void login(String username, String password, Request request) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
		getSubject(request).login(token);
		} catch (AuthenticationException ae) {
			LOGGER.info("Failed log in:" + username);
		}
		LOGGER.info("Logged in:" + username);
	}

	public static boolean isLoggedIn(Request request) {
		return getSubject(request).isAuthenticated();
	}

	public static void setAttribute(String key, Object value, Request request) {
		getSubject(request).getSession().setAttribute(key, value);
	}

	public static Object getAttribute(String key,Request request) {
		return getSubject(request).getSession().getAttribute(key);
	}

}
