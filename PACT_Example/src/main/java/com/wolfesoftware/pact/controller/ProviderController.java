package com.wolfesoftware.pact.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {
	
	@RequestMapping(value = "/getUser")
	public String getUser(HttpServletResponse response) {
		response.setContentType("text/plain");
		return "dan,wolfe";
	}
	
}
