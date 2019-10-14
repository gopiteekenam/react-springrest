package no.kantega.springandreact.repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import no.kantega.springandreact.model.UPLOADHISTORY;

@Repository
public class ProfileHistoryRepository {

	final static org.slf4j.Logger log = LoggerFactory.getLogger(ProfileHistoryRepository.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public UPLOADHISTORY findById(String id) {
		return (UPLOADHISTORY) jdbcTemplate.queryForObject("select * from REF_CCAR_STAT_2000_2014 where PROD_ID=?", new Object[] { id },
				new BeanPropertyRowMapper<UPLOADHISTORY>(UPLOADHISTORY.class));
	}
	
	class StatisticsRowMapper implements RowMapper<UPLOADHISTORY> {


		@Override
		public UPLOADHISTORY mapRow(ResultSet rs, int rowNum) throws SQLException {
			UPLOADHISTORY uploadhistory = new UPLOADHISTORY();
			uploadhistory.setCreatedBy(System.getProperty("user.name"));
			java.sql.Timestamp date = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
			uploadhistory.setCreatedDate(date);
			uploadhistory.setDownloadFile(rs.getBytes("DOWNLOAD_FILE"));
			uploadhistory.setFilename(rs.getString("FILE_NAME"));
			uploadhistory.setNotes(rs.getString("NOTES"));
			uploadhistory.setStatus(rs.getString("STATUS"));
			uploadhistory.setUpdatedBy(System.getProperty("user.name"));
			uploadhistory.setUpdatedDate(date);
			uploadhistory.setUploadprofileId(rs.getInt("UI_UPLOAD_PROFILE_ID"));
			uploadhistory.setUploadVersion(rs.getString("UI_UPLOAD_VERSION"));
			return uploadhistory;
		}	

	}

	public List<UPLOADHISTORY> findAll() {
		return jdbcTemplate.query("select * from UI_UPLOAD_HISTORY", new StatisticsRowMapper());
	}
	

	public void insert(UPLOADHISTORY data) throws IOException {
		log.info("inside insert method of ProfileHistoryRepository"+data.getFilename());
	    String version = jdbcTemplate.queryForObject("select max(ROWNUM)+1 from UI_UPLOAD_HISTORY where UI_UPLOAD_PROFILE_ID="+data.getUploadprofileId(), String.class);
	    log.info("version in CustomRepository"+version);
	    String sql = "insert into UI_UPLOAD_HISTORY (NOTES,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,UI_UPLOAD_PROFILE_ID,UI_UPLOAD_VERSION,DOWNLOAD_FILE,STATUS,FILE_NAME) values(?,?,?,?,?,?,?,?,?,?)";
	    LobHandler lobHandler = new DefaultLobHandler();
	    
	    jdbcTemplate.execute(sql, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
			
	    	@Override
	          protected void setValues(PreparedStatement ps, LobCreator lobCreator)
	                  throws SQLException, DataAccessException {

	              ps.setString(1, data.getNotes());
	              ps.setString(2, data.getCreatedBy());
	              ps.setTimestamp(3, data.getCreatedDate());
	              ps.setString(4, data.getUpdatedBy());
	              ps.setTimestamp(5, data.getUpdatedDate());
	              ps.setInt(6, data.getUploadprofileId());
	              if(version == null) {
	            	  ps.setString(7, "1");  
	              }
	              else {
	              ps.setString(7, version);
	              }
	              ps.setString(9, data.getStatus());
	              ps.setString(10, data.getFilename());
	              
	              ByteArrayInputStream inputStream = new ByteArrayInputStream(data.getDownloadFile());
	              lobCreator.setBlobAsBinaryStream(ps, 8, inputStream, data.getDownloadFile().length);
	          }
	      });
	  
	    
	    /*jdbcTemplate.update("insert into UI_UPLOAD_HISTORY (NOTES,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,UI_UPLOAD_PROFILE_ID,UI_UPLOAD_VERSION,DOWNLOAD_FILE,STATUS,FILE_NAME) " + "values(?,?,?,?,?,?,?,?,?,?)",
				new Object[] {data.getNotes(),data.getCreatedBy(),data.getCreatedDate(),data.getUpdatedBy(),data.getUpdatedDate(),data.getUploadprofileId(),intversion,blobdata,data.getStatus(),data.getFilename()});
		*/
				
	}


	public List<UPLOADHISTORY> findAllByFeeName(String feedname) {
		int profileId = jdbcTemplate.queryForObject("select UI_UPLOAD_PROFILE_ID from UI_UPLOAD_PROFILE where DESCRIPTION=?", new Object[] { feedname }, Integer.class);
		log.info("profiled in UploadProfileRepository getprofileidbyfeedname ..."+profileId);
		return jdbcTemplate.query("select * from UI_UPLOAD_HISTORY where UI_UPLOAD_PROFILE_ID=?", new Object[] { profileId }, new StatisticsRowMapper());
	}


	/**
	 * To read blob from profile history table based on fiename
	 * @param filename
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public ByteArrayOutputStream readDataFromBlob(String filename) throws SQLException, IOException {
		Statement statement = null;
		ResultSet rs = null;
	    Blob blob = null;
	    byte[] buf = new byte[1024];
		Connection  connection =jdbcTemplate.getDataSource().getConnection();
		String strQuery = "SELECT DOWNLOAD_FILE FROM UI_UPLOAD_HISTORY WHERE (FILE_NAME,UPDATED_DATE) in (select FILE_NAME, max(UPDATED_DATE) from UI_UPLOAD_HISTORY where FILE_NAME="+"'"+filename+"'"+" group by FILE_NAME)";
		log.info("query is :"+strQuery);
		statement = connection.createStatement();
		rs = statement.executeQuery(strQuery);
		InputStream in=null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while(rs.next())
		{
			blob =rs.getBlob("DOWNLOAD_FILE");
			log.info("blob isL:"+blob);
			in  = blob.getBinaryStream();
			log.info("input stream is L:"+in);
			int n = 0;
			while ((n=in.read(buf))>=0)
			{
				
				baos.write(buf, 0, n);
			}
			in.close();
		}
				
		rs.close();
		statement.close();
		connection.close();
		
		return baos;
		
	}
	
}

