package ca.media.mp3.application;

import ca.media.mp3.entity.MP3;

public class ID3Tool {
  private final ID3Writer out;
  private final MP3Reader in;

  public ID3Tool(MP3Reader reader, ID3Writer writer) {
    in = reader;
    out = writer;
  }
  
  public boolean perform() {
    MP3 mp3 = in.read();
    if (mp3.hasID3V2tag()) {
      out.print(mp3.getID3V2tag());
      return true;
    }
    return false;
  }
}
