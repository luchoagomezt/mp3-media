package ca.media.mp3.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.media.mp3.web.controller.ID3Controller;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ID3ApplicationSmokeTest 
{
  @Autowired
  private ID3Controller controller;
  
  @Test
  public void contextLoads() throws Exception
  {
    assertThat(controller).isNotNull();
  }  
}
