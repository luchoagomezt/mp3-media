package ca.media.mp3.adapter;

import ca.media.mp3.application.ID3TagFormatter;
import ca.media.mp3.entity.ID3V2Tag;

public class SimplePresenter implements ID3TagFormatter {

  @Override
  public String tagToString(ID3V2Tag tag) {
    if(tag != null) {
      return tag.toString();
    }
    return null;
  }

}
