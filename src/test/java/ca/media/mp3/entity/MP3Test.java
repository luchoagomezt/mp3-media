package ca.media.mp3.entity;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;

@Test
public class MP3Test {
  @Test
  public void mp3ArrayIsEmpty() {
    MP3 mp3 = new MP3(new int[]{});
    assertTrue(mp3.size() == 0);
  }
  
  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void mp3ArrayIsNull() {
    new MP3(null);
  }
}
