package ca.media.mp3.application;

import static org.testng.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.testng.annotations.Test;

import ca.media.mp3.entity.MP3MediaException;
import ca.media.mp3.usercase.ID3Tool;

@Test
public class ID3ToolTest {
  
  @Test
  public void performOnMP3WithAnID3V2TagWithoutFrames() throws IOException {
    byte[] tagWithoutFrames = new byte[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 00, 0x00, 0x00};
	ID3Tool tool = makeID3ToolFrom(tagWithoutFrames);
    assertEquals(tool.perform().toString(), "{\"version\":3, \"revision\":0, \"flags\":0, \"size\":0, \"frames\":[]}");
  }

  @Test
  public void performOnMP3WithAnID3V2TagWithOneFrame() throws IOException {
    byte[] tagWithOneFrame = new byte[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 00, 0x00, 0x0A, 0x49, 0x49, 0x49, 0x49, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    ID3Tool tool = makeID3ToolFrom(tagWithOneFrame);
    String expected = "{\"version\":3, \"revision\":0, \"flags\":0, \"size\":10, \"frames\":[]}";
    assertEquals(tool.perform().toString(), expected);
  }

  @Test(expectedExceptions = {MP3MediaException.class}, expectedExceptionsMessageRegExp = "The stream does not contain an ID3 V2 tag")
  public void performOnMP3WithoutID3V2Tag() throws IOException {
    byte[] tagWithoutFrames = new byte[]{60, 60, 33, 03, 00, 00, 00, 00, 0x02, 0x00};
	ID3Tool tool = makeID3ToolFrom(tagWithoutFrames);
    tool.perform();
  }

  @Test(expectedExceptions = {MP3MediaException.class}, expectedExceptionsMessageRegExp = "The stream does not contain an ID3 V2 tag")
  public void readUptoTenBytes() throws IOException {
    byte[] tagTooShort = new byte[]{60, 60, 33, 03, 00, 00};
    ID3Tool tool = makeID3ToolFrom(tagTooShort);
    tool.perform();
  }

  private ID3Tool makeID3ToolFrom(byte[] tag) {
	  return new ID3Tool(new ByteArrayInputStream(tag));
  }

}
