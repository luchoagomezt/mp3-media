package ca.media.mp3.adapter;

import ca.media.mp3.application.ID3TagFormater;
import ca.media.mp3.entity.ID3V2Tag;

public class Presenter implements ID3TagFormater {

  public  void view(String output) {
    System.out.printf("{%s}%n", output);
  }

  @Override
  public String tagToString(ID3V2Tag tag) {
    return tag.
        header().
        entrySet().
        stream().
        map(e -> "\"" + e.getKey() + "\":" + e.getValue()).
        reduce((s1, s2) -> s1 + ", " + s2).
        orElse("");
  }
}
