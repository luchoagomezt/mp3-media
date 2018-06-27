package ca.media.mp3.entity;

import java.util.Arrays;

public class MP3 {
  private final int[] media;
  public final ID3V2Tag tags;
    
  public MP3(final int[] mp3) throws IllegalArgumentException {
    if(ID3V2Tag.isAnID3V2tag(mp3)) {
      media = Arrays.copyOf(mp3, mp3.length);
      tags = new ID3V2Tag(media);
    } else {
      media = Arrays.copyOf(mp3, mp3.length);
      tags = null;
    }
  }
  
  public int size() {
    return media.length;
  }
}
