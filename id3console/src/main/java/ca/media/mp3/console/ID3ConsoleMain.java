package ca.media.mp3.console;

import ca.media.mp3.adapter.ConsolePresenter;
import ca.media.mp3.adapter.ID3ReaderFactory;
import ca.media.mp3.adapter.MP3Formatter;
import ca.media.mp3.adapter.JSONFormatter;
import ca.media.mp3.adapter.Presenter;
import ca.media.mp3.entity.ID3Reader;
import ca.media.mp3.entity.MP3;

import java.net.MalformedURLException;

public class ID3ConsoleMain {
  
  public static void main(String[] args) throws MalformedURLException {

    Presenter presenter = new ConsolePresenter();
    if(args.length == 0) {
      presenter.display("usage: id3 <path to MP3 file>");
      return;
    }
    
    ID3Reader tool = ID3ReaderFactory.makeAnID3Reader(args[0]);
    MP3Formatter formatter = new JSONFormatter();
    presenter.display(formatter.format(new MP3(args[0], tool.perform())));

  }
}
