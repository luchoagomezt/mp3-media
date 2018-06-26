package ca.media.mp3.entity;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;

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
  
  @Test
  public void mp3Size() {
    assertTrue(new MP3(new int[]{49, 44, 33, 03, 00, 00, 00, 00, 0x02, 0x00}).size() == 10);
  }
}
