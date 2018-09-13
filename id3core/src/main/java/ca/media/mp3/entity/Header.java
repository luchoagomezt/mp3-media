package ca.media.mp3.entity;

public class Header
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

  public static boolean isATagPattern(final int[] data)
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
