package ca.media.mp3.adapter;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.media.mp3.entity.ID3V2Tag;
import ca.media.mp3.entity.MP3MediaException;

@JsonPropertyOrder(alphabetic = true)
public class JSONFormatter implements ID3TagFormatter
{

  @Override
  public String format(ID3V2Tag tag)
  {
    try {
      return new ObjectMapper().writeValueAsString(tag);
    } catch (JsonProcessingException e) {
      throw new MP3MediaException(e);
    }
  }

}
