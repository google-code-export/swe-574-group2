package com.swe.accessibilty.authentication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.swe.accessibility.domain.proxy.LoginStatus;

public class AuthenticationSuccessHandler extends
		SimpleUrlAuthenticationSuccessHandler {

	private RequestCache requestCache = new HttpSessionRequestCache();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {

		clearAuthenticationAttributes(request);

		LoginStatus status = new LoginStatus();
		status.setLoggedIn(true);
		status.setUsername(authentication.getName());

		PrintWriter out = response.getWriter();
		response.addHeader("Access-Control-Allow-Origin", "http://localhost");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		// response.setContentType("text/json");
	
		out.write(String.valueOf(status.toString()));
	}

	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}

}
