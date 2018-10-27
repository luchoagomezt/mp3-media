package ca.media.mp3.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
"ca.media.mp3.web.controller",
"ca.media.mp3.web.service"})
public class ID3Application 
{
  public static void main(String[] args)
  {
    SpringApplication.run(ID3Application.class, args);  
  }
}
