package no.kantega.springandreact.repository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import no.kantega.springandreact.model.STG_CCAR_STATISTICS;

@Repository
public class CustomerRepository {
	
	final static org.slf4j.Logger log = LoggerFactory.getLogger(CustomerRepository.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public STG_CCAR_STATISTICS findById(String id) {
		return (STG_CCAR_STATISTICS) jdbcTemplate.queryForObject("select * from REF_CCAR_STAT_2000_2014 where PROD_ID=?", new Object[] { id },
				new BeanPropertyRowMapper<STG_CCAR_STATISTICS>(STG_CCAR_STATISTICS.class));
	}
	
	class StatisticsRowMapper implements RowMapper<STG_CCAR_STATISTICS> {


		@Override
		public STG_CCAR_STATISTICS mapRow(ResultSet rs, int rowNum) throws SQLException {
			STG_CCAR_STATISTICS stgccarStatistics = new STG_CCAR_STATISTICS();
			stgccarStatistics.setPROD_ID(rs.getString("PROD_ID"));
			stgccarStatistics.setPRODUCT_GROUP(rs.getString("PRODUCT_GROUP"));
			stgccarStatistics.setPRODUCT_LEVEL_1(rs.getString("PRODUCT_LEVEL_1"));
			stgccarStatistics.setPRODUCT_LEVEL_2(rs.getString("PRODUCT_LEVEL_2"));
			stgccarStatistics.setPRODUCT_LEVEL_3(rs.getString("PRODUCT_LEVEL_3"));
			stgccarStatistics.setQRM_MAPPED_CATEGORY(rs.getString("QRM_MAPPED_CATEGORY"));
			stgccarStatistics.setSTANDARD_OF_REFERENCE_DESC(rs.getString("STANDARD_OF_REFERENCE_DESC"));
			stgccarStatistics.setMANAGEMENT_PRODUCT_DESC(rs.getString("MANAGEMENT_PRODUCT_DESC"));
			return stgccarStatistics;
		}	

	}

	public List<STG_CCAR_STATISTICS> findAll() {
		return jdbcTemplate.query("select * from REF_CCAR_STAT_2000_2014", new StatisticsRowMapper());
	}
	

	public int insert(STG_CCAR_STATISTICS data) {
		return jdbcTemplate.update("insert into REF_CCAR_STAT_2000_2014 (PROD_ID,PRODUCT_GROUP,PRODUCT_LEVEL_1,PRODUCT_LEVEL_2,PRODUCT_LEVEL_3,QRM_MAPPED_CATEGORY,STANDARD_OF_REFERENCE_DESC,MANAGEMENT_PRODUCT_DESC) " + "values(?,?,?,?,?,?,?,?)",
				new Object[] {data.getPROD_ID(),data.getPRODUCT_GROUP(),data.getPRODUCT_LEVEL_1(),data.getPRODUCT_LEVEL_2(),data.getPRODUCT_LEVEL_3(),data.getQRM_MAPPED_CATEGORY(),data.getSTANDARD_OF_REFERENCE_DESC(),data.getMANAGEMENT_PRODUCT_DESC() });
	}


	public void delete() {
		log.info("inside delete method of CustomRepository");
		SimpleJdbcCall jdbcCall =  new SimpleJdbcCall(jdbcTemplate).withProcedureName("truncate_table");
			Map<String, Object> simpleJdbcCallResult = jdbcCall.execute();
			log.info("after deleting:"+simpleJdbcCallResult.get(0));
	}


	/**
	 * To get column count from database table
	 * @return int
	 */
	public HashMap<Integer,String> getNumberOfColumnsFromDatabaseTable() {
		Map<Integer,String> map = new HashMap<Integer,String>();
		jdbcTemplate.query("select * from REF_CCAR_STAT_2000_2014", new ResultSetExtractor<Integer>() {
		
		
			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				String firstColumn = null;
				ResultSetMetaData rsmd = rs.getMetaData();
				log.info("inside result set metadata is..."+rsmd.getColumnLabel(1));
				int columncount = rsmd.getColumnCount();
				firstColumn = rsmd.getColumnLabel(1);
				map.put(columncount, firstColumn);
				log.info("inside result set metadata column count ..."+map);
				return columncount;
			}
		});
		return (HashMap<Integer, String>) map;
	}
}