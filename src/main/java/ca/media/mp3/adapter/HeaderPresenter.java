package ca.media.mp3.adapter;

import ca.media.mp3.application.ID3TagFormatter;
import ca.media.mp3.entity.ID3V2Tag;

public class HeaderPresenter implements ID3TagFormatter {

  @Override
  public String tagToString(ID3V2Tag tag) {
    if(tag != null) {
      return 
        "{".
        concat(
          tag.
          header().
          entrySet().
          stream().
          map(e -> "\"" + e.getKey() + "\":" + e.getValue()).
          reduce((s1, s2) -> s1 + ", " + s2).
          orElse("")).
        concat("}");
    }
    return null;
  }
}
