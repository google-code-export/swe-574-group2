package com.swe.accessibility.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.swe.accessibility.domain.Comment;
import com.swe.accessibility.domain.proxy.CommentObject;
import com.swe.accessibility.domain.Entry;
import com.swe.accessibility.domain.InsertEntryResult;
import com.swe.accessibility.domain.User;
import com.swe.accessibilty.service.CommentService;
import com.swe.accessibilty.service.EntryService;
import com.swe.accessibilty.service.UserService;

@Controller
@RequestMapping("/comments")
public class CommentController {

	
	@Resource(name="commentService")
	private CommentService commentService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="entryService")
	private EntryService entryService;
	
	
	@RequestMapping(value="/add",method={RequestMethod.POST,RequestMethod.GET},headers={"Content-Type=application/json"})
	public ResponseEntity<InsertEntryResult> addComment(Principal principal,@RequestBody CommentObject request, HttpServletResponse response){
		
		InsertEntryResult entryResult = new InsertEntryResult();
		String text = request.getText();
		int id = request.getEntryId();
		
		String username = null;
		if (principal == null)
		
			username = "anonymousUser";
		else
			username = principal.getName();
		User currentUser = userService.getUserByName(username);
		Entry entry = entryService.getRawEntry(id);
		Comment comment = new Comment();
		comment.setText(text);
		comment.setUser(currentUser);
		comment.setEntry(entry);
		comment.setInsertdate(new Date(System.currentTimeMillis()));
		
		
		commentService.addComment(comment);
		entryResult.setResultStatus("success");
		
		HttpHeaders responseHeaders = makeCORS();
		ResponseEntity<InsertEntryResult> entity = new ResponseEntity<InsertEntryResult>(entryResult,responseHeaders,HttpStatus.OK );
		
		
		
		
		return entity;
		
	}
	
	private static HttpHeaders makeCORS(){
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Access-Control-Allow-Origin", "*");
		responseHeaders.add("Access-Control-Allow-Methods", "GET, OPTIONS, POST");
		responseHeaders.add("Access-Control-Allow-Headers", "Content-Type");
		responseHeaders.add("Access-Control-Max-Age", "86400");
		
		return responseHeaders;
	}
}
