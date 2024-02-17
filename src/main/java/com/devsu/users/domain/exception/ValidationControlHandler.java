package com.devsu.users.domain.exception;

import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationControlHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {NotFoundException.class})
  protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
    ErrorDto errorDto = ErrorDto.builder()
        .message(ex.getMessage())
        .timeStamp(String.valueOf(new Date(System.currentTimeMillis())))
        .build();
    return handleExceptionInternal(ex, errorDto, new HttpHeaders(), HttpStatus.NOT_FOUND,
        request);
  }

  @ExceptionHandler(value = {BadRequestException.class})
  protected ResponseEntity<Object> handleConflictBadRequest(RuntimeException ex,
      WebRequest request) {
    ErrorDto errorDto = ErrorDto.builder()
        .message(ex.getMessage())
        .timeStamp(String.valueOf(new Date(System.currentTimeMillis())))
        .source(((ServletWebRequest) request).getRequest().getRequestURL().toString())
        .build();
    return handleExceptionInternal(ex, errorDto, new HttpHeaders(), HttpStatus.BAD_REQUEST,
        request);
  }
}