package ca.media.mp3.application;

import java.io.InputStream;
import java.io.IOException;
import ca.media.mp3.entity.ID3V2Tag;

public class ID3Tool implements ID3Reader {
  private final ID3Formatter formatter;

  public ID3Tool(ID3Formatter formatter) throws IllegalArgumentException {
    if (formatter == null) {
      throw new IllegalArgumentException("Writer is null");
    }
    this.formatter = formatter;
  }
  
  public String perform(int[] array) {
    String s;
    try {
      s = formatter.toString(new ID3V2Tag(array));
    } catch(IllegalArgumentException e) {
      s = e.getMessage();
    }
    return s;
  }

  @Override
  public int[] read(InputStream stream) throws IOException {
    if (stream == null) {
      return null;
    }
    
    int[] header;
    if(stream.available() > 10) {
      header = new int[10];
    } else {
      header = new int[stream.available()];
    }

    for(int i = 0; i < header.length; i++) {
      header[i] = stream.read();
    }

    return header;
  }
}
