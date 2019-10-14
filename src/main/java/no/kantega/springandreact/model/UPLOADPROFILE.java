package no.kantega.springandreact.model;

import java.sql.Date;
import java.sql.Timestamp;



public class UPLOADPROFILE {
	
	private String description;
	private String shortDescription;
	private String entity;
	private String portfolio;
	private String targetSchemaName;
	private String targetTableName;
	private Timestamp effectiveFrom;
	private Timestamp effectiveTo;
	private String activeInd;
	private String createdBy;
	private Timestamp createdDate;
	private String upatedBy;
	private Date updatedDate;
	private byte[] templatedownload;
	private String columnlist;
	private int numOfCols;
	private int lastVersion;
	public int getNumOfCols() {
		return numOfCols;
	}
	public void setNumOfCols(int numOfCols) {
		this.numOfCols = numOfCols;
	}
	public int getLastVersion() {
		return lastVersion;
	}
	public void setLastVersion(int lastVersion) {
		this.lastVersion = lastVersion;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getPortfolio() {
		return portfolio;
	}
	public void setPortfolio(String portfolio) {
		this.portfolio = portfolio;
	}
	public String getTargetSchemaName() {
		return targetSchemaName;
	}
	public void setTargetSchemaName(String targetSchemaName) {
		this.targetSchemaName = targetSchemaName;
	}
	public String getTargetTableName() {
		return targetTableName;
	}
	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}
	public Timestamp getEffectiveFrom() {
		return effectiveFrom;
	}
	public void setEffectiveFrom(Timestamp effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}
	public Timestamp getEffectiveTo() {
		return effectiveTo;
	}
	public void setEffectiveTo(Timestamp effectiveTo) {
		this.effectiveTo = effectiveTo;
	}
	public String getActiveInd() {
		return activeInd;
	}
	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpatedBy() {
		return upatedBy;
	}
	public void setUpatedBy(String upatedBy) {
		this.upatedBy = upatedBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public byte[] getTemplatedownload() {
		return templatedownload;
	}
	public void setTemplatedownload(byte[] blob) {
		this.templatedownload = blob;
	}
	public String getColumnlist() {
		return columnlist;
	}
	public void setColumnlist(String columnlist) {
		this.columnlist = columnlist;
	}
	
	
	

}
