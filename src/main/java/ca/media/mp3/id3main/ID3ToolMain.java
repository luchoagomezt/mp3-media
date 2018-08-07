package ca.media.mp3.id3main;

import ca.media.mp3.adapter.ID3ReaderFactory;
import ca.media.mp3.adapter.SimpleFormatter;
import ca.media.mp3.application.ID3Reader;
import ca.media.mp3.application.ID3TagFormatter;
import ca.media.mp3.application.MP3MediaException;

public class ID3ToolMain {
  
  public static void main(String[] args) {
    if(args.length == 0) {
      System.out.printf("%s%n", "usage: id3 <path to MP3 file>");
      return;
    }
    
    try {
      ID3Reader tool = ID3ReaderFactory.makeAnID3Reader(args[0]);
      ID3TagFormatter formatter = new SimpleFormatter();
      System.out.println(formatter.format(tool.perform()));
    } catch (MP3MediaException e) {
      System.out.println(e.getMessage());
    }
  }
}
