package ca.media.mp3.adapter;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ca.media.mp3.application.ID3Reader;
import ca.media.mp3.application.ID3Tool;
import ca.media.mp3.application.MP3MediaException;

public class ID3ReaderFactory {

  public static ID3Reader makeAnID3Reader(String filePath) {
    try {
      final InputStream mp3File = new BufferedInputStream(new FileInputStream(filePath));
      ID3Reader reader = new ID3Tool(mp3File);
      return reader;
    } catch (FileNotFoundException e) {
      throw new MP3MediaException(filePath + " (No such file or directory)");
    }

  }

}
