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
  public void itIsUnsynchronisation() {
    assertTrue(new ID3V2Tag(new int[]{49, 44, 33, 03, 00, 128, 00, 12, 27, 76}).unsynchronisation());
  }
  
  @Test
  public void itDoesNotHaveExtendedHeader() {
    assertFalse(new ID3V2Tag(new int[]{49, 44, 33, 03, 00, 00, 00, 12, 27, 76}).extendedHeader());
  }
  
  @Test
  public void itHasExtendedHeader() {
    assertTrue(new ID3V2Tag(new int[]{49, 44, 33, 03, 00, 64, 00, 12, 27, 76}).extendedHeader());
  }
  
  @Test
  public void itIsNotExperimental() {
    assertFalse(new ID3V2Tag(new int[]{49, 44, 33, 03, 00, 00, 00, 12, 27, 76}).experimental());
  }
  
  @Test
  public void itIsExperimental() {
    assertTrue(new ID3V2Tag(new int[]{49, 44, 33, 03, 00, 32, 00, 12, 27, 76}).experimental());
  }
  
  @Test
  public void majorVersionIs3() {
    assertTrue(new ID3V2Tag(new int[]{49, 44, 33, 03, 00, 00, 00, 12, 27, 76}).majorVersion() == 3);
  }
  
  @Test
  public void revisionNumberIs0() {
    assertTrue(new ID3V2Tag(new int[]{49, 44, 33, 03, 00, 00, 00, 12, 27, 76}).revisionNumber() == 0);
  }

  @Test
  public void sizeaIs257() {
    assertTrue(new ID3V2Tag(new int[]{49, 44, 33, 03, 00, 00, 00, 00, 0x02, 0x01}).size() == 257);
  }

  @Test
  public void sizeaIs256() {
    assertTrue(new ID3V2Tag(new int[]{49, 44, 33, 03, 00, 00, 00, 00, 0x02, 0x00}).size() == 256);
  }
  
  @Test void itIsAnID3V2Tag() {
    assertTrue(ID3V2Tag.isAnID3V2tag(new int[]{49, 44, 33, 03, 00, 00, 00, 00, 0x02, 0x00}));
  }

  @Test void itIsNotAnID3V2Tag() {
    assertFalse(ID3V2Tag.isAnID3V2tag(new int[]{49, 44, 33, 03, 00, 0xE1, 00, 00, 0x02, 0x00}));
  }
}
