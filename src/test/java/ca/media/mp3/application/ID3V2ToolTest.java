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
    ID3Tool tool = new ID3Tool(tag -> String.format("%d", tag.majorVersion()));
    assertTrue(tool.perform(new ByteArrayInputStream(new byte[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 00, 0x02, 0x00})).equalsIgnoreCase("3"));
  }

  @Test
  public void performOnMP3WithoutID3V2Tag() throws IOException {
    ID3Tool tool = new ID3Tool(t -> "");
    assertEquals(tool.perform(new ByteArrayInputStream(new byte[]{60, 60, 33, 03, 00, 00, 00, 00, 0x02, 0x00})), "Array does not contain an ID3 V2 tag");
  }

  @Test
  public void performOnNullArray() throws IOException {
    ID3Tool tool = new ID3Tool(tag -> "");
    assertEquals(tool.perform(null), "Array is null");
  }

  @Test
  public void readOnNonNullArray() throws IOException {
    ID3Tool tool = new ID3Tool(tag -> "");
    InputStream stream = new ByteArrayInputStream(new byte[]{60, 60, 33, 03, 00, 00, 00, 00, 0x02, 0x00});
    assertNotNull(tool.read(stream));
  }

  @Test
  public void readOnNullArray() throws IOException {
    ID3Tool tool = new ID3Tool(tag -> "");
    assertNull(tool.read(null));
  }

  @Test
  public void readOnlyTenBytes() throws IOException {
    ID3Tool tool = new ID3Tool(tag -> "");
    InputStream stream = new ByteArrayInputStream(new byte[]{60, 60, 33, 03, 00, 00, 00, 00, 0x02, 0x00, 127});
    assertEquals(tool.read(stream).length, 10);
  }

  @Test
  public void readUptoTenBytes() throws IOException {
    ID3Tool tool = new ID3Tool(tag -> "");
    InputStream stream = new ByteArrayInputStream(new byte[]{60, 60, 33, 03, 00, 00});
    assertEquals(tool.read(stream).length, 6);
  }
}
