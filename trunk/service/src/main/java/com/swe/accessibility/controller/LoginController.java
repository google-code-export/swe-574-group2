package com.swe.accessibility.controller;

import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.swe.accessibility.domain.LoginStatus;
import com.swe.accessibility.domain.User;
import com.swe.accessibility.domain.UserType;
import com.swe.accessibility.domain.proxy.SignupStatus;
import com.swe.accessibility.domain.proxy.UserObject;
import com.swe.accessibilty.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {

	
	@Autowired
	@Qualifier("authenticationManager")
	AuthenticationManager authenticationManager;
	
	@Autowired
	RememberMeServices rememberMeService;
	
	@Resource(name="userService")
	UserService userService;
	
	

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
	  
	  @RequestMapping(method=RequestMethod.GET,value="/logoutsuccess",produces="text/plain;charset=UTF-8")
	  public ResponseEntity<String>  logoutSuccess(HttpServletRequest request, HttpServletResponse response) {
		  
		  return new ResponseEntity<String>("Success",HttpStatus.OK);
		  
	  }
	  
	  @RequestMapping(method=RequestMethod.POST,value="/signup",headers={"Content-Type=application/json"})
	  public ResponseEntity<SignupStatus> signup(@RequestBody UserObject userObj, HttpServletResponse resp){
		  
		  HttpHeaders responseHeaders = makeCORS();
		  
		  
		  HttpStatus httpStatus = null;
		  SignupStatus status = new SignupStatus();
		  User user = new User();
		  
		  String username = userObj.getUsername();
		  String password = userObj.getPassword();
		  if (username == null || password == null){
			  status.setStatus("Failed");
			  status.setError("User name or password is null.");
			  httpStatus = HttpStatus.BAD_REQUEST;
		  }
		  else if (userService.getUserByName(username).getUsername() != null){
			  status.setStatus("Failed");
			  status.setError("User already exists");
			  httpStatus = HttpStatus.BAD_REQUEST;
		  }
		  else{
			  user.setUsername(username);
			  user.setPassword(DigestUtils.sha1Hex(password));
			  
			  UserType type = new UserType();
			  type.setId(2);
			  type.setTitle("user");
			  user.setUserType(type);
			  
			  userService.addUser(user);
			  
			  status.setStatus("Success");
			  httpStatus = HttpStatus.CREATED;
		  }
		  
		 
		  return new ResponseEntity<SignupStatus>(status,responseHeaders,httpStatus);
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
