package ca.media.mp3.adapter;

import ca.media.mp3.entity.ID3V2Tag;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class PresenterTest {

  @Test
  public void tagToStringWithNullParameter() {
    assertNull(new Presenter().tagToString(null));
  }

  @Test
  public void tagToStringWithNonNullParameter() {
    assertEquals(new Presenter().tagToString(new ID3V2Tag(new int[]{0x49, 0x44, 0x33, 0x03, 00, 00, 00, 00, 0x02, 0x00})), 
    "{\"size\":256, \"revisionNumber\":0, \"flags\":0, \"majorVersion\":3}");
  }
}
