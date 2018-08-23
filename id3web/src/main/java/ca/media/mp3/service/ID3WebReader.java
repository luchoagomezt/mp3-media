package ca.media.mp3.service;

import ca.media.mp3.entity.ID3V2Tag;

public interface ID3WebReader 
{
  ID3V2Tag perform();
  void setUrl(String url);
}
