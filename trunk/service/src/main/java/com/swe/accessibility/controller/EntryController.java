package com.swe.accessibility.controller;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.util.JSONPObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.swe.accessibility.domain.Entry;
import com.swe.accessibility.domain.EntryReason;
import com.swe.accessibility.domain.InsertEntryResult;
import com.swe.accessibility.domain.SubReason;
import com.swe.accessibility.domain.User;
import com.swe.accessibility.domain.proxy.Config;
import com.swe.accessibility.domain.proxy.EntryList;
import com.swe.accessibility.domain.proxy.EntryProxy;
import com.swe.accessibility.domain.proxy.ThumbsObject;
import com.swe.accessibility.domain.proxy.UpdateResult;
import com.swe.accessibilty.service.CommentService;
import com.swe.accessibilty.service.EntryService;
import com.swe.accessibilty.service.ReasonService;
import com.swe.accessibilty.service.UserService;

@Controller
@RequestMapping("/entries")
public class EntryController {

	//Assuming it is a linux/unix file system
	private static String uploadRepo = System.getProperty("user.home") + File.separator + "uploads";
	
	@Resource(name = "entryService")
	private EntryService entryService;
	
	@Resource(name = "reasonService")
	private ReasonService reasonService;

	@Resource(name = "userService")
	private UserService userService;
	
	
	@Resource(name="commentService")
	private CommentService commentService;
	
	@Autowired
	private Config config;
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, headers={"content-type=multipart/form-data"},produces={"application/json"})
	public  ResponseEntity<InsertEntryResult>  insertEntry(MultipartHttpServletRequest request,
			HttpSession session, Principal principal) {
		
		InsertEntryResult entryResult = new InsertEntryResult();
		int categoryId = Integer.parseInt(request.getParameter("category"));
		DecimalFormat format = new DecimalFormat();
		format.setParseBigDecimal(true);
		BigDecimal coordX;
		BigDecimal coordY;
		String value = null;
		
		String username = null;
		if (principal == null)
		
			username = "anonymousUser";
		else
			username = principal.getName();
		try {
			coordX = (BigDecimal) format.parse(request.getParameter("coordX"));
			coordY = (BigDecimal) format.parse(request.getParameter("coordY"));
			value = request.getParameter("value");
			User currentUser = userService.getUserByName(username);
		
			String comment = request.getParameter("comment");
			Entry entry = new Entry();
			
		
			
			SubReason reason = reasonService.getSubReason(categoryId);
			int priority = reason.getPriority();
			String extra = reason.getExtra();
			JSONObject obj = null;
				
			
			if (value != null && extra != null){
				obj = new JSONObject(extra);
				obj.put("value", value);
				//Priority setting according to boundary
				
				if (Integer.parseInt(value) > Integer.parseInt(obj.getString("boundary")) && priority < 3){
					priority++;
				}
			}
			
			
			
			
			String uri = saveFile(request);
			entry.setComment(comment);
			entry.setCoordX(coordX);
			entry.setCoordY(coordY);
			entry.setImageMeta(uri);
			entry.setUser(currentUser);
			entry.setPriority(priority);
			
			EntryReason entryReason = new EntryReason();
			entryReason.setReason(reason);
			entryReason.setEntry(entry);
			if (obj != null)
				entryReason.setExtra(obj.toString());
			
			entry.getEntryReasons().add(entryReason);
			
			int entryId = entryService.addEntry(entry);
			
			entryResult.setEntryId(entryId);
			entryResult.setResultId(1);
			entryResult.setResultStatus("Success");
			
			
		} catch (ParseException e) {
			
			entryResult.setResultStatus("Parse error for coordinate values");
			entryResult.setResultId(2);
			
		} catch (IOException e) {
			e.printStackTrace();
			entryResult.setResultStatus("Error saving file");
			entryResult.setResultId(3);
		} catch (JSONException e) {
			entryResult.setResultStatus("Error adding entry");
			entryResult.setResultId(4);
		}
		
		HttpHeaders responseHeaders = makeCORS();
		ResponseEntity<InsertEntryResult> entity = new ResponseEntity<InsertEntryResult>(entryResult,responseHeaders,HttpStatus.OK );
		
		
		
		
		return entity;

	}
	
	
	@RequestMapping(value = "/add/web", method = RequestMethod.POST, headers={"content-type=multipart/form-data"},produces={"application/json"})
	public  void  insertEntryWeb(MultipartHttpServletRequest request, HttpServletResponse response,
			HttpSession session, Principal principal) {
		
		String page = null;
		
		int categoryId = Integer.parseInt(request.getParameter("category"));
		DecimalFormat format = new DecimalFormat();
		format.setParseBigDecimal(true);
		BigDecimal coordX;
		BigDecimal coordY;
		
		
		String username = null;
		if (principal == null)
		
			username = "anonymousUser";
		else
			username = principal.getName();
		try {
			coordX = (BigDecimal) format.parse(request.getParameter("coordX"));
			coordY = (BigDecimal) format.parse(request.getParameter("coordY"));
			
			User currentUser = userService.getUserByName(username);
			String comment = request.getParameter("comment");
			Entry entry = new Entry();
			
			
			SubReason reason = reasonService.getSubReason(categoryId);
		
		
			
			String uri = saveFile(request);
			entry.setComment(comment);
			entry.setCoordX(coordX);
			entry.setCoordY(coordY);
			entry.setImageMeta(uri);
			entry.setUser(currentUser);
			
			
			EntryReason entryReason = new EntryReason();
			entryReason.setReason(reason);
			entryReason.setEntry(entry);
			
			entry.getEntryReasons().add(entryReason);
			
			entryService.addEntry(entry);
			
			page = "success";
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			page = "error";
			
		} 
		
		String redirect = request.getContextPath() + "/" + page + ".html";
		try {
			response.sendRedirect(redirect);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}
	

	
	private String saveFile(MultipartHttpServletRequest request) throws IOException {
		
		MultipartFile file = request.getFile("file");
		
		InputStream in = new ByteArrayInputStream(file.getBytes());
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
		String format = null;
		 while (readers.hasNext()) {
		        ImageReader read = readers.next();
		        format = read.getFormatName();
		        
		         
		 }
		BufferedImage image = ImageIO.read(iis);
		
		int scaledWidth = image.getHeight() / 2;
		int scaledHeight = image.getWidth() / 2;
		
		BufferedImage resizedImage = createResizedCopy(image, scaledWidth, scaledHeight, true);
		String fileURL = UUID.randomUUID().toString() + "." + format;
		
		File output = new File(uploadRepo + File.separator + fileURL);
		FileOutputStream os = new FileOutputStream(output);
		ImageIO.write(resizedImage, format, os);
		return fileURL;
	}

	@RequestMapping(method={RequestMethod.GET},produces={"application/json"})
	public  ResponseEntity<EntryList> getEntries(@RequestParam(required=false) String x, @RequestParam(required=false) String y,@RequestParam(required=false) String categoryId,@RequestParam(required=false) String typeId,@RequestParam(required=false) String priority){
		
		
		HttpHeaders responseHeaders = makeCORS();
		DecimalFormat format = new DecimalFormat();
		format.setParseBigDecimal(true);
		EntryList result = null;
		
		
		try{
			
			if (x != null && y == null)
				result = getByX(x,format);

			else if (x == null && y != null)
				result = getByY(y,format);
			
			else if (x != null && y != null)
				result = getByXY(x,y,format);
			else if (categoryId != null)
				result = getEntryByCategory(categoryId);
			else if(typeId != null)
				result = getByType(typeId);
			else if (priority != null)
				result = getByPriority(priority);
			else
				result = getEntries();
		}catch(ParseException e){
			
		}
		
		
		
		ResponseEntity<EntryList> entity = new ResponseEntity<EntryList>(result,responseHeaders,HttpStatus.OK );
		return entity;
	}
	
	


	@RequestMapping(value="/{id}",method={RequestMethod.GET},produces={"application/json"})
	public  ResponseEntity<EntryList> getEntry(@PathVariable int id){
		
		
		HttpHeaders responseHeaders = makeCORS();
		
		EntryList result = new EntryList();
		EntryProxy obj = entryService.getEntryById(id);
		obj.setComments(commentService.listComment(id));
		List<EntryProxy> data = new ArrayList<EntryProxy>();
		data.add(obj);
		
		result.setData(data);
		commentService.listComment(id);
		ResponseEntity<EntryList> entity = new ResponseEntity<EntryList>(result,responseHeaders,HttpStatus.OK );
		return entity;
	}
	
	@RequestMapping(value="/thumbs",method={RequestMethod.POST,RequestMethod.OPTIONS},produces="application/json",headers={"Content-Type=application/json"})
	
	public ResponseEntity<UpdateResult> updateVoteCount(Principal principal,@RequestBody ThumbsObject thumbs){
		
		UpdateResult result = new UpdateResult();
		HttpHeaders responseHeaders = makeCORS();
		User user = userService.getUserByName(principal.getName());
		int id = thumbs.getEntryId();
		
		Entry entry = entryService.getRawEntry(id);
		boolean up = thumbs.isUp();
		
		if(entryService.checkForVote(entry, user)){
			result.setResultId(2);
			result.setResultStatus("FAILURE");
		}
		else{
			try{
				entryService.updateEntryVote(entry,up,user);
				
				result.setResultId(0);
				result.setResultStatus("SUCCESS");
			}catch(Exception e){
				result.setResultId(1);
				result.setResultStatus("FAILURE");
			}
		}
		
		
		
		return new ResponseEntity<UpdateResult>(result,responseHeaders,HttpStatus.OK );
		
	}
	
	
	private EntryList getByType(String typeId) {
		
		EntryList result = new EntryList();
		result.setData(entryService.getEntriesByType(typeId));
		
		return result;
	}

	private EntryList getByXY(String x, String y, DecimalFormat format) throws ParseException {
		
		BigDecimal coordX = (BigDecimal) format.parse(x);
		BigDecimal coordY = (BigDecimal) format.parse(y);
		EntryList result = new EntryList();
		result.setData(entryService.loadEntries(coordX, coordY));
		
		return result;
	}

	private EntryList getByY(String y, DecimalFormat format) throws ParseException {
		
		BigDecimal coordY = (BigDecimal) format.parse(y);
		EntryList result = new EntryList();
		result.setData(entryService.loadEntries(null, coordY));
		
		return result;
	}
	
	

	private EntryList getByX(String x, DecimalFormat format) throws ParseException {
		
		BigDecimal coordX = (BigDecimal) format.parse(x);
		EntryList result = new EntryList();
		result.setData(entryService.loadEntries(coordX, null));
		
		return result;
	}
	
	private EntryList getByPriority(String priority) {
		
		EntryList result = new EntryList();
		result.setData(entryService.loadEntries(priority));
		return result;
	}

	private  EntryList getEntryByCategory(String categoryId) throws ParseException{
		
		EntryList result = new EntryList();
		result.setData(entryService.getEntriesByCategory(categoryId));
		
	
		return result;
	}
	
	private EntryList getEntries(){
		
		EntryList result = new EntryList();
		result.setData(entryService.loadEntries());
		
		return result;
	}


	
	private static HttpHeaders makeCORS(){
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Access-Control-Allow-Origin", "*");
		responseHeaders.add("Access-Control-Allow-Methods", "GET, OPTIONS, POST");
		responseHeaders.add("Access-Control-Allow-Headers", "Content-Type");
		responseHeaders.add("Access-Control-Max-Age", "86400");
		
		return responseHeaders;
	}
	
	private static BufferedImage createResizedCopy(Image originalImage, 
    		int scaledWidth, int scaledHeight, 
    		boolean preserveAlpha)
    {
    	
    	int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
    	Graphics2D g = scaledBI.createGraphics();
    	if (preserveAlpha) {
    		g.setComposite(AlphaComposite.Src);
    	}
    	g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null); 
    	g.dispose();
    	return scaledBI;
    }
	
	@ExceptionHandler(value=Exception.class)
	public String handleException(Exception e){
		
		e.printStackTrace();
		return "error";
	}
	
}
