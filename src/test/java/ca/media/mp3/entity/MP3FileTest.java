package ca.media.mp3.entity;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;

@Test
public class MP3FileTest {
  @Test
  public void mp3ArrayIsEmpty() {
    MP3File file = new MP3File(new byte[]{});
    assertTrue(file.getID3V2Tags().isEmpty());
  }
  
  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void mp3ArrayIsNull() {
    new MP3File(null);
  }
}
