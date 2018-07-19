package ca.media.mp3.id3main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.assertEquals;

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
    assertEquals(outContent.toString(), "");
  }

  @Test
  public void fileNameWasNotFound() {
    ID3ToolMain.main(new String[]{"notAFile.mp3"});
    assertEquals(outContent.toString(), String.format("%s%n", "notAFile.mp3 (No such file or directory)"));
  }

  @Test
  public void fileNameWasFoundAndProcessed() {
    ID3ToolMain.main(new String[]{"src/test/resources/journey.mp3"});
    assertEquals(outContent.toString(), String.format("%s%n", "{\"header\":{\"version\":3, \"revision\":0, \"flags\":0, \"size\":300022}, \"frames\":[{\"id\":\"TENC\", \"size\":45, \"flags\":{\"first\":0, \"second\":0}, \"content\":[1, 255, 254, 76, 0, 65, 0, 77, 0, 69, 0, 32, 0, 51, 0, 46, 0, 57, 0, 56, 0, 32, 0, 40, 0, 77, 0, 97, 0, 120, 0, 32, 0, 48, 0, 46, 0, 57, 0, 46, 0, 49, 0, 41, 0]}]}"));
  }

  @Test
  public void fileNameWasFoundButItWasNotAMP3File() {
    ID3ToolMain.main(new String[]{"src/test/resources/notAMP3File.mp3"});
    assertEquals(outContent.toString(), String.format("%s%n", "The stream does not contain an ID3 V2 tag"));
  }
}
