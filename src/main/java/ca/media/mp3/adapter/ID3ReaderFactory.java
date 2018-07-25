package ca.media.mp3.adapter;

import ca.media.mp3.application.ID3Reader;
import ca.media.mp3.application.ID3Tool;

public class ID3ReaderFactory {
  public static final String SIMPLE_PRESENTER = "SIMPLE_PRESENTER";

  public static ID3Reader makeAnID3Reader(String presenterName) {
    if (presenterName.equals(SIMPLE_PRESENTER)) {
      return new ID3Tool(new SimplePresenter());
    }
    return null;
  }

}
