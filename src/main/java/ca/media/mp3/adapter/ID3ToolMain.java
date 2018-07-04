package ca.media.mp3.adapter;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ca.media.mp3.application.ID3Tool;
import ca.media.mp3.application.ID3Formatter;

public class ID3ToolMain {
  
  public static void main(String[] args) {
    if(args.length == 0) {
      return;
    }
    
    ID3Formatter formatter = t -> String.format(
      "{\"version\":%s, \"revision\":%s, \"flags\":%d, \"size\":%d}%n", 
      t.majorVersion(), t.revisionNumber(), t.flags(), t.size());

    try (InputStream mp3File = new BufferedInputStream(new FileInputStream(args[0]))) {
      ID3Tool tool = new ID3Tool(formatter);
      System.out.print(tool.perform(tool.read(mp3File)));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
