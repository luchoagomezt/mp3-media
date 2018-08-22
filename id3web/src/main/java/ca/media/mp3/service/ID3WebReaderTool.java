package ca.media.mp3.service;

import ca.media.mp3.entity.ID3Reader;
import ca.media.mp3.entity.ID3V2Tag;
import ca.media.mp3.entity.MP3MediaException;
import ca.media.mp3.usercase.ID3Tool;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service("ID3WebReader")
public class ID3WebReaderTool implements ID3WebReader
{
  private ID3Reader reader;
  private String url;
  
  @Override
  public void setUrl(String url)
  {
    this.url = url;
  }
  
  @Override
  public ID3V2Tag perform()
  {
    try {
      final InputStream mp3File = new BufferedInputStream(new URL(url).openStream());
      reader = new ID3Tool(mp3File);
      return reader.perform();
    } catch (FileNotFoundException e) {
      throw new MP3MediaException(e);
    } catch (MalformedURLException e) {
      throw new MP3MediaException(e);
    } catch (IOException e) {
      throw new MP3MediaException(e);
    }
  }
}
