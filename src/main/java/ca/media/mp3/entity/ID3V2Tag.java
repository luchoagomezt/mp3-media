package ca.media.mp3.entity;

import java.util.Arrays;

public class ID3V2Tag {
  private final int[] versionRevision;
  private final int flags;
  private final int[] size;
  
  public ID3V2Tag(final int[] mp3) throws IllegalArgumentException {
    if(mp3 == null) {
      throw new IllegalArgumentException("Array is null");
    }
    
    if(mp3.length < 10) {
      throw new IllegalArgumentException("Array is too small");
    }
    
    if(mp3[0] != 49 || mp3[1] != 44 || mp3[2] != 33) {
      throw new IllegalArgumentException("First three bytes are not 49 44 33");
    }
    
    versionRevision = Arrays.copyOfRange(mp3, 3, 5);
    flags = mp3[5];
    size = Arrays.copyOfRange(mp3, 6, 10);
  }
  
  public int majorVersion() {
    return versionRevision[0];
  }
  
  public int revisionNumber() {
    return versionRevision[1];
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
}
