package no.kantega.springandreact.exception;

import org.springframework.http.HttpStatus;

public class CustomizedResponseEntityExceptionHandler extends Exception {

	private static final long serialVersionUID = 1L;
	private final ErrorDetails error = new ErrorDetails();
	private final HttpStatus status;

	public CustomizedResponseEntityExceptionHandler(String message, String errorCode, HttpStatus status) {
		super(message);
		error.setErrorCode(errorCode);
		error.setErrorMsg(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public ErrorDetails getError() {
		return error;
	}
}
