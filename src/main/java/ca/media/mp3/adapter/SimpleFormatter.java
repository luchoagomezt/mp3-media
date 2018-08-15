package ca.media.mp3.adapter;

import ca.media.mp3.entity.ID3TagFormatter;
import ca.media.mp3.entity.ID3V2Tag;

public class SimpleFormatter implements ID3TagFormatter {

  @Override
  public String format(ID3V2Tag tag) {
    return tag.toString().replace("[{", "[\n{").replace(", {", ",\n{");
  }

}
