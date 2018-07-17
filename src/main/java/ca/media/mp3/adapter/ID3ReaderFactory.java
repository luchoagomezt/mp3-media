package ca.media.mp3.adapter;

import ca.media.mp3.application.ID3Reader;
import ca.media.mp3.application.ID3Tool;

public class ID3ReaderFactory {
  public static final String HEADER_ONLY = "HEADER_ONLY";
  public static final String JSON_STRING = "JSON_STRING";

  public static ID3Reader getReader(String type) {
    if(type.equals(HEADER_ONLY)) {
      return new ID3Tool(new HeaderPresenter());
    } else if (type.equals(JSON_STRING)) {
      return new ID3Tool(new SimplePresenter());
    }
    return null;
  }

}
