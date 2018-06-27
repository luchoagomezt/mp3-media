package ca.media.mp3.entity;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertNotNull;

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
    assertEquals(new MP3(new int[]{49, 44, 33, 03, 00, 00, 00, 00, 0x02, 0x00}).size(), 10);
  }

  @Test
  public void mp3WithID3V2Tag() {
    assertTrue(new MP3(new int[]{49, 44, 33, 03, 00, 00, 00, 00, 0x02, 0x00}).hasID3V2tag());
  }

  @Test
  public void mp3WithNoID3V2Tag() {
    assertFalse(new MP3(new int[]{50, 60, 30, 05, 00, 00, 00, 00, 02, 00}).hasID3V2tag());
  }

  @Test
  public void theID3V2TagIsNull() {
    assertNull(new MP3(new int[]{50, 60, 30, 05, 00, 00, 00, 00, 02, 00}).getID3V2tag());
  }

  @Test
  public void theID3V2TagIsNotNull() {
    assertNotNull(new MP3(new int[]{49, 44, 33, 03, 00, 00, 00, 00, 0x02, 0x00}).getID3V2tag());
  }
}
