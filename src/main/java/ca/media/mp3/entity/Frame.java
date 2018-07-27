package ca.media.mp3.entity;

import java.util.ArrayList;
import java.util.List;

public class Frame {
  public static final int HEADER_SIZE = 10;  
  private final String content;
  private final Header header;

  public Frame(int[] data) throws IllegalArgumentException {
    if(!isAValidFrame(data)) {
      throw new IllegalArgumentException("Array is not a valid frame");
    }
    
    header = new Header(data);
    content = getFrameContent(data);
  }

  private String getFrameContent(final int[] intArray) {
    List<Character> content = new ArrayList<>();
    if (header.size == 0) {
      return "";
    }
    int encoding = intArray[HEADER_SIZE];
    int offset = 1;
    if (encoding == 0xFF || encoding == 0xFE) {
      offset = 2;
    }
    for(int i = HEADER_SIZE + offset; i < intArray.length; i++) {
      content.add(new Character((char)intArray[i]));
    }
    return content.stream().map(c -> c.toString()).reduce((s1, s2) -> s1.concat(s2)).orElse("");
  }

  public static boolean isAValidFrame(final int[] data) throws IllegalArgumentException {
    if(data == null) {
      throw new IllegalArgumentException("Array is null");
    }
    if(data.length < HEADER_SIZE) {
      throw new IllegalArgumentException("Array is too small");
    }

    return Header.isItAValidSize(data);
  }

  public static int calculateFrameSizeExcludingHeader(final int[] data) {
    if (data == null) {
      throw new IllegalArgumentException("Parameter is null");
    }

    if (data.length < HEADER_SIZE) {
      throw new IllegalArgumentException("Array's length is less than header's size");
    }

    if(!Header.isItAValidSize(data)) {
      throw new IllegalArgumentException("One or more of the four size bytes is more or equal to " + Header.MAXIMUM_SIZE_VALUE);
    } 

    return Header.calculateTheContentLength(data);
  }

  public String toString() {
    return String.format("{\"id\":\"%s\", \"size\":%d, \"flags\":{\"first\":%d, \"second\":%d}, \"content\":\"%s\"}", 
      header.id, header.size, header.firstFlag, header.secondFlag, content);
  }

  private static class Header {
    static final int MAXIMUM_SIZE_VALUE = 128;
    final String id;
    final int size;
    final int firstFlag;
    final int secondFlag;
    
    Header(int[] data) {
      id = String.format("%c%c%c%c", data[0], data[1], data[2], data[3]);
      size = calculateTheContentLength(data);
      firstFlag = data[8];
      secondFlag = data[9];
    }
    
    static boolean isItAValidSize(final int[] data) {
      return         
        data[4] < MAXIMUM_SIZE_VALUE && 
        data[5] < MAXIMUM_SIZE_VALUE && 
        data[6] < MAXIMUM_SIZE_VALUE && 
        data[7] < MAXIMUM_SIZE_VALUE;
    }
    
    static int calculateTheContentLength(final int[] data) {
      return ((data[4] * MAXIMUM_SIZE_VALUE + data[5]) * MAXIMUM_SIZE_VALUE + data[6]) * MAXIMUM_SIZE_VALUE + data[7];
    }
  }
}
