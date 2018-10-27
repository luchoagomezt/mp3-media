package ca.media.mp3.web.service;

import java.net.URL;

import ca.media.mp3.entity.ID3V2Tag;

public interface ID3WebReader 
{
  ID3V2Tag perform();
  void setUrl(URL url);
  URL getURL();
}
