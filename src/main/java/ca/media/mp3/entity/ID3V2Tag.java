package ca.media.mp3.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ID3V2Tag 
{
  public static final int HEADER_SIZE = 10;
  private static final int MAXIMUM_REVISION_NUMBER = 255;
  private static final int MAXIMUM_VERSION_NUMBER = 255;
  private static final int MAXIMUM_SIZE_VALUE = 128;
  private final List<Frame> frameList;
  private final Header header;

  public ID3V2Tag(final int[] data) 
  {
    checkIfID3TagIsValid(data);

    header = new Header(data);
    frameList = buildFrames(data);  
  }

  private void checkIfID3TagIsValid(final int[] data) 
  {
    if(!isAnID3V2tag(data)) {
      throw new IllegalArgumentException("Array does not contain an ID3 V2 tag");
    }
  }

  private List<Frame> buildFrames(final int[] data) {
    List<Frame> list = new ArrayList<Frame>();
    if(data.length < Frame.HEADER_SIZE + HEADER_SIZE) {
      return list;
    }
    
    int[] frameHeader = Arrays.copyOfRange(data, HEADER_SIZE, Frame.HEADER_SIZE + HEADER_SIZE);
    int frameSize = Frame.calculateContentSize(frameHeader);
    int[] frameArray = Arrays.copyOfRange(data, HEADER_SIZE, Frame.HEADER_SIZE + HEADER_SIZE + frameSize);
    list.add(new Frame(frameArray));
    return list;
  }

  public static int calculateTagSizeExcludingHeader(final int[] header) 
  {
    checkIfHeaderIsNull(header);
    checkIfHeaderIsTooShort(header);
    checkIfSizeDescriptorIsValid(header); 

    return (int)(
      header[6] * Math.pow(MAXIMUM_SIZE_VALUE, 3) + 
      header[7] * Math.pow(MAXIMUM_SIZE_VALUE, 2) +
      header[8] * MAXIMUM_SIZE_VALUE +
      header[9]);
  }

  private static void checkIfSizeDescriptorIsValid(final int[] header) 
  {
    if(header[6] > 0x7F || header[7] > 0x7F || header[8] > 0x7F || header[9] > 0x7F) {
      throw new IllegalArgumentException("One or more of the four size bytes is more than 0x7F");
    }
  }

  private static void checkIfHeaderIsTooShort(final int[] header) 
  {
    if (header.length < HEADER_SIZE) {
      throw new IllegalArgumentException("Array's length is less than header's size");
    }
  }

  private static void checkIfHeaderIsNull(final int[] header) 
  {
    if (header == null) {
      throw new IllegalArgumentException("Parameter is null");
    }
  }

  public static boolean isAnID3V2tag(final int[] data) 
  {
    if(data == null) {
      throw new IllegalArgumentException("Parameter array is null");
    }

    checkIfHeaderIsTooShort(data);

    return 
        data[0] == 0x49 &&
        data[1] == 0x44 &&
        data[2] == 0x33 &&
        data[3] < MAXIMUM_VERSION_NUMBER &&
        data[4] < MAXIMUM_REVISION_NUMBER &&
        data[5] == (data[5] & 0xE0) &&
        data[6] < MAXIMUM_SIZE_VALUE &&
        data[7] < MAXIMUM_SIZE_VALUE &&
        data[8] < MAXIMUM_SIZE_VALUE &&
        data[9] < MAXIMUM_SIZE_VALUE;
  }
    
  public int majorVersion() {
    return getHeader().getMajorVersion();
  }
  
  public int revisionNumber() {
    return getHeader().getRevisionNumber();
  }
  
  public boolean unsynchronisation() {
    return (getHeader().getFlag() & 0x80) == 0x80;
  }
  
  public boolean extendedHeader() {
    return (getHeader().getFlag() & 0x40) == 0x40;
  }
  
  public boolean experimental() {
    return (getHeader().getFlag() & 0x20) == 0x20;
  }
  
  public int flags() {
    return getHeader().getFlag();
  }
  
  public int size() {
    return getHeader().getSize();
  }
  
  @Override
  public String toString() {
    return 
      String.format("%s, \"frames\":[%s]}", 
      getHeader().toString(), 
      getFrameList().
      stream().
      map(e -> e.toString()).
      reduce((s1, s2) -> s1.concat(", ").
      concat(s2)).
      orElse(""));
  }
  
  private Header getHeader() {
    return header;
  }

  private List<Frame> getFrameList() {
    return frameList;
  }

  private class Header 
  {
    private final int majorVersion;
    private final int revisionNumber;
    private final int flag;
    private final int size;
    
    public Header(final int[] data) {
      majorVersion = data[3];
      revisionNumber = data[4];
      flag = data[5];
      size = calculateTagSizeExcludingHeader(data);      
    }

    public int getMajorVersion() {
      return majorVersion;
    }

    public int getRevisionNumber() {
      return revisionNumber;
    }

    public int getFlag() {
      return flag;
    }

    public int getSize() {
      return size;
    }
    
    public String toString() {
      return 
        String.format("{\"header\":{\"version\":%d, \"revision\":%d, \"flags\":%d, \"size\":%d}", 
        majorVersion(), revisionNumber(), flags(), size());
    }
  }
}
