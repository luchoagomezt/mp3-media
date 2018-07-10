package ca.media.mp3.application;

import java.io.InputStream;
import java.io.IOException;
import ca.media.mp3.entity.ID3V2Tag;

public class ID3Tool implements ID3Reader {
  private final InputStream stream;
  private final ID3TagFormatter formater;

  public ID3Tool(InputStream stream, ID3TagFormatter formater) throws IllegalArgumentException {
    if (stream == null) {
      throw new IllegalArgumentException("Stream is null");
    }
    if (formater == null) {
      throw new IllegalArgumentException("Formater is null");
    }
    this.stream = stream;
    this.formater = formater;
  }
  
  @Override
  public String perform() {
    String s;
    try {
      s = formater.tagToString(new ID3V2Tag(read(stream)));
    } catch(IllegalArgumentException e) {
      s = e.getMessage();
    } catch(IOException e) {
      s = e.getMessage();
    }
    return s;
  }

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
