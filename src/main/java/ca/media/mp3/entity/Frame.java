package ca.media.mp3.entity;

import java.util.ArrayList;
import java.util.List;

public class Frame 
{
  public static final int HEADER_SIZE = 10;
  private static final int NULL = 0;
  private final String content;
  private final Header header;

  public Frame(int[] data)
  {
    checkIfDataIsTooShort(data);
    checkIfSizeDescriptorIsValid(data);

    header = new Header(data);
    content = getContent(data);
  }

  private String getContent(final int[] data) 
  {
    List<Character> characterList = getContentAsACharacterList(data);
    return characterListToString(characterList);
  }

  private String characterListToString(List<Character> characterList) 
  {
    return 
      characterList.
      stream().
      map(c -> c.toString()).
      reduce((s1, s2) -> s1.concat(s2)).
      orElse("");
  }

  private List<Character> getContentAsACharacterList(final int[] data) 
  {
    List<Character> contentList = new ArrayList<>();
    for(int i = HEADER_SIZE; i < data.length; i++) {
      if (data[i] == NULL) {
        continue;
      }
      contentList.add(new Character((char)data[i]));
    }
    return contentList;
  }

  private static void checkIfDataIsTooShort(final int[] data) 
  {
    if(data.length < HEADER_SIZE) {
      throw new IllegalArgumentException(
        "Length of the data array passed as a parameter is less than the valid size for a frame's header");
    }
  }

  private static void checkIfSizeDescriptorIsValid(final int[] data) 
  {
    if(!Header.isSizeDescriptorValid(data)) {
      throw new IllegalArgumentException("One or more of the four size bytes is more or equal to " + Header.MAXIMUM_SIZE_VALUE);
    }
  }

  public static boolean isValid(final int[] data) 
  {
    checkIfDataIsTooShort(data);
    checkIfSizeDescriptorIsValid(data);
    return Header.isValidID(data);
  }

  public static int calculateContentSize(final int[] data) 
  {
    checkIfDataIsTooShort(data);
    checkIfSizeDescriptorIsValid(data); 
    return Header.calculateContentSize(data);
  }

  public String toString() 
  {
    String content = getContent();
    if (getHeader().getId().equalsIgnoreCase("APIC")) {
       content = "image";
    }
    return String.format("{%s, \"content\":\"%s\"}", getHeader().toString(), content);
  }
  
  public String getContent() 
  {
    return content;
  }
  
  public Header getHeader() 
  {
    return header;
  }

  private static class Header 
  {
    public static final int MAXIMUM_SIZE_VALUE = 256;
    private final String id;
    private final int contentSize;
    private final int firstFlag;
    private final int secondFlag;
    
    public Header(int[] data) 
    {
      id = String.format("%c%c%c%c", data[0], data[1], data[2], data[3]);
      contentSize = calculateContentSize(data);
      firstFlag = data[8];
      secondFlag = data[9];
    }
    
    public static boolean isSizeDescriptorValid(final int[] data) 
    {
      return 
        data[4] < MAXIMUM_SIZE_VALUE && 
        data[5] < MAXIMUM_SIZE_VALUE && 
        data[6] < MAXIMUM_SIZE_VALUE && 
        data[7] < MAXIMUM_SIZE_VALUE;
    }
    
    public static boolean isValidID(final int[] data) 
    {
      return 
        data[0] != 0x00 && 
        data[1] != 0x00 && 
        data[2] != 0x00 && 
        data[3] != 0x00;
    }
    
    public static int calculateContentSize(final int[] data) 
    {
      return (int)(
        data[4] * Math.pow(MAXIMUM_SIZE_VALUE, 3) +
        data[5] * Math.pow(MAXIMUM_SIZE_VALUE, 2) +
        data[6] * MAXIMUM_SIZE_VALUE +
        data[7]);
    }
    
    public String getId() 
    {
      return id;
    }
    
    public int getContentSize() 
    {
      return contentSize;
    }
    
    public int getFirstFlag() 
    {
      return firstFlag;
    }
    
    public int getSecondFlag() 
    {
      return secondFlag;
    }
    
    public String toString()
    {
      String format = "\"id\":\"%s\", \"size\":%d, \"flags\":0x%02X%02X";
      return String.format(format, getId(), getContentSize(), getFirstFlag(), getSecondFlag());
    }
  }
}
