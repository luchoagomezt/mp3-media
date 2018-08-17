package ca.media.mp3.adapter;

public class ConsolePresenter implements Presenter
{

  @Override
  public void display(String output) 
  {
    System.out.printf("%s%n",  output);
  }

}
