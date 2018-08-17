package ca.media.mp3.id3main;

import ca.media.mp3.adapter.ConsolePresenter;
import ca.media.mp3.adapter.ID3ReaderFactory;
import ca.media.mp3.adapter.ID3TagFormatter;
import ca.media.mp3.adapter.Presenter;
import ca.media.mp3.adapter.SimpleFormatter;
import ca.media.mp3.entity.ID3Reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ID3ToolMain {
  private static final Logger logger = LogManager.getLogger("ID3ToolMain.class");
  
  public static void main(String[] args) {
    logger.debug("starting tool");

    Presenter presenter = new ConsolePresenter();
    if(args.length == 0) {
      logger.debug("args array is empty, stopping tool");
      presenter.display("usage: id3 <path to MP3 file>");
      return;
    }
    
    logger.debug("args[0]: " + args[0]);
    ID3Reader tool = ID3ReaderFactory.makeAnID3Reader(args[0]);
    ID3TagFormatter formatter = new SimpleFormatter();
    presenter.display((formatter.format(tool.perform())));

    logger.debug("stopping tool");
  }
}
