package com.wolfesoftware.gds.users.sessionmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;

import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wolfesoftware.gds.users.sessionmanager.SessionManager;

import spark.Request;
import spark.Session;

public class SessionManagerTest {
	
	@Mock
	Request request;
	@Mock
	Session session;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		when(request.session(true)).thenReturn(session);
	}
	
	@Test
	public void shouldEstablishShiroSessionAndBindIdToHttpSession() {
		String sessionId = setupSession();
		verify(session).attribute("shiro_session_id", sessionId);
		//return an existing session
		Subject subject = SessionManager.getSubject(request);
		String shouldbeTheSameSessionId = subject.getSession().getId().toString();
		assertEquals(sessionId, shouldbeTheSameSessionId);
	}
	
	@Test
	public void shouldFailLogin() {
		setupSession();
		SessionManager.login("baduser", "badpass", request);
		assertFalse(SessionManager.isLoggedIn(request));
	}

	@Test
	public void shouldLogin() {
		setupSession();
		SessionManager.login("username", "password", request);
		assertTrue(SessionManager.isLoggedIn(request));
	}
	
	@Test
	public void shouldSetAttributes() {
		setupSession();
		Object o = new Object();
		SessionManager.setAttribute("key", o, request);
		assertEquals(o,SessionManager.getAttribute("key", request));
	}
	
	private String setupSession() {
		//create a new session
		Subject subject = SessionManager.getSubject(request);
		String sessionId = subject.getSession().getId().toString();
		when(session.attribute("shiro_session_id")).thenReturn(sessionId);
		return sessionId;
	}

}
