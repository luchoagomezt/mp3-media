package ca.media.mp3.entity;

import java.util.ArrayList;
import java.util.List;

public class MP3File {
  private List<ID3V2Tag> tags;
  private List<MP3Frame> frames;
    
  public MP3File(byte[] mp3Array) throws IllegalArgumentException {
    if (mp3Array == null) {
      throw new IllegalArgumentException("Byte array is null"); 
    }
    
    tags = new ArrayList<ID3V2Tag>();
    frames = new ArrayList<MP3Frame>();
    if (mp3Array.length == 0) {
      return;
    }
  }
  
  public List<ID3V2Tag> getID3V2Tags() {
    return tags;
  }
}
