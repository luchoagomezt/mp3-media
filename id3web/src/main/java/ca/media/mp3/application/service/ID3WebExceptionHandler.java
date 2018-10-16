package ca.media.mp3.application.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ca.media.mp3.entity.MP3MediaException;

@ControllerAdvice
@RestController
public class ID3WebExceptionHandler extends ResponseEntityExceptionHandler 
{
  @ExceptionHandler(MP3MediaException.class)
  public final ResponseEntity<ErrorDetails> handleMP3MediaException(MP3MediaException ex, WebRequest request)
  {
    ErrorDetails errorDetails = getErrorDetails(ex, request);
    HttpStatus status = determineHttpStatus(ex);
    return new ResponseEntity<>(errorDetails, status);
  }

  private ErrorDetails getErrorDetails(MP3MediaException ex, WebRequest request)
  {
    String message = ex.getMessage();
   if (ex.getCause() != null) {
      message = ex.getCause().getMessage();
   }
    return new ErrorDetails(new Date(), message, request.getDescription(false));
  }

  private HttpStatus determineHttpStatus(MP3MediaException ex)
  {

    if(ex.getCause() == null) {
      return HttpStatus.BAD_REQUEST;
    }

    Object obj = ex.getCause().getClass();
    if(obj.equals(FileNotFoundException.class)) {
      return HttpStatus.NOT_FOUND;
    }

    if (obj.equals(MalformedURLException.class)) {
      return HttpStatus.BAD_REQUEST;
    }

    if (obj.equals(IOException.class)) {
      return HttpStatus.UNPROCESSABLE_ENTITY;
    }

    return HttpStatus.BAD_REQUEST;
  }
}
