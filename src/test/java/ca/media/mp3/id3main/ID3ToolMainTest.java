package ca.media.mp3.id3main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.assertEquals;
import ca.media.mp3.application.MP3MediaException;

public class ID3ToolMainTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  
  @BeforeClass
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }
  
  @AfterClass
  public void restoreStreams() {
    System.setOut(originalOut);
  }
  
  @BeforeMethod
  public void resetOutContent() {
    outContent.reset();
  }
  
  @Test
  public void usingTheConstructorJustForCoverage() {
    new ID3ToolMain();
  }

  @Test
  public void emptyParameters() {
    ID3ToolMain.main(new String[]{});
    assertEquals(outContent.toString(), String.format("%s%n", "usage: id3 <path to MP3 file>"));
  }

  @Test(expectedExceptions = {MP3MediaException.class})
  public void fileNameWasNotFound() {
    ID3ToolMain.main(new String[]{"notAFile.mp3"});
    assertEquals(outContent.toString(), String.format("%s%n", "notAFile.mp3 (No such file or directory)"));
  }

  @Test
  public void fileNameWasFoundAndProcessed() {
    ID3ToolMain.main(new String[]{"src/test/resources/journey.mp3"});
    assertEquals(outContent.toString(), 
      String.format("%s%n", "{\"header\":{\"version\":3, \"revision\":0, \"flags\":0, \"size\":300022}, \"frames\":["
        + "{\"id\":\"TIT2\", \"size\":6, \"flags\":{\"first\":0, \"second\":0}, \"content\":\"Title\"}, "
        + "{\"id\":\"TPE1\", \"size\":7, \"flags\":{\"first\":0, \"second\":0}, \"content\":\"Artist\"}, "
        + "{\"id\":\"TALB\", \"size\":6, \"flags\":{\"first\":0, \"second\":0}, \"content\":\"Album\"}, "
        + "{\"id\":\"TYER\", \"size\":5, \"flags\":{\"first\":0, \"second\":0}, \"content\":\"1962\"}, "
        + "{\"id\":\"TCON\", \"size\":6, \"flags\":{\"first\":0, \"second\":0}, \"content\":\"Genre\"}, "
        + "{\"id\":\"TRCK\", \"size\":3, \"flags\":{\"first\":0, \"second\":0}, \"content\":\"10\"}]}"));
  }

  @Test(expectedExceptions = {MP3MediaException.class})
  public void fileNameWasFoundButItWasNotAMP3File() {
    ID3ToolMain.main(new String[]{"src/test/resources/notAMP3File.mp3"});
    assertEquals(outContent.toString(), String.format("%s%n", "The stream does not contain an ID3 V2 tag"));
  }
}
