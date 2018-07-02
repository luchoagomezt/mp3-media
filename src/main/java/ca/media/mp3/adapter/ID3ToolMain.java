package ca.media.mp3.adapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import ca.media.mp3.application.ID3Tool;
import ca.media.mp3.application.ID3Writer;

public class ID3ToolMain {
  
  public static void main(String[] args) {
    if(args.length == 0) {
      return;
    }
    
    ID3Writer writer = t -> String.format(
      "{\"version\":%s, \"revision\":%s}%n", t.majorVersion(), t.revisionNumber());

    try (FileInputStream mp3File = new FileInputStream(args[0])) {
      ID3Tool tool = new ID3Tool(writer);
      System.out.print(tool.perform(tool.read(mp3File)));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
