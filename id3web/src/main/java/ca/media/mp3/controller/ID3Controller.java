package ca.media.mp3.controller;

import org.springframework.web.bind.annotation.RestController;

import ca.media.mp3.entity.MP3;
import ca.media.mp3.entity.MP3MediaException;
import ca.media.mp3.service.ID3WebReader;

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
      reader.setUrl(mp3StringUrl);
      URL url = new URL(mp3StringUrl);
      return new MP3(url, reader.perform());
    } catch(MalformedURLException e) {
      throw new MP3MediaException(e);
    }
  }
}
