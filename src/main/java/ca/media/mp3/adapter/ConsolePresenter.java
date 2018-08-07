package ca.media.mp3.adapter;

import ca.media.mp3.application.Presenter;

public class ConsolePresenter implements Presenter
{

  @Override
  public void display(String output) 
  {
    System.out.printf("%s%n",  output);
  }

}
