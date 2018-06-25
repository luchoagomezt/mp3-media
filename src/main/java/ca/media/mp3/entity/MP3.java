package ca.media.mp3.entity;

import java.util.Arrays;

public class MP3 {
  private final int[] media;
  public final ID3V2Tag tags;
    
  public MP3(final int[] mp3) throws IllegalArgumentException {
    if (mp3 == null) {
      throw new IllegalArgumentException("Integer array is null"); 
    }
    media = Arrays.copyOf(mp3, mp3.length);
    tags = new ID3V2Tag(media);
  }
  
  public int size() {
    return media.length;
  }
}
