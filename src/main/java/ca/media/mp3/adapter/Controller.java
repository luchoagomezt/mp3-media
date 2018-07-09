package ca.media.mp3.adapter;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ca.media.mp3.application.ID3Reader;
import ca.media.mp3.application.ID3Tool;

public class Controller {
  
  public String perform(String name) {
    try (InputStream mp3File = new BufferedInputStream(new FileInputStream(name))) {
      ID3Reader tool = new ID3Tool(mp3File);
      return tool.perform();
    } catch (FileNotFoundException e) {
      return e.getMessage();
    } catch (IOException e) {
      return e.getMessage();
    }
  }

}
