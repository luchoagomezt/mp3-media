package ca.media.mp3.application;

import ca.media.mp3.entity.ID3V2Tag;

public interface ID3Writer {

  void print(ID3V2Tag id3v2tag);

}
