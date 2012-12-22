package com.swe.accessibility.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.swe.accessibility.domain.Priority;
import com.swe.accessibility.domain.ReasonList;
import com.swe.accessibility.domain.proxy.PriorityList;
import com.swe.accessibility.domain.proxy.SubReasonList;
import com.swe.accessibility.domain.proxy.TypeList;
import com.swe.accessibilty.service.ReasonService;
import com.swe.accessibilty.service.TypeService;

/**
 * REST provider
 * consist of POST and GET methods
 * @author gucluakkaya
 *
 */
@Controller
@RequestMapping("/")
public class MainController {
	
	protected static Logger logger = Logger.getLogger(MainController.class);
	
	@Resource(name="reasonService")
	private ReasonService reasonService;
	
	@Resource(name="typeService")
	private TypeService typeService;
	
	
//	@RequestMapping(value="/categories",
//			method = RequestMethod.GET,produces={"application/json"})
//	public @ResponseBody ReasonList getMainCategories(){
//		
//		logger.info("In the method");
//		ReasonList result = new ReasonList();
//		result.setData(reasonService.getReasons());
//		
//		
//		return result;
//	}
	
	@RequestMapping(value="/categories",
			method = RequestMethod.GET,produces={"application/json"})
	public ResponseEntity<ReasonList> getMainCategories(){
		
		logger.info("In the method");
		
		
		ReasonList result = new ReasonList();
		result.setData(reasonService.getReasons());
		HttpHeaders responseHeaders = makeCORS();
		ResponseEntity<ReasonList> entity = new ResponseEntity<ReasonList>(result,responseHeaders,HttpStatus.OK );
		
		return entity;
	}
	

	@RequestMapping(value="/categories/{id}",
			method = RequestMethod.GET,produces={"application/json"})
	public  ResponseEntity<SubReasonList> getSubCategories(@PathVariable int id){
		
		
		
		SubReasonList result = new SubReasonList();
		result.setData(reasonService.getSubReasons(id));
		HttpHeaders responseHeaders = makeCORS();
		ResponseEntity<SubReasonList> entity = new ResponseEntity<SubReasonList>(result,responseHeaders,HttpStatus.OK );
		
		return entity;
		
		
	}
	
	@RequestMapping(value="/types", method=RequestMethod.GET, produces={"application/json"})
	public ResponseEntity<TypeList> getTypes(){
		
		TypeList result = new TypeList();
		result.setData(typeService.getAll());
		HttpHeaders responseHeaders = makeCORS();
		ResponseEntity<TypeList> entity = new ResponseEntity<TypeList>(result,responseHeaders,HttpStatus.OK );
		
		return entity;
		
	}
	
	
	@RequestMapping(value="/priorities", method=RequestMethod.GET,produces={"application/json"})
	public ResponseEntity<PriorityList> getPriorities(){
		
		PriorityList result = new PriorityList();
		List<String> priorities = new ArrayList<String>();
		priorities.add(Priority.LOW.getLabel());
		priorities.add(Priority.CRITICAL.getLabel());
		
		result.setData(priorities);
		
		return new ResponseEntity<PriorityList>(result,HttpStatus.OK);
		
		
	}
	
	private static HttpHeaders makeCORS(){
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Access-Control-Allow-Origin", "*");
//		responseHeaders.add("Access-Control-Allow-Methods", "GET, OPTIONS, POST");
//		responseHeaders.add("Access-Control-Allow-Headers", "Content-Type");
//		responseHeaders.add("Access-Control-Max-Age", "86400");
		
		return responseHeaders;
	}
	

//	@RequestMapping(value="/categories/{id}",
//			method = RequestMethod.GET,produces={"application/json"})
//	public @ResponseBody SubReasonList getSubCategories(@PathVariable int id){
//		
//		
//		SubReasonList result = new SubReasonList();
//		result.setData(reasonService.getSubReasons(id));
//		
//		
//		return result;
//	}
//	
	@ExceptionHandler(value=Exception.class)
	public String handleException(){
			
		return "error";
	}
}
