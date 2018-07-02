package ca.media.mp3.application;

import java.io.FileInputStream;
import java.io.IOException;

public interface MP3Reader {

  int[] read(FileInputStream stream) throws IOException;
}
