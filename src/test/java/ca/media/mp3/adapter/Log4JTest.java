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
    System.setProperty("log4j.configurationFile", "src/test/resources/log4j2-test.xml");
    logger = LogManager.getLogger("Log4JTest.class");
  }

  @Test
  public void logCreate() 
  {
    logger.info("Hello, World!");
  }
}