package ca.media.mp3.id3main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ca.media.mp3.adapter.ID3ReaderFactory;
import static ca.media.mp3.adapter.ID3ReaderFactory.SIMPLE_PRESENTER;
import ca.media.mp3.application.ID3Reader;

public class ID3ToolMain {
  
  public static void main(String[] args) {
    if(args.length == 0) {
      return;
    }
    
    try (final InputStream mp3File = new BufferedInputStream(new FileInputStream(args[0]))) {
      ID3Reader tool = ID3ReaderFactory.makeAnID3Reader(SIMPLE_PRESENTER);
      tool.read(mp3File);
      System.out.println(tool.perform());
    } catch (FileNotFoundException e) {
      System.out.println(args[0] + " (No such file or directory)");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }
}
