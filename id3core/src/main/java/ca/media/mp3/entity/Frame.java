package ca.media.mp3.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Frame 
{
  public static final int HEADER_SIZE = 10;
  private static final int NULL = 0;
  private final int[] content;
  private final Header header;
  private final int[] data;

  public Frame(int[] data)
  {
    checkIfDataIsTooShort(data);
    checkIfSizeDescriptorIsValid(data);

    this.data = data;
    header = new Header(data);
    content = setContent(data);
  }

  public int[] getData()
  {
    return data;
  }
  
  public Header getHeader()
  {
    return header;
  }

  private int[] setContent(final int[] data) 
  {
    return Arrays.copyOfRange(data, HEADER_SIZE, data.length);
  }

  private String contentToString() 
  {
    if (header.getId().equalsIgnoreCase("APIC")) {
      return "image";
    }
    List<Character> characterList = contentAsACharacterList();
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

  private List<Character> contentAsACharacterList() 
  {
    List<Character> contentList = new ArrayList<>();
    for(int i = 0; i < content.length; i++) {
      if (content[i] == NULL) {
        continue;
      }
      contentList.add(new Character((char)content[i]));
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
    return String.format("{%s, \"content\":\"%s\"}", header.toString(), contentToString());
  }
  
  public int[] getContent() 
  {
    return content;
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
      String format = "\"id\":\"%s\", \"size\":%d, \"flags\":%d";
      return String.format(format, getId(), getContentSize(), 256 * getFirstFlag() + getSecondFlag());
    }
  }
}
