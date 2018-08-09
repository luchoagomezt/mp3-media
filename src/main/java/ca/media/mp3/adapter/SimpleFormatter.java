package ca.media.mp3.adapter;

import ca.media.mp3.application.ID3Reader;
import ca.media.mp3.application.ID3TagFormatter;

public class SimpleFormatter implements ID3TagFormatter {

  @Override
  public String format(ID3Reader reader) {
    return reader.perform().toString();
  }

}
