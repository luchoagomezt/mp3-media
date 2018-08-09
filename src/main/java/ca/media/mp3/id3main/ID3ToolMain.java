package ca.media.mp3.id3main;

import ca.media.mp3.adapter.ConsolePresenter;
import ca.media.mp3.adapter.ID3ReaderFactory;
import ca.media.mp3.adapter.SimpleFormatter;
import ca.media.mp3.application.ID3Reader;
import ca.media.mp3.application.ID3TagFormatter;
import ca.media.mp3.application.MP3MediaException;
import ca.media.mp3.application.Presenter;

public class ID3ToolMain {
  
  public static void main(String[] args) {
    Presenter presenter = new ConsolePresenter();
    if(args.length == 0) {
      presenter.display("usage: id3 <path to MP3 file>");
      return;
    }
    
    try {
      ID3Reader tool = ID3ReaderFactory.makeAnID3Reader(args[0]);
      ID3TagFormatter formatter = new SimpleFormatter();
      presenter.display((formatter.format(tool)));
    } catch (MP3MediaException e) {
      presenter.display(e.getMessage());
    }
  }
}
