package ca.media.mp3.application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import ca.media.mp3.entity.ID3V2Tag;

public class ID3Tool implements MP3Reader {
  private final ID3Writer out;

  public ID3Tool(ID3Writer writer) {
    out = writer;
  }
  
  public String perform(int[] array) {
    if (array == null) {
      return "The array is null%n";
    }
    return out.print(new ID3V2Tag(array));
  }

  @Override
  public int[] read(FileInputStream stream) throws IOException {
    int buffer;
    List<Integer> list =  new ArrayList<>();
    while((buffer = stream.read()) != -1) {
      list.add(buffer);
    }
    return list.stream().mapToInt(x -> x).toArray();
  }
}
