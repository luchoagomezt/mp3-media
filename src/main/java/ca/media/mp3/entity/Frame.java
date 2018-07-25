package ca.media.mp3.entity;

import java.util.ArrayList;
import java.util.List;

public class Frame {
  public static final int FRAME_HEADER_SIZE = 10;
  private final int[] id;
  private final int size;
  private final int[] flags;
  private final String content;
  
  public Frame(int[] intArray) throws IllegalArgumentException {
    if(!isAFrameTag(intArray)) {
      throw new IllegalArgumentException("Array is not a valid frame");
    }
    
    id = new int[]{intArray[0], intArray[1], intArray[2], intArray[3]};
    size = calculateFrameSizeExcludingHeader(intArray);
    flags = new int[]{intArray[8], intArray[9]};
    content = getFrameContent(intArray);
  }

  private String getFrameContent(final int[] intArray) {
    List<Character> content = new ArrayList<>();
    if (size == 0) {
      return "";
    }
    int encoding = intArray[FRAME_HEADER_SIZE];
    int offset = 1;
    if (encoding == 0xFF || encoding == 0xFE) {
      offset = 2;
    }
    for(int i = FRAME_HEADER_SIZE + offset; i < intArray.length; i++) {
      content.add(new Character((char)intArray[i]));
    }
    return content.stream().map(c -> c.toString()).reduce((s1, s2) -> s1.concat(s2)).orElse("");
  }

  public static boolean isAFrameTag(final int[] array) throws IllegalArgumentException {
    if(array == null) {
      throw new IllegalArgumentException("Array is null");
    }
    if(array.length < FRAME_HEADER_SIZE) {
      throw new IllegalArgumentException("Array is too small");
    }

    return 
        array[4] < 0x80 && 
        array[5] < 0x80 && 
        array[6] < 0x80 && 
        array[7] < 0x80;
  }

  public static int calculateFrameSizeExcludingHeader(final int[] header) {
    if (header == null) {
      throw new IllegalArgumentException("Parameter is null");
    }

    if (header.length < FRAME_HEADER_SIZE) {
      throw new IllegalArgumentException("Array's length is less than header's size");
    }
    
    if(header[4] > 0x7F || header[5] > 0x7F || header[6] > 0x7F || header[7] > 0x7F) {
      throw new IllegalArgumentException("One or more of the four size bytes is more than 0x7F");
    } 

    return ((header[4] * 0x80 + header[5]) * 0x80 + header[6]) * 0x80 + header[7];
  }

  public String toString() {
    return String.format("{\"id\":\"%c%c%c%c\", \"size\":%d, \"flags\":{\"first\":%d, \"second\":%d}, \"content\":\"%s\"}", 
        id[0], id[1], id[2], id[3], size, flags[0], flags[1], content);
  }
}
