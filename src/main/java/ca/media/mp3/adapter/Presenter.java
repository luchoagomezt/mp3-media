package ca.media.mp3.adapter;

import java.util.Map;

public class Presenter {

  public String mapToString(Map<String, Integer> map) {
    return map.
        entrySet().
        stream().
        map(e -> "\"" + e.getKey() + "\":" + e.getValue()).
        reduce((s1, s2) -> s1 + ", " + s2).
        orElse("");
  }
 
  public  void view(String output) {
    System.out.printf("{%s}%n", output);
  }
}
