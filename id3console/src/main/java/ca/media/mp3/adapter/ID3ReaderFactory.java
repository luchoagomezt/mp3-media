package ca.media.mp3.adapter;

import ca.media.mp3.entity.ID3Reader;
import ca.media.mp3.usercase.ID3Tool;

public class ID3ReaderFactory {
  private ID3ReaderFactory() {
  }

  public static ID3Reader makeAnID3Reader(String filePath) {
    return new ID3Tool(filePath);
  }

}
