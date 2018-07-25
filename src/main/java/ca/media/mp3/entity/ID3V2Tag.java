package ca.media.mp3.entity;

import java.util.Arrays;

public class ID3V2Tag {
  public static final int ID3V2_TAG_HEADER_SIZE = 10;
  
  private final Frame[] frame;
  private final int size;
  private final int[] header;
  
  public ID3V2Tag(final int[] mp3) {
    if(!isAnID3V2tag(mp3)) {
      throw new IllegalArgumentException("Array does not contain an ID3 V2 tag");
    }

    size = calculateTagSizeExcludingHeader(mp3);
    header = Arrays.copyOfRange(mp3, 0, ID3V2_TAG_HEADER_SIZE);
    frame = buildFrames(mp3);  
  }

  private Frame[] buildFrames(final int[] mp3) {
    if(mp3.length < Frame.FRAME_HEADER_SIZE + ID3V2_TAG_HEADER_SIZE) {
      return new Frame[]{};
    }
    
    int[] frameHeader = Arrays.copyOfRange(mp3, ID3V2_TAG_HEADER_SIZE, Frame.FRAME_HEADER_SIZE + ID3V2_TAG_HEADER_SIZE);
    int frameSize = Frame.calculateFrameSizeExcludingHeader(frameHeader);
    int[] frameArray = Arrays.copyOfRange(mp3, ID3V2_TAG_HEADER_SIZE, Frame.FRAME_HEADER_SIZE + ID3V2_TAG_HEADER_SIZE + frameSize);
    return new Frame[]{new Frame(frameArray)};
  }

  public static int calculateTagSizeExcludingHeader(final int[] header) {
    if (header == null) {
      throw new IllegalArgumentException("Parameter is null");
    }

    if (header.length < ID3V2_TAG_HEADER_SIZE) {
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

    if(mp3.length < ID3V2_TAG_HEADER_SIZE) {
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
    return header[3];
  }
  
  public int revisionNumber() {
    return header[4];
  }
  
  public boolean unsynchronisation() {
    return (header[5] & 0x80) == 0x80;
  }
  
  public boolean extendedHeader() {
    return (header[5] & 0x40) == 0x40;
  }
  
  public boolean experimental() {
    return (header[5] & 0x20) == 0x20;
  }
  
  public int flags() {
    return header[5];
  }
  
  public int size() {
    return size;
  }
  
  @Override
  public String toString() {
    return 
      String.format("{\"header\":{\"version\":%d, \"revision\":%d, \"flags\":%d, \"size\":%d}, \"frames\":%s}", 
      majorVersion(), revisionNumber(), flags(), size(), Arrays.toString(frame));
  }  
}
