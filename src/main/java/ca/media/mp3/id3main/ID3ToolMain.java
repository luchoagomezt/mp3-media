package ca.media.mp3.id3main;

import ca.media.mp3.adapter.Controller;
import ca.media.mp3.adapter.Presenter;

public class ID3ToolMain {
  
  public static void main(String[] args) {
    if(args.length == 0) {
      return;
    }
    
    Controller controller = new Controller();
    Presenter presenter = new Presenter();
    presenter.view(controller.perform(args[0]));
  }
}
