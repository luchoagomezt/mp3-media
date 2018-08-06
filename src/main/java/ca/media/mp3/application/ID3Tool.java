package ca.media.mp3.application;

import java.io.InputStream;
import java.io.IOException;
import ca.media.mp3.entity.ID3V2Tag;

public class ID3Tool implements ID3Reader 
{
  private int[] header;
  private int[] data;
  private InputStream stream;
  private final ID3TagFormatter formatter;

  public ID3Tool(ID3TagFormatter formatter) 
  {
    this.formatter = formatter;
  }

  @Override
  public String perform() 
  {
    return formatter.tagToString(new ID3V2Tag(data));
  }

  @Override
  public void read(InputStream stream) throws IOException 
  {
    this.stream = stream;
    
    readHeader();
    checkForValidHeader();
    readData();
  }

  private void readHeader() throws IOException 
  {
    header = new int[ID3V2Tag.HEADER_SIZE];
    for(int i = 0; i < header.length; i++) {
      header[i] = stream.read();
    }
  }

  private void checkForValidHeader()
  {
    if (!ID3V2Tag.isAnID3V2tag(header)) {
      throw new IllegalArgumentException("The stream does not contain an ID3 V2 tag");
    }
  }

  private void readData() throws IOException
  {
    int sizeExcludingHeader = ID3V2Tag.calculateTagSize(header);  
    int totalTagSize = sizeExcludingHeader + ID3V2Tag.HEADER_SIZE;
    data = new int[totalTagSize];
    for(int i = 0; i < header.length; i++) {
      data[i] = header[i];
    }

    for(int i = ID3V2Tag.HEADER_SIZE; i < data.length; i++) {
      data[i] = stream.read();
    }
  }

}
