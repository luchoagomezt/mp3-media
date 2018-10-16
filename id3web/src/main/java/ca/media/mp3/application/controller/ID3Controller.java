package ca.media.mp3.application.controller;

import org.springframework.web.bind.annotation.RestController;

import ca.media.mp3.application.service.ID3WebReader;
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
  public MP3 readTag(@RequestParam(name="mp3", required=false, defaultValue="") String mp3StringUrl)
  {
    try {
      reader.setUrl(new URL(mp3StringUrl));
      return new MP3(reader.getURL(), reader.perform());
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
