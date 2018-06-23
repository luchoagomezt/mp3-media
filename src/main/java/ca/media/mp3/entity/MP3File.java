package ca.media.mp3.entity;

import java.util.ArrayList;
import java.util.List;

public class MP3File {
  private final List<ID3V2Tag> tags;
    
  public MP3File(final byte[] mp3Array) throws IllegalArgumentException {
    if (mp3Array == null) {
      throw new IllegalArgumentException("Byte array is null"); 
    }
    
    tags = new ArrayList<ID3V2Tag>();
    if (mp3Array.length == 0) {
      return;
    }
  }
  
  public List<ID3V2Tag> getID3V2Tags() {
    return tags;
  }
}
