package ca.media.mp3.entity;

import java.net.URL;

public class MP3
{
  private final ID3V2Tag tag;
  private final URL url;
  
  public MP3(URL url, ID3V2Tag tag)
  {
    this.url = url;;
    this.tag = tag;
  }
  
  public ID3V2Tag getTag()
  {
    return this.tag;
  }
  
  public URL getUrl()
  {
    return this.url;
  }
}
