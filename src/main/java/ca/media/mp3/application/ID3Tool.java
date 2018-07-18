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
    String outputString;
    try {
      outputString = formatter.tagToString(new ID3V2Tag(byteArray));
    } catch(IllegalArgumentException e) {
      outputString = e.getMessage();
    }
    return outputString;
  }

  @Override
  public int[] read(InputStream stream) throws IOException {
    if (stream == null) {
      throw new IllegalArgumentException("The Inputstream is null");
    }

    if(stream.available() > 20) {
      byteArray = new int[20];
    } else {
      byteArray = new int[stream.available()];
    }

    for(int i = 0; i < byteArray.length; i++) {
      byteArray[i] = stream.read();
    }

    return byteArray;
  }
}
