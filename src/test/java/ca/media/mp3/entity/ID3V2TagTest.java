package ca.media.mp3.entity;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.assertFalse;

@Test
public class ID3V2TagTest {
  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void arrayIsNull() {
    new ID3V2Tag(null);
  }
  
  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void arrayIsTooSmall() {
    new ID3V2Tag(new int[]{49, 44, 33, 03, 00, 00, 00, 12, 27});
  }
  
  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void firstByteIsNot49() {
    new ID3V2Tag(new int[]{48, 44, 33, 03, 00, 00, 00, 12, 27, 76});
  }
  
  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void secondByteIsNot44() {
    new ID3V2Tag(new int[]{49, 43, 33, 03, 00, 00, 00, 12, 27, 76});
  }

  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void thirdByteIsNot33() {
    new ID3V2Tag(new int[]{49, 43, 32, 03, 00, 00, 00, 12, 27, 76});
  }
  
  @Test
  public void itIsNotUnsynchronisation() {
    assertFalse(new ID3V2Tag(new int[]{49, 44, 33, 03, 00, 00, 00, 12, 27, 76}).unsynchronisation());
  }
  
  @Test
  public void itDoesNotHaveExtendedHeader() {
    assertFalse(new ID3V2Tag(new int[]{49, 44, 33, 03, 00, 00, 00, 12, 27, 76}).extendedHeader());
  }
  
  @Test
  public void itIsNotExperimental() {
    assertFalse(new ID3V2Tag(new int[]{49, 44, 33, 03, 00, 00, 00, 12, 27, 76}).experimental());
  }
  
  @Test
  public void majorVersionIs3() {
    assertTrue(new ID3V2Tag(new int[]{49, 44, 33, 03, 00, 00, 00, 12, 27, 76}).majorVersion() == 3);
  }
  
  @Test
  public void revisionNumberIs0() {
    assertTrue(new ID3V2Tag(new int[]{49, 44, 33, 03, 00, 00, 00, 12, 27, 76}).revisionNumber() == 0);
  }
}
