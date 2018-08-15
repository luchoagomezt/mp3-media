package ca.media.mp3.entity;

public class MP3MediaException extends RuntimeException
{
  private static final long serialVersionUID = 20180807L;

  public MP3MediaException(Exception e) 
  {
    super(e);
  }

  public MP3MediaException(String message) 
  {
    super(message);
  }

}
