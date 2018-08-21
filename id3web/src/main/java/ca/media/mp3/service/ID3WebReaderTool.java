package ca.media.mp3.service;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import ca.media.mp3.entity.ID3Reader;
import ca.media.mp3.entity.ID3V2Tag;
import ca.media.mp3.entity.MP3MediaException;
import ca.media.mp3.usercase.ID3Tool;

@Service
@EnableConfigurationProperties(ID3ServiceProperties.class)
public class ID3Service implements ID3Reader
{
  private final ID3Reader reader;
  public ID3Service(String ulr)
  {
    try {
      final InputStream mp3File = new BufferedInputStream(new URL(ulr).openStream());
      reader = new ID3Tool(mp3File);
    } catch (FileNotFoundException e) {
      throw new MP3MediaException(ulr + " (No such file or directory)");
    } catch (IllegalArgumentException e) {
      throw new MP3MediaException(e);
    } catch (IOException e) {
      throw new MP3MediaException(e);
    }
  }
  
  public ID3V2Tag perform() {
    return reader.perform(); 
  }
  
}
