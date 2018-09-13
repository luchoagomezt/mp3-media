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

  public Header getHeader()
  {
    return this.header;
  }

  public List<Frame> getFrameList()
  {
    return frameList;
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
}
