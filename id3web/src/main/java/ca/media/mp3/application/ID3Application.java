package ca.media.mp3.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ca.media.mp3.controller")
public class ID3Application 
{
  public static void main(String[] args)
  {
    SpringApplication.run(ID3Application.class, args);
  }
}
