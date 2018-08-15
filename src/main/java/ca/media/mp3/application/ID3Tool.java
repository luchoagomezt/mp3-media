package ca.media.mp3.application;

import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import ca.media.mp3.entity.ID3Reader;
import ca.media.mp3.entity.ID3V2Tag;
import ca.media.mp3.entity.MP3MediaException;

public class ID3Tool implements ID3Reader 
{
  private final InputStream stream;
  private static final Logger logger = LogManager.getLogger("ID3Tool.class");

  public ID3Tool(InputStream stream) 
  {
    this.stream = stream;
  }

  @Override
  public ID3V2Tag perform() 
  {
    int[] header = new int[ID3V2Tag.HEADER_SIZE];
    readBuffer(header, 0);
    checkForValidHeader(header);
    logger.debug("header[6-9]:  " + header[6] + " " + header[7] + " " + header[8] + " " + header[9]);

    int[] data = new int[ID3V2Tag.HEADER_SIZE + ID3V2Tag.calculateTagSize(header)];
    arrayCopy(header, data);
    logger.debug("data length: " + data.length);
    readBuffer(data, ID3V2Tag.HEADER_SIZE);
 
    return new ID3V2Tag(data);
  }

  private void checkForValidHeader(int[] header)
  {
    if (!ID3V2Tag.isAnID3V2tag(header)) {
      throw new MP3MediaException("The stream does not contain an ID3 V2 tag");
    }
  }

  private void readBuffer(int[] buffer, int from)
  {
    try {
      for(int i = from; i < buffer.length; i++) {
        buffer[i] = stream.read();
      }
      logger.debug("read " + buffer.length + "bytes into the buffer");
    } catch (IOException e) {
      throw new MP3MediaException(e);
    }
  }

  private void arrayCopy(int[] src, int[] dest) 
  {
    System.arraycopy(src, 0, dest, 0, src.length);
  }

}
