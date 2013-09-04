package at.tugraz.kmi.medokyservice.fca.db.conf;

import at.tugraz.kmi.medokyservice.fca.db.Database;

/**
 * Contains {@link Database} configuration parameters
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 */
public interface DBConfig {
  /**
   * The path to the persistent database file
   */
  public static final String DB_PATH = "at.tugraz.kmi.medoky.fca.db";
}
