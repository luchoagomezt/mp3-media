package ca.media.mp3.adapter;

import org.testng.annotations.Test;

import ca.media.mp3.entity.MP3MediaException;

import static org.testng.Assert.assertNotNull;

public class ID3ReaderFactoryTest {

  @Test(expectedExceptions = {MP3MediaException.class})
  public void checkFileNotFound() {
    ID3ReaderFactory.makeAnID3Reader("");
  }

  @Test
  public void returnAReader() {
    assertNotNull(ID3ReaderFactory.makeAnID3Reader("src/test/resources/journey.mp3"));
  }
}
