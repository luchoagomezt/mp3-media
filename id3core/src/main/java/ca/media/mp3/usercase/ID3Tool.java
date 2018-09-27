package ca.media.mp3.usercase;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import ca.media.mp3.entity.ID3Reader;
import ca.media.mp3.entity.ID3V2Tag;
import ca.media.mp3.entity.MP3MediaException;

public class ID3Tool implements ID3Reader 
{
  private final InputStream stream;

  public ID3Tool(InputStream stream) 
  {
    this.stream = stream;
  }

  public ID3Tool(String filePath) 
  {
    try {
      this.stream = new BufferedInputStream(new FileInputStream(filePath));
    } catch (FileNotFoundException e) {
      throw new MP3MediaException(filePath + " (No such file or directory)");
    }
  }

  @Override
  public ID3V2Tag perform() 
  {
    int[] header = readHeader();
    int[] tag = readRestOfTag(ID3V2Tag.calculateTagSize(header));
    copySourceArrayOntoDestination(header, tag);
    return new ID3V2Tag(tag);
  }

  private int[] readRestOfTag(int amountInBytes) {
    int[] data = new int[ID3V2Tag.HEADER_SIZE + amountInBytes];
    readFromStreamIntoBufferAtIndex(data, ID3V2Tag.HEADER_SIZE);
    return data;
  }

  private int[] readHeader() {
    int[] header = new int[ID3V2Tag.HEADER_SIZE];
    readFromStreamIntoBufferAtIndex(header, 0);
    checkForValidHeader(header);
    return header;
  }

  private void checkForValidHeader(int[] header)
  {
    if (!ID3V2Tag.isAnID3V2tag(header)) {
      throw new MP3MediaException("The stream does not contain an ID3 V2 tag");
    }
  }

  private void readFromStreamIntoBufferAtIndex(int[] buffer, int from)
  {
    try {
      for(int i = from; i < buffer.length; i++) {
        buffer[i] = stream.read();
      }
    } catch (IOException e) {
      throw new MP3MediaException(e);
    }
  }

  private void copySourceArrayOntoDestination(int[] src, int[] dest) 
  {
    System.arraycopy(src, 0, dest, 0, src.length);
  }

}
