package ca.media.mp3.service;

import ca.media.mp3.entity.ID3Reader;

public interface ID3WebReader extends ID3Reader 
{
  void setUrl(String url);
}
