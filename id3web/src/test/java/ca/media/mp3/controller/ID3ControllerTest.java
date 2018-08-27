package ca.media.mp3.controller;

import static org.testng.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.media.mp3.service.ID3WebReaderTool;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ID3Controller.class, ID3WebReaderTool.class})
public class ID3ControllerTest 
{
  @Autowired
  private ID3Controller controller;
  
  @Test
  public void controllerContextLoads() throws Exception
  {
    assertNotNull(controller);
  }

}
