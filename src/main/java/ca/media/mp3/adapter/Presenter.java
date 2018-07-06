package ca.media.mp3.adapter;

import ca.media.mp3.application.ID3Formatter;
import ca.media.mp3.entity.ID3V2Tag;

public class Presenter implements ID3Formatter {

  @Override
  public String toString(ID3V2Tag t) {
    return String.format(
        "{\"version\":%s, \"revision\":%s, \"flags\":%d, \"size\":%d}%n", 
        t.majorVersion(), t.revisionNumber(), t.flags(), t.size());
  }
}
