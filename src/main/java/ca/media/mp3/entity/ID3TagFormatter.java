package ca.media.mp3.entity;

@FunctionalInterface
public interface ID3TagFormatter {
  
  String format(ID3V2Tag tag);

}
