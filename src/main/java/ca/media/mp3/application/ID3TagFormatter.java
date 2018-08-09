package ca.media.mp3.application;

@FunctionalInterface
public interface ID3TagFormatter {
  
  String format(ID3Reader reader);

}
