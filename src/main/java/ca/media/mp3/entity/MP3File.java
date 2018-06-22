package ca.media.mp3.entity;

import java.util.List;

public class MP3File {
  private List<ID3V2Tag> tags;
  private List<MP3Frame> frames;
    
  public MP3File(byte[] mp3file) {    
  }
}
