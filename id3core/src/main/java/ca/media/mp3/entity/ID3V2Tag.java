package ca.media.mp3.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ID3V2Tag 
{
  public static final int HEADER_SIZE = 10;
  private final List<Frame> frameList;
  private final Header header;

  public ID3V2Tag(final int[] data) 
  {
    checkIfID3TagIsValid(data);

    header = new Header(data);
    frameList = buildFrameList(data);  
  }

  private List<Frame> buildFrameList(final int[] data)
  {
    List<Frame> list = new ArrayList<Frame>();
    int last = header.getSize();

    int from = HEADER_SIZE;
    int[] frameHeader = copyStartingAtAmount(data, from, Frame.HEADER_SIZE);
    int frameSize = Frame.HEADER_SIZE + Frame.calculateContentSize(frameHeader);

    while (Frame.isValid(frameHeader) && (from + frameSize) < last) {
      int[] frameArray = copyStartingAtAmount(data, from, frameSize);
      list.add(new Frame(frameArray));
      
      from = from + frameSize;
      frameHeader = copyStartingAtAmount(data, from, Frame.HEADER_SIZE);
      frameSize = Frame.HEADER_SIZE + Frame.calculateContentSize(frameHeader);
    }
    return list;
  }

  private int[] copyStartingAtAmount(int[] original, int from, int amount) 
  {
    return Arrays.copyOfRange(original, from, from + amount);
  }

  public static int calculateTagSize(final int[] data) 
  {
    checkIfDataIsTooShort(data);
    checkIfSizeDescriptorsAreValid(data); 

    return Header.calculateSize(data);
  }

  public static boolean isAnID3V2tag(final int[] data) 
  {
    checkIfDataIsTooShort(data);

    return Header.isATagPattern(data);
  }

  private void checkIfID3TagIsValid(final int[] data) 
  {
    if(!isAnID3V2tag(data)) {
      throw new IllegalArgumentException("Array does not contain an ID3 V2 tag");
    }
  }

  private static void checkIfSizeDescriptorsAreValid(final int[] data) 
  {
    if(!Header.areSizeDescriptorsValid(data)) 
    {
      throw new IllegalArgumentException("One or more of the four size bytes is more than 0x7F");
    }
  }

  private static void checkIfDataIsTooShort(final int[] data) 
  {
    if (data.length < HEADER_SIZE) {
      throw new IllegalArgumentException("Array's length is less than header's size");
    }
  }

  public int majorVersion()
  {
    return header.getMajorVersion();
  }
  
  public int revisionNumber()
  {
    return header.getRevisionNumber();
  }

  public boolean unsynchronisation()
  {
    return header.isUnsynchronized();
  }

  public boolean extendedHeader()
  {
    return header.hasAnExtendedHeader();
  }

  public boolean experimental()
  {
    return header.isExperimental();
  }

  public int size()
  {
    return header.getSize();
  }

  @Override
  public String toString()
  {
    return 
      String.format("%s, \"frames\":[%s]}", 
      header.toString(), 
      frameList.
      stream().
      map(e -> e.toString()).
      reduce((s1, s2) -> s1.concat(", ").concat(s2)).
      orElse(""));
  }

  private static class Header 
  {
    private static final int CHAR_I = 0x49;
    private static final int CHAR_D = 0x44;
    private static final int CHAR_3 = 0x33;
    private static final int MAXIMUM_REVISION_NUMBER = 0xFF;
    private static final int MAXIMUM_VERSION_NUMBER = 0xFF;
    private static final int EXPERIMENTAL_BIT = 0x20;
    private static final int EXTENDED_BIT = 0x40;
    private static final int UNSYNCHRONISATION_BIT = 0x80;
    private static final int FLAG_MASK = 0xE0;
    private static final int MAXIMUM_SIZE_VALUE = 0x80;
    private final int majorVersion;
    private final int revisionNumber;
    private final int flag;
    private final int size;

    public Header(final int[] data) 
    {
      majorVersion = data[3];
      revisionNumber = data[4];
      flag = data[5];
      size = calculateSize(data);
    }

    private static boolean isATagPattern(final int[] data)
    {
      return 
        data[0] == CHAR_I &&
        data[1] == CHAR_D &&
        data[2] == CHAR_3 &&
        data[3] < MAXIMUM_VERSION_NUMBER &&
        data[4] < MAXIMUM_REVISION_NUMBER &&
        data[5] == (data[5] & FLAG_MASK) &&
        data[6] < MAXIMUM_SIZE_VALUE &&
        data[7] < MAXIMUM_SIZE_VALUE &&
        data[8] < MAXIMUM_SIZE_VALUE &&
        data[9] < MAXIMUM_SIZE_VALUE;
    }

    public static int calculateSize(final int[] data)
    {
      return (int)(
        data[6] * Math.pow(MAXIMUM_SIZE_VALUE, 3) + 
        data[7] * Math.pow(MAXIMUM_SIZE_VALUE, 2) +
        data[8] * MAXIMUM_SIZE_VALUE +
        data[9]);
    }

    public static boolean areSizeDescriptorsValid(int[] data)
    {
      return 
        data[6] < MAXIMUM_SIZE_VALUE && 
        data[7] < MAXIMUM_SIZE_VALUE && 
        data[8] < MAXIMUM_SIZE_VALUE &&
        data[9] < MAXIMUM_SIZE_VALUE;
    }

    public int getMajorVersion()
    {
      return majorVersion;
    }

    public int getRevisionNumber()
    {
      return revisionNumber;
    }

    public boolean isUnsynchronized()
    {
      return (flag & UNSYNCHRONISATION_BIT) == UNSYNCHRONISATION_BIT;
    }
    
    public boolean hasAnExtendedHeader()
    {
      return (flag & EXTENDED_BIT) == EXTENDED_BIT;
    }

    public boolean isExperimental()
    {
      return (flag & EXPERIMENTAL_BIT) == EXPERIMENTAL_BIT;
    }

    public int getSize()
    {
      return size;
    }

    public String toString()
    {
      return 
        String.format(
          "{\"version\":%d, \"revision\":%d, \"flags\":%d, \"size\":%d",
          majorVersion, revisionNumber, flag, size);
    }
  }
}
