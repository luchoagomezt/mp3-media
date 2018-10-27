package ca.media.mp3.web.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.media.mp3.web.service.HypertextMP3;
import ca.media.mp3.web.service.ID3WebReader;
import ca.media.mp3.entity.MP3;
import ca.media.mp3.entity.MP3MediaException;

@RestController
public class ID3Controller 
{
  @GetMapping("/")
  public String hello()
  {
    return "Hello  Spring Boot!";
  }

  @Autowired
  private ID3WebReader reader;
  
  @GetMapping("/id3tag")
  public HypertextMP3 readTag(@RequestParam(name="mp3", required=false, defaultValue="") String mp3StringUrl)
  {
    try {
      reader.setUrl(new URL(mp3StringUrl));
      HypertextMP3 mp3 = new HypertextMP3(new MP3(reader.getURL(), reader.perform()));
      mp3.add(linkTo(methodOn(ID3Controller.class).readTag(mp3StringUrl)).withSelfRel());
      return mp3;
    } catch(MalformedURLException e) {
      throw new MP3MediaException(e);
    }
  }
}
