package ca.media.mp3.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MP3 {
  private final List<ID3V2Tag> tags;
    
  public MP3(final Stream<Integer> stream) throws IllegalArgumentException {
    if (stream == null) {
      throw new IllegalArgumentException("Stream is null"); 
    }
    
    tags = new ArrayList<ID3V2Tag>();
    if (stream.count() == 0) {
      return;
    }
  }
  
  public List<ID3V2Tag> getID3V2Tags() {
    return tags;
  }
}
