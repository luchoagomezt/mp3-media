package ca.media.mp3.application;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.AssertJUnit.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.testng.annotations.Test;

@Test
public class ID3V2ToolTest {
  
  @Test
  public void performOnMP3WithID3V2Tag() throws IOException {
    ID3Tool tool = new ID3Tool(new ByteArrayInputStream(new byte[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 00, 0x02, 0x00}));
    //assertTrue(tool.perform());
  }

  @Test
  public void performOnMP3WithoutID3V2Tag() throws IOException {
    ID3Tool tool = new ID3Tool(new ByteArrayInputStream(new byte[]{60, 60, 33, 03, 00, 00, 00, 00, 0x02, 0x00}));
    assertEquals(tool.perform(), "Array does not contain an ID3 V2 tag");
  }

  @Test(expectedExceptions = {IllegalArgumentException.class})
  public void performOnNullArray() throws IOException {
    new ID3Tool(null);
  }

  @Test
  public void readOnNonNullArray() throws IOException {
    InputStream stream = new ByteArrayInputStream(new byte[]{60, 60, 33, 03, 00, 00, 00, 00, 0x02, 0x00});
    ID3Tool tool = new ID3Tool(stream);
    assertNotNull(tool.read(stream));
  }

  @Test
  public void readOnlyTenBytes() throws IOException {
    InputStream stream = new ByteArrayInputStream(new byte[]{60, 60, 33, 03, 00, 00, 00, 00, 0x02, 0x00, 127});
    ID3Tool tool = new ID3Tool(stream);
    assertEquals(tool.read(stream).length, 10);
  }

  @Test
  public void readUptoTenBytes() throws IOException {
    InputStream stream = new ByteArrayInputStream(new byte[]{60, 60, 33, 03, 00, 00});
    ID3Tool tool = new ID3Tool(stream);
    assertEquals(tool.read(stream).length, 6);
  }
}
