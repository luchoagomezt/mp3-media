package ca.media.mp3.adapter;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ca.media.mp3.entity.ID3Reader;
import ca.media.mp3.entity.MP3MediaException;
import ca.media.mp3.usercase.ID3Tool;

public class ID3ReaderFactory {
  private ID3ReaderFactory() {
  }

  public static ID3Reader makeAnID3Reader(String filePath) {
    try {
      InputStream inputStream = new FileInputStream(filePath);
      try {
        final InputStream mp3File = new BufferedInputStream(inputStream);
        return new ID3Tool(mp3File);
      } finally {
        inputStream.close();
      }
    } catch (FileNotFoundException e) {
      throw new MP3MediaException(filePath + " (No such file or directory)");
    } catch (IOException e) {
      throw new MP3MediaException("Cannot close the input stream");
    }

  }

}
