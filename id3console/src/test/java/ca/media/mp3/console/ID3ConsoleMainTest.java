package ca.media.mp3.console;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;

import org.testng.annotations.Test;

import ca.media.mp3.console.ID3ConsoleMain;
import ca.media.mp3.entity.MP3MediaException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertEquals;

public class ID3ConsoleMainTest {
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
    new ID3ConsoleMain();
  }

  @Test
  public void emptyParameters() throws MalformedURLException {
    ID3ConsoleMain.main(new String[]{});
    assertEquals(outContent.toString(), String.format("%s%n", "usage: id3 <path to MP3 file>"));
  }

  @Test(expectedExceptions = {MP3MediaException.class})
  public void fileNameWasNotFound() throws MalformedURLException {
    ID3ConsoleMain.main(new String[]{"notAFile.mp3"});
    assertEquals(outContent.toString(), String.format("%s%n", "notAFile.mp3 (No such file or directory)"));
  }

  @Test
  public void fileNameWasFoundAndProcessed() throws MalformedURLException {
    ID3ConsoleMain.main(new String[]{"src/test/resources/journey.mp3"});
    assertFalse(outContent.toString().isEmpty());
  }

  @Test(expectedExceptions = {MP3MediaException.class})
  public void fileNameWasFoundButItWasNotAMP3File() throws MalformedURLException {
    ID3ConsoleMain.main(new String[]{"src/test/resources/notAMP3File.mp3"});
    assertEquals(outContent.toString(), String.format("%s%n", "The stream does not contain an ID3 V2 tag"));
  }
}
