package no.kantega.springandreact.repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
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

import no.kantega.springandreact.model.UPLOADPROFILE;

@Repository
public class UploadProfileRepository {
	
	final static org.slf4j.Logger log = LoggerFactory.getLogger(UploadProfileRepository.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public UPLOADPROFILE findById(String id) {
		return (UPLOADPROFILE) jdbcTemplate.queryForObject("select * from UI_UPLOAD_PROFILE where UI_UPLOAD_PROFILE_ID=?", new Object[] { id },
				new BeanPropertyRowMapper<UPLOADPROFILE>(UPLOADPROFILE.class));
	}
	
	class StatisticsRowMapper implements RowMapper<UPLOADPROFILE> {


		@Override
		public UPLOADPROFILE mapRow(ResultSet rs, int rowNum) throws SQLException {
			UPLOADPROFILE uploadprofile = new UPLOADPROFILE();
			uploadprofile.setDescription(rs.getString("DESCRIPTION"));
			uploadprofile.setActiveInd(rs.getString("ACTIVE_IND"));
			uploadprofile.setColumnlist(rs.getString("COLUMN_LIST"));
			uploadprofile.setCreatedBy(System.getProperty("user.name"));
			java.sql.Timestamp date = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
			uploadprofile.setCreatedDate(date);
			uploadprofile.setEffectiveFrom(rs.getTimestamp("EFFECTIVE_FROM"));
			uploadprofile.setEffectiveTo(rs.getTimestamp("EFFECTIVE_TO"));
			uploadprofile.setEntity(rs.getString("ENTITY"));
			uploadprofile.setPortfolio(rs.getString("PORTFOLIO"));
			uploadprofile.setShortDescription(rs.getString("SHORT_DESCRIPTION"));
			uploadprofile.setTargetSchemaName(rs.getString("TARGET_SCHEMA_NAME"));
			uploadprofile.setTargetTableName(rs.getString("TARGET_TABLE_NAME"));
			uploadprofile.setTemplatedownload(rs.getBytes("TEMPLATE_DOWNLOAD"));
			uploadprofile.setCreatedBy(System.getProperty("user.name"));
			uploadprofile.setCreatedDate(date);
			uploadprofile.setNumOfCols(rs.getInt("NUM_OF_COLUMNS"));
			uploadprofile.setLastVersion(rs.getInt("LAST_VERSION"));
			return uploadprofile;
		}	

	}

	public List<UPLOADPROFILE> findAll() {
		return jdbcTemplate.query("select * from UI_UPLOAD_PROFILE", new StatisticsRowMapper());
	}
	
	public OutputStream readDataFromBlob(String feedname) throws SQLException, IOException, ClassNotFoundException {
		Statement statement = null;
		ResultSet rs = null;
	    Blob blob = null;
	    byte[] buf = new byte[1024];
		Connection  connection =jdbcTemplate.getDataSource().getConnection(); 
		String strQuery = "SELECT TEMPLATE_DOWNLOAD FROM UI_UPLOAD_PROFILE WHERE DESCRIPTION="+"'"+feedname+"'";
		log.info("query is :"+strQuery);
		statement = connection.createStatement();
		rs = statement.executeQuery(strQuery);
		InputStream in=null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while(rs.next())
		{
			blob =rs.getBlob("TEMPLATE_DOWNLOAD");
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
	
	public void insert(UPLOADPROFILE data) throws IOException {
		log.info("inside insert method of upload profile repository"+data.getTemplatedownload());
		
		String sql = "insert into UI_UPLOAD_PROFILE (TEMPLATE_DOWNLOAD) values (?)";
		LobHandler lobHandler; lobHandler = new DefaultLobHandler();
	    
	    jdbcTemplate.execute(sql, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
			
	    	@Override
	          protected void setValues(PreparedStatement ps, LobCreator lobCreator)
	                  throws SQLException, DataAccessException {
	              ByteArrayInputStream inputStream = new ByteArrayInputStream(data.getTemplatedownload());
	              lobCreator.setBlobAsBinaryStream(ps, 1, inputStream, data.getTemplatedownload().length);
	          }
	      });

		/*byte[] blobdata = new byte[data.getTemplatedownload().available()];
		return jdbcTemplate.update("insert into UI_UPLOAD_PROFILE (TEMPLATE_DOWNLOAD) " + "values (?)",
				new Object[] {blobdata });*/
	}

	public int getProfileIdByFeedname(String feedname) {
		
		int profileId = jdbcTemplate.queryForObject("select UI_UPLOAD_PROFILE_ID from UI_UPLOAD_PROFILE where DESCRIPTION=?", new Object[] { feedname }, Integer.class);
		log.info("profiled in UploadProfileRepository getprofileidbyfeedname ..."+profileId);
		return profileId;
	}
}