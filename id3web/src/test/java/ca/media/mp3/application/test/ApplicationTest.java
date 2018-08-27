package ca.media.mp3.application.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ca.media.mp3.controller.ID3Controller;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest 
{
  @Autowired
  private ID3Controller controller;
  
  @Test
  public void controllerIsNotNull() throws Exception
  {
    assertNotNull(controller);
  }
  
  @Autowired
  private MockMvc mockMvc;
  
  @Test
  public void responseIsTheSystemCannotTheFindPath() throws Exception
  {
    String url = "/id3tag?mp3=file:///notAPathInTheFileSystem/journey.mp3";
    String expectedMessage = "The system cannot find the path specified";
    this.mockMvc.
      perform(get(url)).
      andDo(print()).
      andExpect(status().isNotFound()).
      andExpect(content().string(containsString(expectedMessage)));
  }
}
