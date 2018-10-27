package ca.media.mp3.web.service;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.media.mp3.entity.MP3;

public class HypertextMP3 extends ResourceSupport
{
  private final MP3 mp3;
  
  @JsonCreator
  public HypertextMP3(@JsonProperty("mp3") MP3 mp3)
  {
    this.mp3 = mp3;
  }

  public MP3 getMp3()
  {
    return mp3;
  }

}
