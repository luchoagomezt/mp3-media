package ca.media.mp3.id3main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.assertEquals;

public class ID3ToolMainTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  
  @BeforeMethod
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }
  
  @AfterMethod
  public void restoreStreams() {
    System.setOut(originalOut);
  }
  
  @Test
  public void emptyParameters() {
    ID3ToolMain.main(new String[]{});
    assertEquals(outContent.toString(), "");
  }

}
