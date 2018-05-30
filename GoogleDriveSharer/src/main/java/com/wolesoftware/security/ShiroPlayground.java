package com.wolesoftware.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

public class ShiroPlayground {

	private static final transient Logger log = LoggerFactory.getLogger(ShiroPlayground.class);

	public static void main(String[] args) {
		log.info("Apache Shiro");
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			// collect user principals and credentials in a gui specific manner
			// such as username/password html form, X509 certificate, OpenID, etc.
			// We'll use the username/password example here since it is the most common.
			UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");

			// this is all you have to do to support 'remember me' (no config - built in!):
			token.setRememberMe(true);

			try {
				currentUser.login(token);
				log.info( "User [" + currentUser.getPrincipal() + "] logged in successfully." );
				if ( currentUser.hasRole( "schwartz" ) ) {
				    log.info("May the Schwartz be with you!" );
				} else {
				    log.info( "Hello, mere mortal." );
				}
				if ( currentUser.isPermitted( "lightsaber:weild" ) ) {
				    log.info("You may use a lightsaber ring.  Use it wisely.");
				} else {
				    log.info("Sorry, lightsaber rings are for schwartz masters only.");
				}
				if ( currentUser.isPermitted( "winnebago:drive:eagle5" ) ) {
				    log.info("You are permitted to 'drive' the 'winnebago' with license plate (id) 'eagle5'.  " +
				                "Here are the keys - have fun!");
				} else {
				    log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
				}
			} catch (AuthenticationException ae) {
				log.info("Login failed");
			}
			currentUser.logout();
		}
	}

}
