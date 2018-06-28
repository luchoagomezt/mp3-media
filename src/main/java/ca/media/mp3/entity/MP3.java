package ca.media.mp3.entity;

public class MP3 {
  private final int length;
  private final ID3V2Tag tags;
    
  public MP3(final int[] mp3) throws IllegalArgumentException {
    if(ID3V2Tag.isAnID3V2tag(mp3)) {
      length = mp3.length;
      tags = new ID3V2Tag(mp3);
    } else {
      length = mp3.length;
      tags = null;
    }
  }
  
  public int size() {
    return length;
  }
  
  public boolean hasID3V2tag() {
    return (tags != null);
  }
  
  public ID3V2Tag getID3V2tag() {
    return tags;
  }
}
