package ca.media.mp3.entity;

public class ID3V2Tag {
  private final int version;
  private final int revision;
  private final int flags;
  private final int size;
  
  public ID3V2Tag(final int[] mp3) throws IllegalArgumentException {
    if(mp3 == null) {
      throw new IllegalArgumentException("Array is null");
    }
    
    if(mp3.length < 10) {
      throw new IllegalArgumentException("Array is too small");
    }
    
    if(!isAnID3V2tag(mp3)) {
      throw new IllegalArgumentException("Array does not contain an ID3 V2 tag");
    }
    
    version = mp3[3];
    revision = mp3[4];
    flags = mp3[5];
    size = ((mp3[6] * 0x80 + mp3[7]) * 0x80 + mp3[8]) * 0x80 + mp3[9];
  }
  
  public static boolean isAnID3V2tag(final int[] mp3) {
    return 
        (mp3[0] == 49 && mp3[1] == 44 && mp3[2] == 33) &&
        (mp3[3] < 0xFF && mp3[4] < 0xFF) &&
        (mp3[5] & 0xE0) == mp3[5] &&
        (mp3[6] < 0x80 && mp3[7] < 0x80 && mp3[8] < 0x80 && mp3[9] < 0x80);
  }
    
  public int majorVersion() {
    return version;
  }
  
  public int revisionNumber() {
    return revision;
  }
  
  public boolean unsynchronisation() {
    return (flags & 0x80) == 0x80;
  }
  
  public boolean extendedHeader() {
    return (flags & 0x40) == 0x40;
  }
  
  public boolean experimental() {
    return (flags & 0x20) == 0x20;
  }
  
  public int size() {
    return size;
  }
}
