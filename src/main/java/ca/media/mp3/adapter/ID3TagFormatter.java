package ca.media.mp3.adapter;

import ca.media.mp3.entity.ID3V2Tag;

@FunctionalInterface
public interface ID3TagFormatter {
  
  String format(ID3V2Tag tag);

}
