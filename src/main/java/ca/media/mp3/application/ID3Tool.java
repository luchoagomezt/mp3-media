package ca.media.mp3.application;

import java.io.InputStream;
import java.io.IOException;
import ca.media.mp3.entity.ID3V2Tag;

public class ID3Tool implements ID3Reader 
{
  private int[] header;
  private int[] data;
  private final InputStream stream;

  public ID3Tool(InputStream stream) 
  {
    this.stream = stream;
  }

  @Override
  public ID3V2Tag perform() 
  {
    read();
    return new ID3V2Tag(data);
  }

  private void read() 
  {
    readHeader();
    checkForValidHeader();
    readData();
  }

  private void readHeader() 
  {
    header = new int[ID3V2Tag.HEADER_SIZE];
    for(int i = 0; i < header.length; i++) {
      try {
        header[i] = stream.read();
      } catch (IOException e) {
        throw new MP3MediaException(e);
      }
    }
  }

  private void checkForValidHeader()
  {
    if (!ID3V2Tag.isAnID3V2tag(header)) {
      throw new MP3MediaException("The stream does not contain an ID3 V2 tag");
    }
  }

  private void readData()
  {
    int sizeExcludingHeader = ID3V2Tag.calculateTagSize(header);  
    int totalTagSize = sizeExcludingHeader + ID3V2Tag.HEADER_SIZE;
    data = new int[totalTagSize];
    for(int i = 0; i < header.length; i++) {
      data[i] = header[i];
    }

    for(int i = ID3V2Tag.HEADER_SIZE; i < data.length; i++) {
      try {
        data[i] = stream.read();
      } catch (IOException e) {
        throw new MP3MediaException(e);
      }
    }
  }

}
