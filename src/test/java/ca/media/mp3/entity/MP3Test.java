package ca.media.mp3.entity;

import org.testng.annotations.Test;

@Test
public class MP3Test {
  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void mp3ArrayIsEmpty() {
    new MP3(new int[]{});
  }
  
  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void mp3ArrayIsNull() {
    new MP3(null);
  }
}
