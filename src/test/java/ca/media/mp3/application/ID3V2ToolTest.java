package ca.media.mp3.application;

import static org.testng.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.testng.annotations.Test;

@Test
public class ID3V2ToolTest {
  
  @Test
  public void performOnMP3WithAnID3V2TagWithoutFrames() throws IOException {
    ID3Tool tool = new ID3Tool(new ByteArrayInputStream(new byte[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 00, 0x00, 0x00}));
    assertEquals(tool.perform().toString(), "{\"version\":3, \"revision\":0, \"flags\":0, \"size\":0, \"frames\":[]}");
  }

  @Test
  public void performOnMP3WithAnID3V2TagWithOneFrame() throws IOException {
    byte[] data = new byte[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 00, 0x00, 0x0A, 0x49, 0x49, 0x49, 0x49, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    ID3Tool tool = new ID3Tool(new ByteArrayInputStream(data));
    String expected = "{\"version\":3, \"revision\":0, \"flags\":0, \"size\":10, \"frames\":[]}";
    assertEquals(tool.perform().toString(), expected);
  }

  @Test(expectedExceptions = {MP3MediaException.class}, expectedExceptionsMessageRegExp = "The stream does not contain an ID3 V2 tag")
  public void performOnMP3WithoutID3V2Tag() throws IOException {
    ID3Tool tool = new ID3Tool(new ByteArrayInputStream(new byte[]{60, 60, 33, 03, 00, 00, 00, 00, 0x02, 0x00}));
    tool.perform();
  }

  @Test(expectedExceptions = {MP3MediaException.class}, expectedExceptionsMessageRegExp = "The stream does not contain an ID3 V2 tag")
  public void readUptoTenBytes() throws IOException {
    InputStream stream = new ByteArrayInputStream(new byte[]{60, 60, 33, 03, 00, 00});
    ID3Tool tool = new ID3Tool(stream);
    tool.perform();
  }

}
