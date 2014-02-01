package at.tugraz.kmi.medokyservice.fca.db.conf;

import at.tugraz.kmi.medokyservice.fca.db.Database;

/**
 * Contains {@link Database} configuration parameters
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public interface DBConfig {
  /**
   * The name of the persistent database file
   */
  public static final String DB_PATH = "at.tugraz.kmi.medoky.fca.db";
  /**
   * The path to the persistent database file
   */
  public static final String DB_DIR = "webapps/";
}
