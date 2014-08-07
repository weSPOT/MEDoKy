package at.tugraz.kmi.medokyservice.fca.tests;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Failure {
  public boolean failed = false;
  ConcurrentLinkedQueue<Exception> eceptions = new ConcurrentLinkedQueue<Exception>();
}
