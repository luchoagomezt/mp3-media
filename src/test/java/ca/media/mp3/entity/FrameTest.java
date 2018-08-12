package ca.media.mp3.entity;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;

import static org.testng.Assert.assertEquals;

public class FrameTest {
  @Test(
    expectedExceptions = {IllegalArgumentException.class})
  public void aTooShortArrayIsNotAFrame() 
  {
    Frame.isValid(new int[]{});
  }

  @Test
  public void thisArrayIsAValidFrame() 
  {
    assertTrue(Frame.isValid(new int[]{0x49, 0x49, 0x49, 0x49, 0x7F, 0x7F, 0x7F, 0x7F, 0x00, 0x00}));
  }

  @Test
  public void constructorReceivesAValidFrameArray() 
  {
    new Frame(new int[]{0x49, 0x49, 0x49, 0x49, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
  }

  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void constructorReceivesAnInvalidFrameArray() {
    new Frame(new int[]{0x49, 0x49, 0x49, 0x49, 256, 0x7F, 0x7F, 0x7F, 0x00, 0x00});
  }

  @Test
  public void frameToString() 
  {
    Frame frame = new Frame(new int[]{84, 73, 84, 50, 0, 0, 0, 6, 0, 0, 0, 84, 105, 116, 118, 101});
    assertEquals(frame.toString(), "{\"id\":\"TIT2\", \"size\":6, \"flags\":{\"first\":0, \"second\":0}, \"content\":\"Titve\"}");
  }

  @Test(expectedExceptions = {IllegalArgumentException.class}, 
    expectedExceptionsMessageRegExp = 
      "Length of the data array passed as a parameter is less than the valid size for a frame's header")
  public void calculateSizeOnEmptyArray() 
  {
    Frame.calculateContentSize(new int[] {});
  }

  @DataProvider(name = "frameArrayProvider")
  private Object[][] frameArrayDataProvider() 
  {
    return new Object[][]{
      {new int[]{0x49, 0x49, 0x49, 0x49, 256, 0x7F, 0x7F, 0x7F, 0x00, 0x00}},
      {new int[]{0x49, 0x49, 0x49, 0x49, 0x7F, 256, 0x7F, 0x7F, 0x00, 0x00}},
      {new int[]{0x49, 0x49, 0x49, 0x49, 0x7F, 0x7F, 256, 0x7F, 0x00, 0x00}},
      {new int[]{0x49, 0x49, 0x49, 0x49, 0x7F, 0x7F, 0x7F, 256, 0x00, 0x00}},
    };
  }

  @Test(dataProvider = "frameArrayProvider", expectedExceptions = {IllegalArgumentException.class}, 
    expectedExceptionsMessageRegExp = "One or more of the four size bytes is more or equal to 256")
  public void calculateSizeOnInvalidArray(int[] frameArray) 
  {
    Frame.calculateContentSize(frameArray);
  }
  
  @Test(expectedExceptions = {IllegalArgumentException.class}, 
    expectedExceptionsMessageRegExp = "Encoding byte is invalid")
  public void receivesAnInvalidEncodingByte() 
  {
    new Frame(new int[]{0x49, 0x49, 0x49, 0x49, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00, 0x01, 84, 105, 116, 118, 101});
  }
}
