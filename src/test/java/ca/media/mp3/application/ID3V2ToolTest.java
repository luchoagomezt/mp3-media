package ca.media.mp3.application;

import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;

@Test
public class ID3V2ToolTest {
  
  @Test
  public void performOnMP3WithID3V2Tag() {
    ID3Tool tool = new ID3Tool(tag -> String.format("%d", tag.majorVersion()));
    assertTrue(tool.perform(new int[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 00, 0x02, 0x00}).equalsIgnoreCase("3"));
  }

  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void performOnMP3WithoutID3V2Tag() {
    ID3Tool tool = new ID3Tool(tag -> String.format("%d", tag.majorVersion()));
    tool.perform(new int[]{60, 60, 33, 03, 00, 00, 00, 00, 0x02, 0x00}).equalsIgnoreCase("3");
  }

}
