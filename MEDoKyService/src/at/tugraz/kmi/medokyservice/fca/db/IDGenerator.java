package at.tugraz.kmi.medokyservice.fca.db;

/**
 * Singelton generating sequential unique {@link Long} ids starting at -(2^50)
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 */
public class IDGenerator {
  private static IDGenerator instance = null;
  /**
   * The last generated id
   */
  long lastId;

  private IDGenerator() {
    lastId = (long) -Math.pow(2, 50);
  }

  /**
   * @return IDGenerator instance
   */
  public static synchronized IDGenerator getInstance() {
    if (instance == null)
      instance = new IDGenerator();
    return instance;
  }

  /**
   * Returns the next valid id
   * 
   * @return the next valid id
   */
  public synchronized long getID() {
    return ++lastId;
  }

  void reset() {
    lastId = (long) -Math.pow(2, 50);
  }
}
