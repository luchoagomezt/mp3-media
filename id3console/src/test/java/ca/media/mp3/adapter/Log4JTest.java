package ca.media.mp3.adapter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Log4JTest
{
  private static Logger logger;

  @BeforeTest
  public void setUp() 
  {
    logger = LogManager.getLogger("Log4JTest.class");
  }

  @Test
  public void logCreate() 
  {
    logger.info("Hello, World!");
  }
}