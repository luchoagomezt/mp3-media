package ca.media.mp3.entity;

public class Frame {
  public static final int FRAME_HEADER_SIZE = 10;
  private final int[] id;
  private final int size;
  private final int[] flags;
  
  public Frame(int[] intArray) throws IllegalArgumentException {
    if(!isAFrameTag(intArray)) {
      throw new IllegalArgumentException("Array is not a valid frame");
    }
    
    id = new int[]{intArray[0], intArray[1], intArray[2], intArray[3]};
    size = ((intArray[4] * 0x80 + intArray[5]) * 0x80 + intArray[6]) * 0x80 + intArray[7];
    flags = new int[]{intArray[8], intArray[9]};
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
  
  public String toString() {
    return String.format("{\"id\":\"%c%c%c%c\", \"size\":%d, \"flags\":%d}", 
        id[0], id[1], id[2], id[3], size, flags[0], flags[1]);
  }
}
