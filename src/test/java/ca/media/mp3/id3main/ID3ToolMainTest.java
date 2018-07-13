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
    assertEquals(outContent.toString(), String.format("%s%n", "{\"size\":300022, \"revisionNumber\":0, \"flags\":0, \"majorVersion\":3}"));
  }

  @Test
  public void fileNameWasFoundButItWasNotAMP3File() {
    ID3ToolMain.main(new String[]{"src/test/resources/emptyFile.mp3"});
    assertEquals(outContent.toString(), String.format("%s%n", "Array does not contain an ID3 V2 tag"));
  }
}
