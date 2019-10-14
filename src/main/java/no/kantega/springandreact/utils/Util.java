package no.kantega.springandreact.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;

import no.kantega.springandreact.model.STG_CCAR_STATISTICS;
import no.kantega.springandreact.model.UPLOADPROFILE;

public class Util {

	final static org.slf4j.Logger log = LoggerFactory.getLogger(Util.class);

	@SuppressWarnings("deprecation")
	public static ArrayList<STG_CCAR_STATISTICS> read(InputStream in)
			throws ParseException, EncryptedDocumentException, InvalidFormatException {
		ArrayList<STG_CCAR_STATISTICS> data = new ArrayList<>();
		try {
			// FileInputStream excelFile = new FileInputStream(new
			// File(FILE_NAME));
			XSSFWorkbook workbook = new XSSFWorkbook(in);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {
				log.info("inside iterator");
				STG_CCAR_STATISTICS statistics = new STG_CCAR_STATISTICS();
				Row row = iterator.next();
				log.info("product id:::" + row.getCell(0).toString());
				statistics.setPROD_ID(row.getCell(0).toString());
				log.info("product group is:::" + row.getCell(1).toString());
				statistics.setPRODUCT_GROUP(row.getCell(1).toString());
				log.info("product level 1:::" + row.getCell(2).toString());
				statistics.setPRODUCT_LEVEL_1(row.getCell(2).toString());
				log.info("product level 2:::" + row.getCell(3).toString());
				statistics.setPRODUCT_LEVEL_2(row.getCell(3).toString());
				log.info("product level 3:::" + row.getCell(4).toString());
				statistics.setPRODUCT_LEVEL_3(row.getCell(4).toString());
				log.info("mapped category:::" + row.getCell(5).toString());
				statistics.setQRM_MAPPED_CATEGORY(row.getCell(5).toString());
				log.info("standard of reference:::" + row.getCell(6).toString());
				statistics.setSTANDARD_OF_REFERENCE_DESC(row.getCell(6).toString());
				log.info("management product desc:" + row.getCell(7).toString());
				statistics.setMANAGEMENT_PRODUCT_DESC(row.getCell(7).toString());
				if (row.getCell(8) != null && !row.getCell(8).toString().isEmpty()) {
					extracted();
				}
				log.info("product id in statistics:" + statistics.getPROD_ID());
				data.add(statistics);
			}

			workbook.close();

			/*
			 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); Date date1 =
			 * sdf.parse("2018-07-31"); Date date = new Date();
			 * 
			 * if(date.after(date1)) throw new RuntimeException("Wrong data");
			 */
			System.out.println("*****************");
			for (STG_CCAR_STATISTICS a : data) {
				System.out.println(a.getPROD_ID());
			}

			return data;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}

	public static ArrayList<STG_CCAR_STATISTICS> readCSV(InputStream in, int numberofcolumns, String columnname) throws ParseException, IOException {
		System.out.println("Util ... inside readCSV");
		ArrayList<STG_CCAR_STATISTICS> data = new ArrayList<>();
		ArrayList<String> row = null;
		{

			BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			CSVParser parser = new CSVParser(br, CSVFormat.DEFAULT.withDelimiter('|'));
			
			CSVRecord firstRecord = parser.iterator().next();
			int numColumns = firstRecord.size();
			
			log.info("number of columns..."+firstRecord.get(0));
			if(!columnname.equals(firstRecord.get(0))) {
				throw new RuntimeException("column name does not match");
			}
			if(numColumns > numberofcolumns) {
				extracted();
			}
			List<CSVRecord> list = parser.getRecords();
			log.info("list size in readCSV is:" + list.size());
			for (CSVRecord record : list) {
				row = new ArrayList<>();
				
				String prodId = record.get(0);
				log.info("product id..." + prodId);
				row.add(prodId);
				String productGroup = record.get(1);
				log.info("product group..." + productGroup);
				row.add(productGroup);
				String productLevel1 = record.get(2);
				row.add(productLevel1);
				String productLevel2 = record.get(3);
				row.add(productLevel2);
				String productLevel3 = record.get(4);
				row.add(productLevel3);
				String qRMMapped = record.get(5);
				row.add(qRMMapped);
				String standardOfReference = record.get(6);
				row.add(standardOfReference);
				String managementProduct = record.get(7);
				log.info("management" + managementProduct);
				row.add(managementProduct);
				data.add(new STG_CCAR_STATISTICS(row));
			}

			parser.close();
			return data;

		}
	}

	private static void extracted() {
		throw new ArrayIndexOutOfBoundsException("Number of columns exceeds the limit");
	}

	public static ByteArrayOutputStream getListFromStream(ByteArrayOutputStream data) {
		return data;
	}

}
