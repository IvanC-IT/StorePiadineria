package it.course.piadac4.exception;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import it.course.piadac4.payload.response.ApiResponseCustom;
import it.course.piadac4.payload.response.ResponseEntityHandler;

@ControllerAdvice
public class ApiExceptionValidationHandler {

	@ExceptionHandler({ SQLIntegrityConstraintViolationException.class })
	public ResponseEntity<ApiResponseCustom> handleSQLIntegrityConstraintViolationException(
			SQLIntegrityConstraintViolationException ex, HttpServletRequest request) {

		ResponseEntityHandler response;
		String msg = "SQL Error: " + ex.getErrorCode() + " - SQLState : " + ex.getSQLState() + " - " + ex.getMessage();
		response = new ResponseEntityHandler(msg, request, HttpStatus.BAD_REQUEST);
		return response.getResponseEntity();

	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
		ApiFieldError apiFieldError = new ApiFieldError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
				ex.getLocalizedMessage());
		return new ResponseEntity<Object>(apiFieldError, apiFieldError.getStatus());
	}

	@ExceptionHandler({ MissingServletRequestParameterException.class })
	public ResponseEntity<Object> handleMissingValue(MissingServletRequestParameterException ex) {
		ApiFieldError apiFieldError = new ApiFieldError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
				ex.getLocalizedMessage());
		return new ResponseEntity<Object>(apiFieldError, apiFieldError.getStatus());
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
		ApiFieldError apiFieldError = new ApiFieldError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
				ex.getLocalizedMessage());
		return new ResponseEntity<Object>(apiFieldError, apiFieldError.getStatus());
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<Object> handleArgumentNotValidValue(MethodArgumentNotValidException ex) {

		BindingResult bindingResults = ex.getBindingResult();
		List<String> errors = bindingResults.getFieldErrors().stream().map(e -> {
			return e.getField() + ": " + e.getDefaultMessage();
		}).collect(Collectors.toList());

		ApiFieldError apiFieldError = new ApiFieldError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
				errors.toString());
		return new ResponseEntity<Object>(apiFieldError, apiFieldError.getStatus());
	}

	@ExceptionHandler({ BindException.class })
	public ResponseEntity<Object> handleBindException(BindException ex) {

		BindingResult bindingResults = ex.getBindingResult();
		List<String> errors = bindingResults.getFieldErrors().stream().map(e -> {
			return e.getField() + ": " + e.getDefaultMessage();
		}).collect(Collectors.toList());

		ApiFieldError apiFieldError = new ApiFieldError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
				errors.toString());
		return new ResponseEntity<Object>(apiFieldError, apiFieldError.getStatus());
	}

}
