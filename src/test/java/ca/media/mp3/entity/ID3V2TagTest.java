package ca.media.mp3.entity;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Test
public class ID3V2TagTest {
  @Test(expectedExceptions = {IllegalArgumentException.class}, expectedExceptionsMessageRegExp = "Parameter is null")
  public void calculateTagSizeOnNullArray() {
    ID3V2Tag.calculateTagSizeExcludingHeader(null);
  }

  @Test(expectedExceptions = {IllegalArgumentException.class}, expectedExceptionsMessageRegExp = "Array's length is less than header's size")
  public void calculateTagSizeOnArrayWithLessBytesThanHeaderSize() {
    ID3V2Tag.calculateTagSizeExcludingHeader(new int[]{0, 0, 0});
  }

  @Test(expectedExceptions = {IllegalArgumentException.class}, expectedExceptionsMessageRegExp = "One or more of the four size bytes is more than 0x7F")
  public void calculateTagSizeOnArrayWithOneOrMoreByteSizeMoreThan0x7F() {
    ID3V2Tag.calculateTagSizeExcludingHeader(new int[]{0x49, 0x44, 0x33, 0x03, 00, 00, 0x80, 0x00, 0x80, 0x00});
  }

  @Test
  public void calculateTagSizeOnAValidArray() {
    assertEquals(ID3V2Tag.calculateTagSizeExcludingHeader(new int[]{0x49, 0x44, 0x33, 0x03, 00, 00, 0x00, 0x00, 0x02, 0x01}), 257);
  }

  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void arrayIsNull() {
    new ID3V2Tag(null);
  }
  
  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void arrayIsTooSmall() {
    new ID3V2Tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 12, 27});
  }
  
  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void firstByteIsNot49() {
    new ID3V2Tag(new int[]{0x48, 0x44, 0x33, 0x03, 00, 00, 00, 12, 27, 76});
  }
  
  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void secondByteIsNot44() {
    new ID3V2Tag(new int[]{0x49, 0x43, 0x33, 0x03, 00, 00, 00, 12, 27, 76});
  }

  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void thirdByteIsNot33() {
    new ID3V2Tag(new int[]{0x49, 0x43, 0x32, 0x03, 00, 00, 00, 12, 27, 76});
  }
  
  @Test
  public void itIsNotUnsynchronisation() {
    assertFalse(new ID3V2Tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 12, 27, 76}).unsynchronisation());
  }
  
  @Test
  public void itIsUnsynchronisation() {
    assertTrue(new ID3V2Tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 128, 00, 12, 27, 76}).unsynchronisation());
  }
  
  @Test
  public void itDoesNotHaveExtendedHeader() {
    assertFalse(new ID3V2Tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 00, 0x00, 0x12, 0x27, 0x76}).extendedHeader());
  }
  
  @Test
  public void itHasExtendedHeader() {
    assertTrue(new ID3V2Tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 64, 00, 12, 27, 76}).extendedHeader());
  }
  
  @Test
  public void getTheHeaderMap() {
    ID3V2Tag tag = new ID3V2Tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 64, 00, 12, 27, 76});
    assertNotNull(tag.header());
  }
  
  @Test
  public void itIsNotExperimental() {
    assertFalse(new ID3V2Tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 12, 27, 76}).experimental());
  }
  
  @Test
  public void itIsExperimental() {
    assertTrue(new ID3V2Tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 32, 00, 12, 27, 76}).experimental());
  }
  
  @Test
  public void majorVersionIs3() {
    assertEquals(new ID3V2Tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 12, 27, 76}).majorVersion(), 3);
  }
  
  @Test
  public void revisionNumberIs0() {
    assertEquals(new ID3V2Tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 12, 27, 76}).revisionNumber(), 0);
  }

  @Test
  public void sizeaIs257() {
    assertEquals(new ID3V2Tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 00, 0x02, 0x01}).size(), 257);
  }

  @Test
  public void flagsIsZero() {
    assertEquals(new ID3V2Tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 00, 0x02, 0x00}).flags(), 0);
  }
  
  @Test void itIsAnID3V2Tag() {
    assertTrue(ID3V2Tag.isAnID3V2tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 00, 0x02, 0x00}));
  }

  @Test void firstSizeByteIsMoreThan0x7F() {
    assertFalse(ID3V2Tag.isAnID3V2tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 0xE1, 0x80, 0x00, 0x00, 0x00}));
  }
  
  @Test void secondSizeByteIsMoreThan0x7F() {
    assertFalse(ID3V2Tag.isAnID3V2tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 0xE1, 0x7F, 0x80, 0x00, 0x00}));
  }
  
  @Test void thirdSizeByteIsMoreThan0x7F() {
    assertFalse(ID3V2Tag.isAnID3V2tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 0xE1, 0x7F, 0x7F, 0x80, 0x00}));
  }
  
  @Test void lastSizeByteIsMoreThan0x7F() {
    assertFalse(ID3V2Tag.isAnID3V2tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 0xE1, 0x7F, 0x7F, 0x7F, 0x80}));
  }
  
  @Test void theFlagIsMoreThan0xE0() {
    assertFalse(ID3V2Tag.isAnID3V2tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 0xE1, 00, 00, 0x02, 0x00}));
  }
  
  @Test void theMajorVersionIsMoreThan0xFE() {
    assertFalse(ID3V2Tag.isAnID3V2tag(new int[]{0x49, 0x44, 0x33, 0xFF, 00, 0xE0, 00, 00, 0x02, 0x00}));
  }
  
  @Test void theRevisionNumberIsMoreThan0xFE() {
    assertFalse(ID3V2Tag.isAnID3V2tag(new int[]{0x49, 0x44, 0x33, 0xFE, 0xFF, 0xE0, 00, 00, 0x02, 0x00}));
  }
  
  @Test void theFirstByteIsNot0x49() {
    assertFalse(ID3V2Tag.isAnID3V2tag(new int[]{0x50, 0x44, 0x33, 0xFE, 0xFE, 0xE0, 0x00, 0x00, 0x00, 0x00}));
  }
  
  @Test void theSecondByteIsNot0x44() {
    assertFalse(ID3V2Tag.isAnID3V2tag(new int[]{0x49, 0x43, 0x33, 0xFE, 0xFE, 0xE0, 0x00, 0x00, 0x00, 0x00}));
  }
  
  @Test void theThirdByteIsNot0x33() {
    assertFalse(ID3V2Tag.isAnID3V2tag(new int[]{0x49, 0x44, 0x32, 0xFE, 0xFE, 0xE0, 0x00, 0x00, 0x00, 0x00}));
  }
  
  @Test(expectedExceptions = {IllegalArgumentException.class})
  void isAnID3V2tagReceivesANullArray() {
    assertFalse(ID3V2Tag.isAnID3V2tag(null));
  }
  
  @Test(expectedExceptions = {IllegalArgumentException.class})
  void isAnID3V2tagReceivesATooShortArray() {
    assertFalse(ID3V2Tag.isAnID3V2tag(new int[]{0x49, 0x44, 0x33, 0x03, 00}));
  }
}
