package ca.media.mp3.entity;

import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.assertEquals;

public class MP3Test
{
  final static ID3V2Tag TAG = new ID3V2Tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 00, 0x02, 0x00});

  @Test
  public void constructAMP3AndGetTheArguments() 
  {
    try {
      URL url = new URL("file", "localhost", "/home/luis");
      MP3 mp3 = new MP3(url, TAG);
      assertEquals(mp3.getTag(), TAG);
      assertEquals(mp3.getURL(), url);
    } catch(MalformedURLException e) {  
    }
  }
  
  @Test
  public void buildAMP3UsingAValidPath() throws MalformedURLException
  {
    new MP3("/fdf/example.txt", TAG);
  }

}
