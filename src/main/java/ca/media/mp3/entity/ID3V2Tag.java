package ca.media.mp3.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ID3V2Tag {
  public static final int HEADER_SIZE = 10;
  private final List<Frame> frameList;
  private final Header header;

  public ID3V2Tag(final int[] mp3) {
    if(!isAnID3V2tag(mp3)) {
      throw new IllegalArgumentException("Array does not contain an ID3 V2 tag");
    }

    header = new Header(mp3);
    frameList = buildFrames(mp3);  
  }

  private List<Frame> buildFrames(final int[] mp3) {
    List<Frame> list = new ArrayList<Frame>();
    if(mp3.length < Frame.HEADER_SIZE + HEADER_SIZE) {
      return list;
    }
    
    int[] frameHeader = Arrays.copyOfRange(mp3, HEADER_SIZE, Frame.HEADER_SIZE + HEADER_SIZE);
    int frameSize = Frame.calculateFrameSizeExcludingHeader(frameHeader);
    int[] frameArray = Arrays.copyOfRange(mp3, HEADER_SIZE, Frame.HEADER_SIZE + HEADER_SIZE + frameSize);
    list.add(new Frame(frameArray));
    return list;
  }

  public static int calculateTagSizeExcludingHeader(final int[] header) {
    if (header == null) {
      throw new IllegalArgumentException("Parameter is null");
    }

    if (header.length < HEADER_SIZE) {
      throw new IllegalArgumentException("Array's length is less than header's size");
    }
    
    if(header[6] > 0x7F || header[7] > 0x7F || header[8] > 0x7F || header[9] > 0x7F) {
      throw new IllegalArgumentException("One or more of the four size bytes is more than 0x7F");
    } 

    return ((header[6] * 0x80 +header[7]) * 0x80 + header[8]) * 0x80 + header[9];
  }

  public static boolean isAnID3V2tag(final int[] mp3) {
    if(mp3 == null) {
      throw new IllegalArgumentException("Parameter array is null");
    }

    if(mp3.length < HEADER_SIZE) {
      throw new IllegalArgumentException("Array's length is less than header's size");
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
    return header.majorVersion;
  }
  
  public int revisionNumber() {
    return header.revisionNumber;
  }
  
  public boolean unsynchronisation() {
    return (header.flag & 0x80) == 0x80;
  }
  
  public boolean extendedHeader() {
    return (header.flag & 0x40) == 0x40;
  }
  
  public boolean experimental() {
    return (header.flag & 0x20) == 0x20;
  }
  
  public int flags() {
    return header.flag;
  }
  
  public int size() {
    return header.size;
  }
  
  @Override
  public String toString() {
    return 
      String.format("{\"header\":{\"version\":%d, \"revision\":%d, \"flags\":%d, \"size\":%d}, \"frames\":[%s]}", 
      majorVersion(), revisionNumber(), flags(), size(), frameList.stream().map(e -> e.toString()).
      reduce((s1, s2) -> s1.concat(", ").concat(s2)).orElse(""));
  }
  
  private class Header {
    private final int majorVersion;
    private final int revisionNumber;
    private final int flag;
    private final int size;
    
    Header(final int[] data) {
      majorVersion = data[3];
      revisionNumber = data[4];
      flag = data[5];
      size = calculateTagSizeExcludingHeader(data);      
    }
  }
}
