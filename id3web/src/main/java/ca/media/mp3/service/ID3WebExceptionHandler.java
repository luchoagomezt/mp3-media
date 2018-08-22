package ca.media.mp3.service;

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
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getCause().getMessage(), request.getDescription(false));
    HttpStatus status = determineHttpStatus(ex);
    return new ResponseEntity<>(errorDetails, status);
  }

  private HttpStatus determineHttpStatus(MP3MediaException ex) {
    HttpStatus status = HttpStatus.I_AM_A_TEAPOT;
    Object obj = ex.getCause().getClass();
    
    if(obj.equals(FileNotFoundException.class)) {
      status = HttpStatus.NOT_FOUND;
    } else if (obj.equals(IllegalArgumentException.class)) {
      status = HttpStatus.BAD_REQUEST;
    } else if (obj.equals(MalformedURLException.class)) {
      status = HttpStatus.BAD_REQUEST;
    } else if (obj.equals(IOException.class)) {
      status = HttpStatus.SEE_OTHER;
    }
    return status;
  }
}
