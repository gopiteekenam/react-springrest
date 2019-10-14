package no.kantega.springandreact.model;

import java.util.ArrayList;

public class STG_CCAR_STATISTICS {

	// "customer_seq" is Oracle sequence name.
/*	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int PARTITION_KEY;
*/
	String PROD_ID;
	public String getPROD_ID() {
		return PROD_ID;
	}

	public void setPROD_ID(String pROD_ID) {
		PROD_ID = pROD_ID;
	}

	public String getPRODUCT_GROUP() {
		return PRODUCT_GROUP;
	}

	public void setPRODUCT_GROUP(String pRODUCT_GROUP) {
		PRODUCT_GROUP = pRODUCT_GROUP;
	}

	public String getPRODUCT_LEVEL_1() {
		return PRODUCT_LEVEL_1;
	}

	public void setPRODUCT_LEVEL_1(String pRODUCT_LEVEL_1) {
		PRODUCT_LEVEL_1 = pRODUCT_LEVEL_1;
	}

	public String getPRODUCT_LEVEL_2() {
		return PRODUCT_LEVEL_2;
	}

	public void setPRODUCT_LEVEL_2(String pRODUCT_LEVEL_2) {
		PRODUCT_LEVEL_2 = pRODUCT_LEVEL_2;
	}

	public String getPRODUCT_LEVEL_3() {
		return PRODUCT_LEVEL_3;
	}

	public void setPRODUCT_LEVEL_3(String pRODUCT_LEVEL_3) {
		PRODUCT_LEVEL_3 = pRODUCT_LEVEL_3;
	}

	public String getQRM_MAPPED_CATEGORY() {
		return QRM_MAPPED_CATEGORY;
	}

	public void setQRM_MAPPED_CATEGORY(String qRM_MAPPED_CATEGORY) {
		QRM_MAPPED_CATEGORY = qRM_MAPPED_CATEGORY;
	}

	public String getSTANDARD_OF_REFERENCE_DESC() {
		return STANDARD_OF_REFERENCE_DESC;
	}

	public void setSTANDARD_OF_REFERENCE_DESC(String sTANDARD_OF_REFERENCE_DESC) {
		STANDARD_OF_REFERENCE_DESC = sTANDARD_OF_REFERENCE_DESC;
	}

	public String getMANAGEMENT_PRODUCT_DESC() {
		return MANAGEMENT_PRODUCT_DESC;
	}

	public void setMANAGEMENT_PRODUCT_DESC(String mANAGEMENT_PRODUCT_DESC) {
		MANAGEMENT_PRODUCT_DESC = mANAGEMENT_PRODUCT_DESC;
	}

	String PRODUCT_GROUP;
	String PRODUCT_LEVEL_1;
	String PRODUCT_LEVEL_2;
	String PRODUCT_LEVEL_3;
	String QRM_MAPPED_CATEGORY;
	String STANDARD_OF_REFERENCE_DESC;
	String MANAGEMENT_PRODUCT_DESC;
	
	public STG_CCAR_STATISTICS() {
		super();
		// TODO Auto-generated constructor stub
	}

	public STG_CCAR_STATISTICS(ArrayList<String> row) {
		System.out.println("product id"+row.get(0));
		PROD_ID = row.get(0);
		PRODUCT_GROUP = row.get(1);
		PRODUCT_LEVEL_1 = row.get(2);
		PRODUCT_LEVEL_2 = row.get(3);
		PRODUCT_LEVEL_3 = row.get(4);
		QRM_MAPPED_CATEGORY = row.get(5);
		STANDARD_OF_REFERENCE_DESC = row.get(6);
		MANAGEMENT_PRODUCT_DESC = row.get(7);
	}
}