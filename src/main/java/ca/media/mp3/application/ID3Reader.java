package ca.media.mp3.application;

import ca.media.mp3.entity.ID3V2Tag;

@FunctionalInterface
public interface ID3Reader {

  ID3V2Tag perform();
}
