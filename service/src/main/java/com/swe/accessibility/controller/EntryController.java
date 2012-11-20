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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.swe.accessibility.domain.Entry;
import com.swe.accessibility.domain.InsertEntryResult;
import com.swe.accessibility.domain.SubReason;
import com.swe.accessibility.domain.User;
import com.swe.accessibility.domain.proxy.EntryList;
import com.swe.accessibility.domain.proxy.ThumbsObject;
import com.swe.accessibility.domain.proxy.UpdateResult;
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
	
	
	
//	@RequestMapping(value = "/add", method = RequestMethod.POST, headers={"content-type=multipart/form-data","Access-Control-Allow-Origin:*"},produces={"application/json"})
//	public @ResponseBody InsertEntryResult  insertEntry(MultipartHttpServletRequest request,
//			HttpSession session, Principal principal) {
//		
//		InsertEntryResult entryResult = new InsertEntryResult();
//		int categoryId = Integer.parseInt(request.getParameter("category"));
//		DecimalFormat format = new DecimalFormat();
//		format.setParseBigDecimal(true);
//		BigDecimal coordX;
//		BigDecimal coordY;
//		
//		MultipartFile file = request.getFile("file");
//		String username = principal.getName();
//		try {
//			coordX = (BigDecimal) format.parse(request.getParameter("coordX"));
//			coordY = (BigDecimal) format.parse(request.getParameter("coordX"));
//			
//			User currentUser = userService.getUserByName(username);
//			String comment = request.getParameter("comment");
//			Entry entry = new Entry();
//			Set<SubReason> reasons = new  HashSet<SubReason>();
//			
//			SubReason reason = reasonService.getSubReason(categoryId);
//			
//			reasons.add(reason);
//			
//			entry.setComment(comment);
//			entry.setCoordX(coordX);
//			entry.setCoordY(coordY);
//			entry.setReasons(reasons);
//			entry.setImageMeta(file.getBytes());
//			entry.setUser(currentUser);
//			
//			
//			int entryId = entryService.addEntry(entry);
//			
//			entryResult.setEntryId(entryId);
//			entryResult.setResultId(1);
//			entryResult.setResultStatus("Success");
//			
//			
//		} catch (ParseException e) {
//			
//			entryResult.setResultStatus("Parse error for coordinate values");
//			entryResult.setResultId(2);
//			
//		} catch (IOException e) {
//			
//			entryResult.setResultStatus("Not a valid file");
//			entryResult.setResultId(3);
//		}
//		
//		
//		
//		
//		
//		
//		return entryResult;
//
//	}
	
	

	@RequestMapping(value = "/add", method = RequestMethod.POST, headers={"content-type=multipart/form-data"},produces={"application/json"})
	public  ResponseEntity<InsertEntryResult>  insertEntry(MultipartHttpServletRequest request,
			HttpSession session, Principal principal) {
		
		InsertEntryResult entryResult = new InsertEntryResult();
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
			Set<SubReason> reasons = new  HashSet<SubReason>();
			
			SubReason reason = reasonService.getSubReason(categoryId);
			
			reasons.add(reason);
			
			String uri = saveFile(request);
			entry.setComment(comment);
			entry.setCoordX(coordX);
			entry.setCoordY(coordY);
			entry.setReasons(reasons);
			entry.setImageMeta(uri);
			entry.setUser(currentUser);
			
			
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
		}
		
		
		HttpHeaders responseHeaders = makeCORS();
		ResponseEntity<InsertEntryResult> entity = new ResponseEntity<InsertEntryResult>(entryResult,responseHeaders,HttpStatus.OK );
		
		
		
		
		return entity;

	}
	
//	@RequestMapping(method={RequestMethod.GET},produces={"application/json"})
//	public  ResponseEntity<EntryList> getEntries(){
//		
//		
//		EntryList result = new EntryList();
//		
//		result.setData(entryService.loadEntries());
//		
//		
//		
//		HttpHeaders responseHeaders = makeCORS();
//		ResponseEntity<EntryList> entity = new ResponseEntity<EntryList>(result,responseHeaders,HttpStatus.OK );
//		return entity;
//	}
	
	private String saveFile(MultipartHttpServletRequest request) throws IOException {
		
		MultipartFile file = request.getFile("file");
		
		InputStream in = new ByteArrayInputStream(file.getBytes());
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
		String format = null;
		 while (readers.hasNext()) {
		        ImageReader read = readers.next();
		        format = read.getFormatName();
		        System.out.println("format name = " + format);
		         
		 }
		BufferedImage image = ImageIO.read(iis);
		
		int scaledWidth = image.getHeight() / 4;
		int scaledHeight = image.getWidth() / 4;
		
		BufferedImage resizedImage = createResizedCopy(image, scaledWidth, scaledHeight, true);
		String fileURL = UUID.randomUUID().toString() + "." + format;
		
		File output = new File(uploadRepo + File.separator + fileURL);
		FileOutputStream os = new FileOutputStream(output);
		ImageIO.write(resizedImage, format, os);
		return fileURL;
	}

	@RequestMapping(method={RequestMethod.GET},produces={"application/json"})
	public  ResponseEntity<EntryList> getEntry(@RequestParam(required=false) String x, @RequestParam(required=false) String y,@RequestParam(required=false) String categoryId,@RequestParam(required=false) String typeId){
		
		
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
			else
				result = getEntries();
		}catch(ParseException e){
			
		}
		
		
		
		ResponseEntity<EntryList> entity = new ResponseEntity<EntryList>(result,responseHeaders,HttpStatus.OK );
		return entity;
	}
	
	@RequestMapping(value="/thumbs",method={RequestMethod.POST,RequestMethod.OPTIONS},produces="application/json",headers={"Content-Type=application/json"})
	
	public ResponseEntity<UpdateResult> updateVoteCount(@RequestBody ThumbsObject thumbs){
		
		UpdateResult result = new UpdateResult();
		HttpHeaders responseHeaders = makeCORS();
		int id = thumbs.getEntryId();
		boolean up = thumbs.isUp();
		try{
			entryService.updateEntryVote(id,up);
			result.setResultId(0);
			result.setResultStatus("SUCCESS");
		}catch(Exception e){
			result.setResultId(1);
			result.setResultStatus("FAILURE");
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

	private  EntryList getEntryByCategory(@RequestParam String categoryId) throws ParseException{
		
		int id;
		
		EntryList result = new EntryList();
		id = Integer.parseInt(categoryId);
		result.setData(entryService.getEntriesByCategory(categoryId));
		
	
		return result;
	}
	
	private EntryList getEntries(){
		
		EntryList result = new EntryList();
		result.setData(entryService.loadEntries());
		
		return result;
	}

//	@RequestMapping(value="/get",method={RequestMethod.GET},produces={"application/json"},headers={"Access-Control-Allow-Origin:*"})
//	public @ResponseBody EntryList getEntry(@RequestParam String x, @RequestParam String y){
//		
//		BigDecimal coordX;
//		BigDecimal coordY;
//		DecimalFormat format = new DecimalFormat();
//		format.setParseBigDecimal(true);
//		EntryList result = new EntryList();
//		try{
//			coordX = (BigDecimal) format.parse(x);
//			coordY = (BigDecimal) format.parse(y);
//			result.setData(entryService.getEntry(coordX, coordY));
//		}catch(ParseException e){
//			
//		}
//		
//		return result;
//	}
	
	
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
	
}
