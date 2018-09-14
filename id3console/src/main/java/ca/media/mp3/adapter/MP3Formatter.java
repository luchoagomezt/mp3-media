package ca.media.mp3.adapter;

import ca.media.mp3.entity.MP3;

@FunctionalInterface
public interface MP3Formatter {
  
  String format(MP3 mp3);

}
