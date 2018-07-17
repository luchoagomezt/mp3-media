package ca.media.mp3.application;

import java.io.IOException;
import java.io.InputStream;

public interface ID3Reader {

  String perform();
  int[] read(InputStream stream) throws IOException;
}
