package tests;

import java.util.concurrent.ConcurrentLinkedDeque;

public class Failure {
  public boolean failed = false;
  ConcurrentLinkedDeque<Exception> eceptions = new ConcurrentLinkedDeque<Exception>();
}
