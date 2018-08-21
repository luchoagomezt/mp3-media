package ca.media.mp3.entity;

@FunctionalInterface
public interface ID3Reader {

  ID3V2Tag perform();
}
