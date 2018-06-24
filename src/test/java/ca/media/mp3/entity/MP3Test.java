package ca.media.mp3.entity;

import java.util.ArrayList;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;

@Test
public class MP3Test {
  @Test
  public void mp3StreamIsEmpty() {
    MP3 file = new MP3(new ArrayList<Integer>().stream());
    assertTrue(file.getID3V2Tags().isEmpty());
  }
  
  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void mp3StreamIsNull() {
    new MP3(null);
  }
}
