package ca.media.mp3.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ID3V2Tag {
  private final Map<String, Integer> header;
  private final Frame[] frame;
  private final int majorVersion;
  private final int revisionNumber;
  private final int flags;
  private final int size;
  
  public ID3V2Tag(final int[] mp3) throws IllegalArgumentException {
    if(!isAnID3V2tag(mp3)) {
      throw new IllegalArgumentException("Array does not contain an ID3 V2 tag");
    }

    majorVersion = mp3[3];
    revisionNumber = mp3[4];
    flags = mp3[5];
    size = ((mp3[6] * 0x80 + mp3[7]) * 0x80 + mp3[8]) * 0x80 + mp3[9];
    header = new HashMap<>();
    header.put("majorVersion", majorVersion);
    header.put("revisionNumber", revisionNumber);
    header.put("flags", flags);
    header.put("size", size);
    if(mp3.length >= 20) {
      frame = new Frame[]{new Frame(Arrays.copyOfRange(mp3, 10, 20))};
    } else {
      frame = new Frame[]{};
    }
  }
  
  public static boolean isAnID3V2tag(final int[] mp3) throws IllegalArgumentException {
    if(mp3 == null) {
      throw new IllegalArgumentException("Array is null");
    }

    if(mp3.length < 10) {
      throw new IllegalArgumentException("Array is too small");
    }

    return 
        mp3[0] == 0x49 &&
        mp3[1] == 0x44 &&
        mp3[2] == 0x33 &&
        mp3[3] < 0xFF &&
        mp3[4] < 0xFF &&
        mp3[5] == (mp3[5] & 0xE0) &&
        mp3[6] < 0x80 &&
        mp3[7] < 0x80 &&
        mp3[8] < 0x80 &&
        mp3[9] < 0x80;
  }
    
  public int majorVersion() {
    return majorVersion;
  }
  
  public int revisionNumber() {
    return revisionNumber;
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
  
  public int flags() {
    return flags;
  }
  
  public int size() {
    return size;
  }
  
  @Override
  public String toString() {
    return 
      String.format("{\"version\":%d, \"revision\":%d, \"flags\":%d, \"size\":%d, \"frames\":%s}", 
      majorVersion, revisionNumber, flags, size, Arrays.toString(frame));
  }
  
  public Map<String, Integer> header() {
    return header;
  }
}
