package ca.media.mp3.entity;

import java.util.Arrays;

public class ID3V2Tag {
  private final int size;
  private final int[] id3V2tag;
  
  public ID3V2Tag(final int[] mp3) throws IllegalArgumentException {
    if(!isAnID3V2tag(mp3)) {
      throw new IllegalArgumentException("Array does not contain an ID3 V2 tag");
    }

    size = ((mp3[6] * 0x80 + mp3[7]) * 0x80 + mp3[8]) * 0x80 + mp3[9];
    id3V2tag = Arrays.copyOf(mp3, size);    
  }
  
  public static boolean isAnID3V2tag(final int[] mp3) throws IllegalArgumentException {
    if(mp3 == null) {
      throw new IllegalArgumentException("Array is null");
    }

    if(mp3.length < 10) {
      throw new IllegalArgumentException("Array is too small");
    }

    return 
        (mp3[0] == 0x49 && mp3[1] == 0x44 && mp3[2] == 0x33) &&
        (mp3[3] < 0xFF && mp3[4] < 0xFF) &&
        (mp3[5] & 0xE0) == mp3[5] &&
        (mp3[6] < 0x80 && mp3[7] < 0x80 && mp3[8] < 0x80 && mp3[9] < 0x80);
  }
    
  public int majorVersion() {
    return id3V2tag[3];
  }
  
  public int revisionNumber() {
    return id3V2tag[4];
  }
  
  public boolean unsynchronisation() {
    return (id3V2tag[5] & 0x80) == 0x80;
  }
  
  public boolean extendedHeader() {
    return (id3V2tag[5] & 0x40) == 0x40;
  }
  
  public boolean experimental() {
    return (id3V2tag[5] & 0x20) == 0x20;
  }
  
  public int flags() {
    return id3V2tag[5];
  }
  
  public int size() {
    return size;
  }
}
