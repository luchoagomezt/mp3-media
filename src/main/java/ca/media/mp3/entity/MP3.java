package ca.media.mp3.entity;

public class MP3 {
  private final int[] media;
    
  public MP3(final int[] mp3) throws IllegalArgumentException {
    if (mp3 == null) {
      throw new IllegalArgumentException("Integer array is null"); 
    }
    this.media = mp3;
  }
  
  public int size() {
    return media.length;
  }
}
