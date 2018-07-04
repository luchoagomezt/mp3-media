package ca.media.mp3.application;

import java.io.InputStream;
import java.io.IOException;

@FunctionalInterface
public interface ID3Reader {

  int[] read(InputStream stream) throws IOException;
}
