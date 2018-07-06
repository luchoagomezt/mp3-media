package ca.media.mp3.adapter;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ca.media.mp3.application.ID3Tool;
import ca.media.mp3.application.ID3Reader;

public class ID3ToolMain {
  
  public static void main(String[] args) {
    if(args.length == 0) {
      return;
    }
    
    try (InputStream mp3File = new BufferedInputStream(new FileInputStream(args[0]))) {
      ID3Reader tool = new ID3Tool(new Presenter());
      System.out.print(tool.perform(mp3File));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
