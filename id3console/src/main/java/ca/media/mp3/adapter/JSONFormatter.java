package ca.media.mp3.adapter;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.media.mp3.entity.MP3;
import ca.media.mp3.entity.MP3MediaException;

@JsonPropertyOrder(alphabetic = true)
public class JSONFormatter implements MP3Formatter
{

  @Override
  public String format(MP3 mp3)
  {
    try {
      return new ObjectMapper().writeValueAsString(mp3);
    } catch (JsonProcessingException e) {
      throw new MP3MediaException(e);
    }
  }

}
