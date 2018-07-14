package ca.media.mp3.id3main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ca.media.mp3.adapter.Presenter;
import ca.media.mp3.application.ID3Reader;
import ca.media.mp3.application.ID3Tool;

public class ID3ToolMain {
  
  public static void main(String[] args) {
    if(args.length == 0) {
      return;
    }
    
    try (InputStream mp3File = new BufferedInputStream(new FileInputStream(args[0]))) {
      ID3Reader tool = new ID3Tool(mp3File, new Presenter());
      System.out.println(tool.perform());
    } catch (FileNotFoundException e) {
      System.out.println(args[0] + " (No such file or directory)");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
