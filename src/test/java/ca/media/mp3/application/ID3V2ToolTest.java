package ca.media.mp3.application;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.testng.annotations.Test;

@Test
public class ID3V2ToolTest {
  
  @Test
  public void performOnMP3WithAnID3V2TagWithoutFrames() throws IOException {
    ID3Tool tool = new ID3Tool(new ByteArrayInputStream(new byte[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 00, 0x02, 0x00}), e -> e.toString());
    assertEquals(tool.perform(), "{\"version\":3, \"revision\":0, \"flags\":0x00, \"size\":256, \"frames\":[]}");
  }

  @Test
  public void performOnMP3WithAnID3V2TagWithOneFrame() throws IOException {
    byte[] data = new byte[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 00, 0x02, 0x00, 0x49, 0x49, 0x49, 0x49, 0x00, 0x00, 0x02, 0x01, 0x00, 0x00};
    ID3Tool tool = new ID3Tool(new ByteArrayInputStream(data), e -> e.toString());
    String expected = "{\"version\":3, \"revision\":0, \"flags\":0x00, \"size\":256, \"frames\":[{\"id\":IIII, \"size\":257, \"flags\":0x0000}]}";
    assertEquals(tool.perform(), expected);
  }

  @Test
  public void performOnMP3WithoutID3V2Tag() throws IOException {
    ID3Tool tool = new ID3Tool(new ByteArrayInputStream(new byte[]{60, 60, 33, 03, 00, 00, 00, 00, 0x02, 0x00}), e -> e.toString());
    assertEquals(tool.perform(), "Array does not contain an ID3 V2 tag");
  }

  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void performOnNullArray() throws IOException {
    new ID3Tool(null, null);
  }

  @Test
  public void readOnNonNullArray() throws IOException {
    InputStream stream = new ByteArrayInputStream(new byte[]{60, 60, 33, 03, 00, 00, 00, 00, 0x02, 0x00});
    ID3Tool tool = new ID3Tool(stream, e -> e.toString());
    assertNotNull(tool.read(stream));
  }

  @Test
  public void readOnlyTenBytes() throws IOException {
    InputStream stream = new ByteArrayInputStream(new byte[]{60, 60, 33, 03, 00, 00, 00, 00, 0x02, 0x00, 127});
    ID3Tool tool = new ID3Tool(stream, e -> e.toString());
    assertEquals(tool.read(stream).length, 11);
  }

  @Test
  public void readUptoTenBytes() throws IOException {
    InputStream stream = new ByteArrayInputStream(new byte[]{60, 60, 33, 03, 00, 00});
    ID3Tool tool = new ID3Tool(stream, e -> e.toString());
    assertEquals(tool.read(stream).length, 6);
  }

  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void formatterIsNull() {
    new ID3Tool(new ByteArrayInputStream(new byte[]{60, 60, 33, 03, 00, 00}), null);
  }
}
