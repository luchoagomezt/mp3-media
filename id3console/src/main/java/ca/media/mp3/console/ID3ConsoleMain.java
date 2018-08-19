package ca.media.mp3.console;

import ca.media.mp3.adapter.ConsolePresenter;
import ca.media.mp3.adapter.ID3ReaderFactory;
import ca.media.mp3.adapter.ID3TagFormatter;
import ca.media.mp3.adapter.Presenter;
import ca.media.mp3.adapter.SimpleFormatter;
import ca.media.mp3.entity.ID3Reader;

public class ID3ConsoleMain {
  
  public static void main(String[] args) {

    Presenter presenter = new ConsolePresenter();
    if(args.length == 0) {
      presenter.display("usage: id3 <path to MP3 file>");
      return;
    }
    
    ID3Reader tool = ID3ReaderFactory.makeAnID3Reader(args[0]);
    ID3TagFormatter formatter = new SimpleFormatter();
    presenter.display((formatter.format(tool.perform())));

  }
}
