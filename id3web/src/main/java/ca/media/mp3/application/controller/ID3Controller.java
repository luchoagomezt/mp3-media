package ca.media.mp3.application.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.web.bind.annotation.RestController;

import ca.media.mp3.application.service.ID3WebReader;
import ca.media.mp3.application.service.MP3Hal;
import ca.media.mp3.entity.MP3;
import ca.media.mp3.entity.MP3MediaException;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class ID3Controller 
{
  @Autowired
  private ID3WebReader reader;
  
  @GetMapping("/id3tag")
  @ResponseBody
  public MP3Hal readTag(@RequestParam(name="mp3", required=false, defaultValue="") String mp3StringUrl)
  {
    try {
      reader.setUrl(new URL(mp3StringUrl));
      MP3Hal mp3Hal = new MP3Hal(new MP3(reader.getURL(), reader.perform()));
      mp3Hal.add(linkTo(methodOn(ID3Controller.class).readTag(mp3StringUrl)).withSelfRel());
      return mp3Hal;
    } catch(MalformedURLException e) {
      throw new MP3MediaException(e);
    }
  }
  
  @GetMapping("/")
  @ResponseBody
  public String defaultURL()
  {
    return new String("");
  }
}
