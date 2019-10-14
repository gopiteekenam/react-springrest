package no.kantega.springandreact.model;

import java.sql.Timestamp;;

public class UPLOADHISTORY {
	
	private String notes;
	private String createdBy;
	private Timestamp createdDate;
	private String updatedBy;
	private Timestamp updatedDate;
	private int uploadprofileId;
	private String uploadVersion;
	private byte[] downloadFile;
	private String status;
	private String filename;
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
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
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public int getUploadprofileId() {
		return uploadprofileId;
	}
	public void setUploadprofileId(int uploadprofileId) {
		this.uploadprofileId = uploadprofileId;
	}
	public String getUploadVersion() {
		return uploadVersion;
	}
	public void setUploadVersion(String uploadVersion) {
		this.uploadVersion = uploadVersion;
	}
	public byte[] getDownloadFile() {
		return downloadFile;
	}
	public void setDownloadFile(byte[] downloadFile) {
		this.downloadFile = downloadFile;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	
	
	

}
