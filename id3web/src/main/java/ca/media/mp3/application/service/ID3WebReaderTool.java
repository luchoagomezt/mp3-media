package ca.media.mp3.application.service;

import ca.media.mp3.entity.ID3V2Tag;
import ca.media.mp3.entity.MP3MediaException;
import ca.media.mp3.usercase.ID3Tool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service("ID3WebReader")
public class ID3WebReaderTool implements ID3WebReader
{
  private URL url;
  
  @Override
  public void setUrl(URL url)
  {
    this.url = url;
  }
  
  @Override
  public ID3V2Tag perform()
  {
    try {
      final InputStream mp3File = url.openConnection().getInputStream();
      return new ID3Tool(mp3File).perform();
    } catch (FileNotFoundException e) {
      throw new MP3MediaException(new FileNotFoundException("(No such file or directory)"));
    } catch (IOException e) {
      throw new MP3MediaException(e);
    }
  }
  
  @Override
  public URL getURL()
  {
    return url;
  }
}
