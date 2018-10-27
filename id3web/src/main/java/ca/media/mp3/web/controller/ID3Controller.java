package ca.media.mp3.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ID3Controller 
{
  @GetMapping("/")
  public String hello()
  {
    return "Hello  Spring Boot!";
  }
}
