package ca.media.mp3.id3main;

import ca.media.mp3.adapter.ID3ReaderFactory;
import ca.media.mp3.application.ID3Reader;
import ca.media.mp3.entity.ID3V2Tag;
import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionalID3ToolMain {

  public static void main(String[] args) {
    Consumer<String> presenter = s -> System.out.printf("%s%n", s);
    if(args.length == 0) {
      presenter.accept("usage: id3 <path to MP3 file>");
      return;
    }
    
    ID3Reader tool = ID3ReaderFactory.makeAnID3Reader(args[0]);
    Function<ID3V2Tag, String> formatter = 
      t -> t.toString().replace("[{", "[\n{").replace(", {", ",\n{");
    presenter.accept((formatter.apply(tool.perform())));
  }
}
