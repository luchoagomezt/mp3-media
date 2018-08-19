package ca.media.mp3.id3main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.testng.annotations.Test;

import ca.media.mp3.entity.MP3MediaException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.assertEquals;

public class FunctionalID3ConsoleMainTest {
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
    new FunctionalID3ConsoleMain();
  }

  @Test
  public void emptyParameters() {
  	FunctionalID3ConsoleMain.main(new String[]{});
    assertEquals(outContent.toString(), String.format("%s%n", "usage: id3 <path to MP3 file>"));
  }

  @Test(expectedExceptions = {MP3MediaException.class})
  public void fileNameWasNotFound() {
  	FunctionalID3ConsoleMain.main(new String[]{"notAFile.mp3"});
    assertEquals(outContent.toString(), String.format("%s%n", "notAFile.mp3 (No such file or directory)"));
  }

  @Test
  public void fileNameWasFoundAndProcessed() {
  	FunctionalID3ConsoleMain.main(new String[]{"src/test/resources/journey.mp3"});
    assertEquals(outContent.toString(), 
      String.format("%s%n", "{\"version\":3, \"revision\":0, \"flags\":0x00, \"size\":300022, \"frames\":[\n"
        + "{\"id\":\"TIT2\", \"size\":6, \"flags\":0x0000, \"content\":\"Title\"},\n"
        + "{\"id\":\"TPE1\", \"size\":7, \"flags\":0x0000, \"content\":\"Artist\"},\n"
        + "{\"id\":\"TALB\", \"size\":6, \"flags\":0x0000, \"content\":\"Album\"},\n"
        + "{\"id\":\"TYER\", \"size\":5, \"flags\":0x0000, \"content\":\"1962\"},\n"
        + "{\"id\":\"TCON\", \"size\":6, \"flags\":0x0000, \"content\":\"Genre\"},\n"
        + "{\"id\":\"TRCK\", \"size\":3, \"flags\":0x0000, \"content\":\"10\"}]}"));
  }

  @Test(expectedExceptions = {MP3MediaException.class})
  public void fileNameWasFoundButItWasNotAMP3File() {
  	FunctionalID3ConsoleMain.main(new String[]{"src/test/resources/notAMP3File.mp3"});
    assertEquals(outContent.toString(), String.format("%s%n", "The stream does not contain an ID3 V2 tag"));
  }
}
