package ca.media.mp3.adapter;

import org.testng.annotations.Test;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertNotNull;

public class ID3ReaderFactoryTest {

  @Test
  public void instantiateAFactory() {
    assertNotNull(new ID3ReaderFactory());
  }

  @Test
  public void returnsANullObject() {
    assertNull(ID3ReaderFactory.getReader("NOTHING"));
  }

  @Test
  public void presentJustTheHeader() {
    assertNotNull(ID3ReaderFactory.getReader(ID3ReaderFactory.HEADER_ONLY));
  }

  @Test
  public void presentAJSONString() {
    assertNotNull(ID3ReaderFactory.getReader(ID3ReaderFactory.JSON_STRING));
  }
}
