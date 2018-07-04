package ca.media.mp3.application;

import ca.media.mp3.entity.ID3V2Tag;

@FunctionalInterface
public interface ID3Formatter {

  String toString(ID3V2Tag id3v2tag);

}
