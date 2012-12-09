package com.swe.accessibility.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swe.accessibility.domain.LoginStatus;
import com.swe.accessibility.domain.UserObject;

@Controller
@RequestMapping("/login")
public class LoginController {

	
	@Autowired
	@Qualifier("authenticationManager")
	AuthenticationManager authenticationManager;
	
	@Autowired
	RememberMeServices rememberMeService;

	  @RequestMapping(value="/status", method = RequestMethod.GET,produces="application/json")
	  public ResponseEntity<LoginStatus> getStatus(HttpServletRequest request) {
		
		SecurityContext context = (SecurityContext) request.getSession().getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		 LoginStatus status = new LoginStatus();
		if (context == null){
			status.setLoggedIn(false);
		    status.setUsername(null);
		}
		else{
			 Authentication auth = context.getAuthentication();
			   
			    if (auth != null && !auth.getName().equals("anonymousUser") && auth.isAuthenticated()) {
			      status.setLoggedIn(true);
			      status.setUsername(auth.getName());
			    } else {
			    	status.setLoggedIn(false);
				      status.setUsername(null);
			    }
		}
	   
	    
	    HttpHeaders responseHeaders = makeCORS();
	    return new ResponseEntity<LoginStatus>(status,responseHeaders,HttpStatus.OK);
	  }

//	  @RequestMapping(value="/sigin",method = {RequestMethod.POST,RequestMethod.OPTIONS,RequestMethod.GET},produces="application/json",headers={"Content-Type=application/json"})
//	  public ResponseEntity<LoginStatus> login(@RequestBody UserObject user, HttpServletRequest request, HttpServletResponse response) {
//
//		HttpStatus httpStatus = null;
//		LoginStatus status = new LoginStatus();

//		if (user == null){
//			status.setLoggedIn(false);
//			status.setUsername(null);
//			status.setErrorReason("User not found");
//			httpStatus = HttpStatus.UNAUTHORIZED;
//			HttpHeaders responseHeaders = makeCORS();
//			
//		    return new ResponseEntity<LoginStatus>(status,responseHeaders,httpStatus);
//		}
//	    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//	    
//	    token.setDetails(user);
//
//	    
//		
//	    try {
//	      Authentication auth = authenticationManager.authenticate(token);
//	      SecurityContextHolder.getContext().setAuthentication(auth);
//	      request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
//	      status.setLoggedIn(auth.isAuthenticated());
//	      status.setUsername(auth.getName());
//	      //rememberMeService.loginSuccess(request, response, auth);
//	      httpStatus = HttpStatus.OK;
//	    } catch (BadCredentialsException e) {
//	      status.setLoggedIn(false);
//		  status.setUsername(null);
//		  status.setErrorReason("Bad credentials");
//		  httpStatus = HttpStatus.UNAUTHORIZED;
//	    }catch (AuthenticationCredentialsNotFoundException e) {
//		  status.setLoggedIn(false);
//		  status.setUsername(user.getUsername());
//		  status.setErrorReason("User not found");
//		  httpStatus = HttpStatus.UNAUTHORIZED;
//		}
//	    //String cookieValue = "AuthToken=" + user.getUsername() + ":" + DigestUtils.sha1Hex(user.getPassword());
//	    HttpHeaders responseHeaders = makeCORS();
//	    
//	    //responseHeaders.set("AuthToken", cookieValue);
//	    return new ResponseEntity<LoginStatus>(status,responseHeaders,httpStatus);
//	  }
	  
	  @RequestMapping(method=RequestMethod.GET,value="/success",produces="application/json")
	  public ResponseEntity<LoginStatus> loginSuccess(Principal user, HttpServletRequest request, HttpServletResponse response) {
		  
	
		  HttpStatus httpStatus = HttpStatus.OK;
		  LoginStatus status = new LoginStatus();
		  status.setLoggedIn(true);
		  status.setErrorReason(null);
		  status.setUsername(user.getName());
		  HttpHeaders responseHeaders = makeCORS();
		  return new ResponseEntity<LoginStatus>(status,responseHeaders,httpStatus);
	  }
	  
	  @RequestMapping(method=RequestMethod.GET,value="/logoutsuccess",produces="application/json")
	  public @ResponseBody String logoutSuccess(HttpServletRequest request, HttpServletResponse response) {
		  
		
		  return "Sucessfull logout from service";
		  
	  }
	  
	  private static HttpHeaders makeCORS(){
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Access-Control-Allow-Origin", "*");
			responseHeaders.add("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
			responseHeaders.add("Access-Control-Allow-Headers", "Content-Type,AuthToken");
			responseHeaders.add("Access-Control-Max-Age", "86400");
			
			return responseHeaders;
		}
		
		@ExceptionHandler(value=Exception.class)
		public String handleException(){
				
			return "error";
		}
	  	
}
