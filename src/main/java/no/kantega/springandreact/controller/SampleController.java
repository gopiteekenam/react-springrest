package no.kantega.springandreact.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import no.kantega.springandreact.model.STG_CCAR_STATISTICS;
import no.kantega.springandreact.model.UPLOADHISTORY;
import no.kantega.springandreact.model.UPLOADPROFILE;
import no.kantega.springandreact.exception.FileNotSupportedException;
import no.kantega.springandreact.repository.CustomerRepository;
import no.kantega.springandreact.repository.ProfileHistoryRepository;
import no.kantega.springandreact.repository.UploadProfileRepository;
import no.kantega.springandreact.utils.Util;
import sun.swing.UIClientPropertyKey;

@Controller
public class SampleController {

	@Autowired
	DataSource dataSource;

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	UploadProfileRepository profileRepository;
	
	@Autowired
	ProfileHistoryRepository profileHistoryRepository;

	final static org.slf4j.Logger log = LoggerFactory.getLogger(SampleController.class);

	private ResponseEntity<String> responseEntity;
	
	@RequestMapping(value="/uploadccarstat2000", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public  ResponseEntity<String> uploadfile(@RequestParam("file") MultipartFile upgradeFile,@RequestParam("notes") String notes,@RequestParam("comments") String comments,@RequestParam("feedname") String feedname) throws Exception {
		
		String resp = null;
		String originalFileName = upgradeFile.getOriginalFilename();
		int columncount=0;
		String columnname=null;
		log.info("File name " + originalFileName);
		/*if(!originalFileName.contains(feedname))
		{
			resp = "File name is incorrect, please update right file";
			return new ResponseEntity<String>(resp, HttpStatus.BAD_REQUEST); 
		}*/
		
		String extension = FilenameUtils.getExtension(upgradeFile.getOriginalFilename());
		log.info("File extension " + extension);
		
		
		log.info("File type " + upgradeFile.getName());
		log.info("Obtained a upload request for the id {} " + upgradeFile.getContentType());
		log.info("Obtained a upload request for the id {} " + upgradeFile.getSize());
		log.info("Obtained a upload request for the id {} " + upgradeFile.getInputStream());
		log.info("Obtained a upload request for the id {} " + upgradeFile.getBytes());

		log.info("notes is:"+notes);
		log.info("comments:"+comments);
		ArrayList<STG_CCAR_STATISTICS> data = new ArrayList<STG_CCAR_STATISTICS>();
		if(!upgradeFile.isEmpty()){
			HashMap<Integer,String> map= customerRepository.getNumberOfColumnsFromDatabaseTable();
			for(Integer count:map.keySet()) {
				log.info("controller number of columns from database is..."+count);
				columncount = count;
				log.info("controller number of columns name is ..."+map.get(count));
				columnname = map.get(count);
			}
			
			
		try {
		if(extension.equals("csv"))
		{
			
			data = Util.readCSV(upgradeFile.getInputStream(),columncount,columnname);
			log.info("data after reading csv is " + data);
			deleteFromRefDatabase();
			addtoDatabase(data);
			addFileToDatabase(upgradeFile.getBytes(),notes,comments,originalFileName,feedname);
			log.info("After adding csv data to database " + data);
			resp="csv file is uploaded successfully";
			return new ResponseEntity<String>(resp, HttpStatus.ACCEPTED);
			 
		}
		else 
		if(extension.equals("xls")||extension.equals("xlsx")){
			data = Util.read(upgradeFile.getInputStream());
			log.info("data after reading xls is " + data);
			deleteFromRefDatabase();
			addtoDatabase(data);
			addFileToDatabase(upgradeFile.getBytes(),notes,comments,originalFileName,feedname);
			log.info("After adding excel data to database " + data);
		    resp = "Excel file is uploaded successfully";
			return new ResponseEntity<String>(resp, HttpStatus.ACCEPTED);
//			return "xls file uploaded successfully";
			
		}
		else {
			log.info("not a valid format");
			throw new RuntimeException("not a valid format");
		}
	
		}catch (Exception e) {
			
			if(e instanceof ArrayIndexOutOfBoundsException) {
				log.info("inside arrayindexoutofboundsexception:::"+e.getMessage());
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
			if(e instanceof RuntimeException) {
				log.info("inside runtime exception :::"+e.getMessage());
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		    resp=e.getMessage();
			 return new ResponseEntity<String>(resp, HttpStatus.BAD_REQUEST);
			}
		}		else {
			
		    resp = "You failed to upload " + upgradeFile.getOriginalFilename() + " because the file was empty.";
			return new ResponseEntity<String>(resp, HttpStatus.NO_CONTENT);
        }
	}
	
	@RequestMapping(value="/uploadccmiscirating", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public  ResponseEntity<String> uploadccmiscirating(@RequestParam("file") MultipartFile upgradeFile,@RequestParam("notes") String notes,@RequestParam("comments") String comments,@RequestParam("feedname") String feedname) throws Exception {
		String columnname = null;
		int columncount = 0;
		log.info("SampleController...inside uploadccmiscirating");
		log.info("feed name in samplecontroller is..."+feedname);
		String originalFileName = upgradeFile.getOriginalFilename();
		log.info("File name " + originalFileName);
		
		String extension = FilenameUtils.getExtension(upgradeFile.getOriginalFilename());
		log.info("File extension " + extension);
		
		
		log.info("File type " + upgradeFile.getName());
		log.info("Obtained a upload request for the id {} " + upgradeFile.getContentType());
		log.info("Obtained a upload request for the id {} " + upgradeFile.getSize());
		log.info("Obtained a upload request for the id {} " + upgradeFile.getInputStream());
		log.info("Obtained a upload request for the id {} " + upgradeFile.getBytes());

		log.info("notes is:"+notes);
		log.info("comments:"+comments);
		ArrayList<STG_CCAR_STATISTICS> data = new ArrayList<STG_CCAR_STATISTICS>();
		if(!upgradeFile.isEmpty()){
			HashMap<Integer,String> map= customerRepository.getNumberOfColumnsFromDatabaseTable();
			for(Integer count:map.keySet()) {
				log.info("controller number of columns from database is..."+count);
				columncount = count;
				log.info("controller number of columns name is ..."+map.get(count));
				columnname = map.get(count);
			}
		try {
		if(extension.equals("csv"))
		{
			data = Util.readCSV(upgradeFile.getInputStream(), columncount,columnname);
			log.info("data after reading csv is " + data);
			deleteFromRefDatabase();
			addtoDatabase(data);
			addFileToDatabase(upgradeFile.getBytes(),notes,comments,originalFileName,feedname);
			log.info("After adding csv data to database " + data);
			responseEntity = new ResponseEntity<String>("file upload has been accepted for CSV.", HttpStatus.ACCEPTED);
			log.info("response entity in csv is..."+responseEntity.getBody());
			return responseEntity;
			 
		}
		else 
		if(extension.equals("xls")||extension.equals("xlsx")){
			data = Util.read(upgradeFile.getInputStream());
			log.info("data after reading xls is " + data);
			deleteFromRefDatabase();
			addtoDatabase(data);
			addFileToDatabase(upgradeFile.getBytes(),notes,comments,originalFileName,feedname);
			log.info("After adding excel data to database " + data);
			ResponseEntity<String> responseEntity = new ResponseEntity<String>("file upload has been accepted for Excel.", HttpStatus.ACCEPTED);
			log.info("response entity is..."+responseEntity.getBody());
			return responseEntity;
//			return "xls file uploaded successfully";
			
		}
		else {
			log.info("not a valid format");
			throw new RuntimeException("not a valid format");
		}
	
		}catch (Exception e) {
			ResponseEntity<String> responseEntity = null;
			log.info("inside exception ..."+e.getMessage());
            return responseEntity;
			
//			return " error in file upload";
        }
		}
		else {
//			return ResponseWrapper.buildResponse(RTCodeEnum.C_OK, "add report failed");
//			return "file format is not valid";
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("You failed to upload " + upgradeFile.getOriginalFilename() + " because the file was empty.");
        }
		
		
		
		
	}



	private void deleteFromRefDatabase() {
		log.info("inside delete from ref database");
		customerRepository.delete();
	}

	private void addFileToDatabase(byte[] bs, String notes, String comments, String originalFileName, String feedname) throws IOException, SerialException, SQLException {
		int profileid = profileRepository.getProfileIdByFeedname(feedname);
		log.info("inside add file to database");
		UPLOADHISTORY history = new UPLOADHISTORY();
		log.info("file array is :"+bs);
		history.setDownloadFile(bs);
		log.info("original file name  :"+originalFileName);
		history.setFilename(originalFileName);
		log.info("notes is  :"+notes);
		history.setNotes(notes);
		history.setUploadprofileId(profileid);
		history.setCreatedBy(System.getProperty("user.name"));
		java.sql.Timestamp date = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		history.setCreatedDate(date);
		history.setUpdatedBy(System.getProperty("user.name"));
		history.setUpdatedDate(date);
		profileHistoryRepository.insert(history);
		
		/*UPLOADPROFILE profile = new UPLOADPROFILE();
		profile.setTemplatedownload(bs);
		profileRepository.insert(profile);*/
		
		
		
	}

	public int addtoDatabase(ArrayList<STG_CCAR_STATISTICS> data) {
		System.out.println("inside addToDatabase"+data.size());
		int profileId = 0;	
		for (int i =0;i<data.size();i++)
		{
			profileId = customerRepository.insert(data.get(i));
			
		}
		return profileId;
	}
	
	
	@RequestMapping(value = "/profiles", method = RequestMethod.GET)
	@ResponseBody
	public Collection<UPLOADPROFILE> getProfiles(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		System.out.println("inside getProfileHistory");
		List<UPLOADPROFILE> lstUploadProfile = profileRepository.findAll();
		return lstUploadProfile;
		
	}

	@RequestMapping(value = "/profileHistory", method = RequestMethod.GET)
	@ResponseBody
	public Collection<UPLOADHISTORY> getProfileHistory(@RequestParam("feedname") String feedname, HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		System.out.println("inside getProfileHistory feed name:::"+feedname);
		List<UPLOADHISTORY> lstUploadHistory = profileHistoryRepository.findAllByFeeName("CCAR_STAT_2000");
		return lstUploadHistory;
		
	}
	
	@RequestMapping(value = "/downloadTemplate", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String downloadProfile(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ClassNotFoundException {
		System.out.println("inside download template");
		ByteArrayOutputStream data= null;
		try {
		data = (ByteArrayOutputStream)profileRepository.readDataFromBlob("CCAR_STAT_2000");
       	System.out.println("inside download csv,,,data..."+data);
     // serialize PDF to Base64
//       	byte[] encodedBytes = java.util.Base64.getEncoder().encode(data.toByteArray());
       	response.reset();
       	response.addHeader("Pragma", "public");
       	response.addHeader("Cache-Control", "max-age=0");
       	response.setHeader("Content-disposition", "attachment;filename=" + "download.xls");
       	response.setContentType("application/xls");

     // avoid "byte shaving" by specifying precise length of transferred data
       	response.setContentLength(data.toByteArray().length);
       	
     // send to output stream
       	ServletOutputStream servletOutputStream = response.getOutputStream();

       	servletOutputStream.write(data.toByteArray());
       	servletOutputStream.flush();
       	servletOutputStream.close();
   } catch (Exception e) {
		// TODO Auto-generated catch block
		log.info("error message in csv is:" + e.getMessage());
	}
	return response.getOutputStream().toString();
}
		
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadcsv", method = RequestMethod.GET)
	public String downloadcsvfromblob(@RequestParam("feedname") String feedname,HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		System.out.println("inside download csv feedname:::"+feedname);
		ByteArrayOutputStream data= null;
		try {
			if(StringUtils.isEmpty(feedname)) {
				feedname="CCMIS_CI_RATING";
			}
	        data = (ByteArrayOutputStream)profileRepository.readDataFromBlob(feedname);
	       	System.out.println("inside download csv,,,data..."+data);
	     // serialize PDF to Base64
//	       	byte[] encodedBytes = java.util.Base64.getEncoder().encode(data.toByteArray());
	       	response.reset();
	       	response.addHeader("Pragma", "public");
	       	response.addHeader("Cache-Control", "max-age=0");
	       	response.setHeader("Content-disposition", "attachment;filename=" + "download.csv");
	       	response.setContentType("application/csv");

	     // avoid "byte shaving" by specifying precise length of transferred data
	       	response.setContentLength(data.toByteArray().length);
	       	
	     // send to output stream
	       	ServletOutputStream servletOutputStream = response.getOutputStream();

	       	servletOutputStream.write(data.toByteArray());
	       	servletOutputStream.flush();
	       	servletOutputStream.close();
	   } catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("error message in csv is:" + e.getMessage());
		}
		return data.toString();
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/historycsv", method = RequestMethod.GET)
	public String downloadhistorycsvfromblob(@RequestParam("filename") String filename, HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		System.out.println("inside download csv for history filename:::" + filename);
		ByteArrayOutputStream data = null;
		try {
			if (StringUtils.isEmpty(filename)) {
				filename = "myfile873.csv";
			}
			data = (ByteArrayOutputStream) profileHistoryRepository.readDataFromBlob(filename);
			System.out.println("inside download csv for history is ..." + data);
			response.reset();
	       	response.addHeader("Pragma", "public");
	       	response.addHeader("Cache-Control", "max-age=0");
	       	response.setHeader("Content-disposition", "attachment;filename=" + "download.csv");
	       	response.setContentType("application/csv");

	     // avoid "byte shaving" by specifying precise length of transferred data
	       	response.setContentLength(data.toByteArray().length);
	       	
	     // send to output stream
	       	ServletOutputStream servletOutputStream = response.getOutputStream();

	       	servletOutputStream.write(data.toByteArray());
	       	servletOutputStream.flush();
	       	servletOutputStream.close();
	   } catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("error message in csv is:" + e.getMessage());
		}
		return data.toString();
		
	}

	/**
     * Error case, returns ErrorResponse which Spring automatically converts to JSON (using Jackson)
     * Content type will be application/json
     */
    @ExceptionHandler(FileNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(FileNotSupportedException e) {
        return new ErrorResponse(e.getMessage()); // use message from the original exception
    }
    
    /**
     * Defines the JSON output format of error responses
     */
    private static class ErrorResponse {
        public ErrorResponse(String message) {
        }
    }
}