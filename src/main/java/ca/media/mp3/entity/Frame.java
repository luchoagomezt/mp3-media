package ca.media.mp3.entity;

import java.util.ArrayList;
import java.util.List;

public class Frame 
{
  public static final int HEADER_SIZE = 10;  
  private final String content;
  private final Header header;

  public Frame(
    int[] data) 
  {
    checkIfDataIsNull(data);
    checkIfDataIsTooShort(data);
    checkIfSizeDescriptorIsValid(data); 
    
    header = new Header(data);
    if (header.contentSize > 0) 
    {
      content = getContent(data);
    } 
    else 
    {
      content = "";
    }
  }

  private String getContent(
    final int[] data) 
  {
    List<Character> characterList = getContentAsACharacterList(data);
    return characterListToString(characterList);
  }

  private String characterListToString(
    List<Character> characterList) 
  {
    return 
      characterList.
      stream().
      map(c -> c.toString()).
      reduce((s1, s2) -> s1.concat(s2)).
      orElse("");
  }

  private List<Character> getContentAsACharacterList(
    final int[] data) 
  {
    List<Character> contentList = new ArrayList<>();
    for(int i = HEADER_SIZE + getOffset(data); i < data.length; i++) 
    {
      contentList.add(new Character((char)data[i]));
    }
    return contentList;
  }

  private int getOffset(
    final int[] data) 
  {
    if (data[HEADER_SIZE] == 0x00) 
    {
      return 1;
    }
    
    if (data[HEADER_SIZE] == 0xFF || data[HEADER_SIZE] == 0xFE) 
    {
      return 2;
    }
    
    throw new IllegalArgumentException("Encoding byte is invalid");
  }

  public static boolean isValid(
    final int[] data) 
  {
    checkIfDataIsNull(data);
    checkIfDataIsTooShort(data);
    return Header.isSizeDescriptorValid(data);
  }

  private static void checkIfDataIsTooShort(
    final int[] data) 
  {
    if(data.length < HEADER_SIZE) 
    {
      throw new IllegalArgumentException(
        "Length of the data array passed as a parameter is less than the valid size for a frame's header");
    }
  }

  private static void checkIfDataIsNull(
    final int[] data) 
  {
    if(data == null) 
    {
      throw new IllegalArgumentException("Data array passed as a parameter is null");
    }
  }

  public static int calculateContentSize(
    final int[] data) 
  {
    checkIfDataIsNull(data);
    checkIfDataIsTooShort(data);
    checkIfSizeDescriptorIsValid(data); 
    return Header.calculateContentSize(data);
  }

  private static void checkIfSizeDescriptorIsValid(
    final int[] data) 
  {
    if(!Header.isSizeDescriptorValid(data)) 
    {
      throw new IllegalArgumentException("One or more of the four size bytes is more or equal to " + Header.MAXIMUM_SIZE_VALUE);
    }
  }

  public String toString() 
  {
    return String.format("{%s, \"content\":\"%s\"}", header.toString(), content);
  }

  private static class Header 
  {
    static final int MAXIMUM_SIZE_VALUE = 128;
    final String id;
    final int contentSize;
    final int firstFlag;
    final int secondFlag;
    
    Header(
      int[] data) 
    {
      id = String.format("%c%c%c%c", data[0], data[1], data[2], data[3]);
      contentSize = calculateContentSize(data);
      firstFlag = data[8];
      secondFlag = data[9];
    }
    
    static boolean isSizeDescriptorValid(
      final int[] data) 
    {
      return 
        data[4] < MAXIMUM_SIZE_VALUE && 
        data[5] < MAXIMUM_SIZE_VALUE && 
        data[6] < MAXIMUM_SIZE_VALUE && 
        data[7] < MAXIMUM_SIZE_VALUE;
    }
    
    static int calculateContentSize(
      final int[] data) 
    {
      return (int)(
        data[4] * Math.pow(MAXIMUM_SIZE_VALUE, 3) +
        data[5] * Math.pow(MAXIMUM_SIZE_VALUE, 2) +
        data[6] * MAXIMUM_SIZE_VALUE +
        data[7]);
    }
    
    public String toString() {
      String format = "\"id\":\"%s\", \"size\":%d, \"flags\":{\"first\":%d, \"second\":%d}";
      return String.format(format, id, contentSize, firstFlag, secondFlag);
    }
  }
}
