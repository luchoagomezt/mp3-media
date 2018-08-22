package ca.media.mp3.controller;

import org.springframework.web.bind.annotation.RestController;

import ca.media.mp3.entity.ID3V2Tag;
import ca.media.mp3.service.ID3WebReader;

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
  public ID3V2Tag readTag(@RequestParam(name="mp3", required=false, defaultValue="") String mp3) 
  {
    reader.setUrl(mp3);
    return reader.perform();
  }
}
