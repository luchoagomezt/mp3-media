package ca.media.mp3.entity;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;

public class FrameTest {
  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void aNullArrayIsNotAframe() {
    Frame.isAFrameTag(null);
  }

  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void aTooShortArrayIsNotAFrame() {
    Frame.isAFrameTag(new int[]{});
  }

  @Test
  public void firstSizeByteIsMoreThan0x7F() {
    assertFalse(Frame.isAFrameTag(new int[]{0x49, 0x49, 0x49, 0x49, 0x80, 0x7F, 0x7F, 0x7F, 0x00, 0x00}));
  }

  @Test
  public void secondSizeByteIsMoreThan0x7F() {
    assertFalse(Frame.isAFrameTag(new int[]{0x49, 0x49, 0x49, 0x49, 0x7F, 0x80, 0x7F, 0x7F, 0x00, 0x00}));
  }
  @Test
  public void thirdSizeByteIsMoreThan0x7F() {
    assertFalse(Frame.isAFrameTag(new int[]{0x49, 0x49, 0x49, 0x49, 0x7F, 0x7F, 0x80, 0x7F, 0x00, 0x00}));
  }

  @Test
  public void lastSizeByteIsMoreThan0x7F() {
    assertFalse(Frame.isAFrameTag(new int[]{0x49, 0x49, 0x49, 0x49, 0x7F, 0x7F, 0x7F, 0x80, 0x00, 0x00}));
  }

  @Test
  public void thisArrayIsAValidFrame() {
    assertTrue(Frame.isAFrameTag(new int[]{0x49, 0x49, 0x49, 0x49, 0x7F, 0x7F, 0x7F, 0x7F, 0x00, 0x00}));
  }

  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void constructorReceivesANullArray() {
    new Frame(null);
  }

  @Test
  public void constructorReceivesAValidFrameArray() {
    new Frame(new int[]{0x49, 0x49, 0x49, 0x49, 0x7F, 0x7F, 0x7F, 0x7F, 0x00, 0x00});
  }

  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void constructorReceivesAnInvalidFrameArray() {
    new Frame(new int[]{0x49, 0x49, 0x49, 0x49, 0x8F, 0x7F, 0x7F, 0x7F, 0x00, 0x00});
  }

  @Test
  public void frameToString() {
    Frame frame = new Frame(new int[]{84, 73, 84, 50, 0, 0, 0, 6, 0, 0, 0, 84, 105, 116, 118, 101});
    assertEquals(frame.toString(), "{\"id\":\"TIT2\", \"size\":6, \"flags\":{\"first\":0, \"second\":0}, \"content\":[0, 84, 105, 116, 118, 101]}");
  }
}
