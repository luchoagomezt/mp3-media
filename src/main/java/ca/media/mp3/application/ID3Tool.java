package ca.media.mp3.application;

import java.io.InputStream;
import java.io.IOException;
import ca.media.mp3.entity.ID3V2Tag;

public class ID3Tool implements ID3Reader {
  private int[] byteArray;
  private final ID3TagFormatter formatter;

  public ID3Tool(ID3TagFormatter formatter) {
    if (formatter == null) {
      throw new IllegalArgumentException("The ID3 Formatter is null");
    }
    this.formatter = formatter;
  }
  
  @Override
  public String perform() {
    return formatter.tagToString(new ID3V2Tag(byteArray));
  }

  @Override
  public int[] read(InputStream stream) throws IOException {
    if (stream == null) {
      throw new IllegalArgumentException("The InputStream parameter is null ");
    }
    
    if(stream.available() < ID3V2Tag.HEADER_SIZE) {
      throw new IllegalArgumentException("The stream'size is less than the tag's header size");
    }
    
    int[] header = new int[ID3V2Tag.HEADER_SIZE];
    for(int i = 0; i < header.length; i++) {
      header[i] = stream.read();
    }
    
    if (!ID3V2Tag.isAnID3V2tag(header)) {
      throw new IllegalArgumentException("The stream does not contain an ID3 V2 tag");
    }
    
    int sizeExcludingHeader = ID3V2Tag.calculateTagSize(header);  
    int totalTagSize = sizeExcludingHeader + ID3V2Tag.HEADER_SIZE;
    byteArray = new int[totalTagSize];
    for(int i = 0; i < header.length; i++) {
      byteArray[i] = header[i];
    }

    for(int i = ID3V2Tag.HEADER_SIZE; i < byteArray.length; i++) {
      byteArray[i] = stream.read();
    }

    return byteArray;
  }
}
