package ca.media.mp3.adapter;

import ca.media.mp3.application.ID3Reader;
import ca.media.mp3.entity.ID3V2Tag;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class SimplePresenterTest {

  @Test
  public void tagToStringWithNonNullParameter() {
    ID3Reader tool = () -> new ID3V2Tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 00, 0x00, 0x00});
    assertEquals(new SimpleFormatter().format(tool),
      "{\"header\":{\"version\":3, \"revision\":0, \"flags\":0, \"size\":0}, \"frames\":[]}");
  }
}
