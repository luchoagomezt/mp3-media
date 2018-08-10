package ca.media.mp3.application;

import java.io.InputStream;
import java.io.IOException;
import ca.media.mp3.entity.ID3V2Tag;

public class ID3Tool implements ID3Reader 
{
  private final InputStream stream;

  public ID3Tool(InputStream stream) 
  {
    this.stream = stream;
  }

  @Override
  public ID3V2Tag perform() 
  {
    int[] header = new int[ID3V2Tag.HEADER_SIZE];
    readBuffer(header, 0);
    checkForValidHeader(header);

    int[] data = new int[ID3V2Tag.HEADER_SIZE + ID3V2Tag.calculateTagSize(header)];
    arrayCopy(header, data);
    readBuffer(data, ID3V2Tag.HEADER_SIZE);
 
    return new ID3V2Tag(data);
  }

  private void checkForValidHeader(int[] header)
  {
    if (!ID3V2Tag.isAnID3V2tag(header)) {
      throw new MP3MediaException("The stream does not contain an ID3 V2 tag");
    }
  }

  private void readBuffer(int[] buffer, int from)
  {
    try {
      for(int i = from; i < buffer.length; i++) {
        buffer[i] = stream.read();
      }
    } catch (IOException e) {
      throw new MP3MediaException(e);
    }
  }

  private void arrayCopy(int[] src, int[] dest) 
  {
    System.arraycopy(src, 0, dest, 0, src.length);
  }

}
