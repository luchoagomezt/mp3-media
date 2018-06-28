package ca.media.mp3.application;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;
import ca.media.mp3.entity.MP3;

@Test
public class ID3V2ToolTest {
  
  @Test
  public void performOnMP3WithID3V2Tag() {
    MP3Reader reader = () -> new MP3(new int[]{49, 44, 33, 03, 00, 00, 00, 00, 0x02, 0x00});
    ID3Writer writer = tag -> System.out.printf("%d%n", tag.majorVersion()); 
    assertTrue(new ID3Tool(reader, writer).perform()); 
  }

  @Test
  public void performOnMP3WithoutID3V2Tag() {
    MP3Reader reader = () -> new MP3(new int[]{60, 60, 33, 03, 00, 00, 00, 00, 0x02, 0x00});
    ID3Writer writer = tag -> System.out.printf("%d%n", tag.majorVersion()); 
    assertFalse(new ID3Tool(reader, writer).perform()); 
  }

}
