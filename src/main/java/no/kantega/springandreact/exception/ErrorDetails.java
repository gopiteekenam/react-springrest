package no.kantega.springandreact.exception;


public class ErrorDetails {
  private String errorCode;
  private String errorMsg;
  
  public ErrorDetails() {
	  
  }

  public ErrorDetails(String errorCode, String errorMsg) {
    super();
    this.errorCode = errorCode;
    this.errorMsg = errorMsg;
  }


  public void setErrorCode(String errorCode) {
	  this.errorCode = errorCode;
  }
  
  public String getErrorCode( ) {
	  return errorCode;
  }
  
  public void setErrorMsg(String errorMsg) {
	  this.setErrorMsg(errorMsg);
  }
  
  public String getErrorMsg() {
	  return errorMsg;
  }



}