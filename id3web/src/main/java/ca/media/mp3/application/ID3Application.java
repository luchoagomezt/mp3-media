package ca.media.mp3.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
  "ca.media.mp3.entity",
  "ca.media.mp3.controller",
  "ca.media.mp3.service"})
public class ID3Application
{
  public static void main(String[] args)
  {
    SpringApplication.run(ID3Application.class, args);
  }
}
