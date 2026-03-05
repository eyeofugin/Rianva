package utils;

public class Range {
  public int from;
  public int until;

  public Range(int from, int until) {
    this.from = from;
    this.until = until;
  }

  public boolean contains(int a) {
    return a >= from && a <= until;
  }
}
