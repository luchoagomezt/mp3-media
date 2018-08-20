package ca.media.mp3.controller;

import org.springframework.web.bind.annotation.RestController;

import ca.media.mp3.entity.ID3Reader;
import ca.media.mp3.entity.ID3V2Tag;
import ca.media.mp3.service.ID3Service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class ID3Controller 
{
  @GetMapping("/id3tag")
  @ResponseBody
  public ID3V2Tag readTag(@RequestParam(name="mp3", required=false, defaultValue="") String mp3) 
  {
    ID3Reader service = new ID3Service(mp3);
    return service.perform();
  }
}
