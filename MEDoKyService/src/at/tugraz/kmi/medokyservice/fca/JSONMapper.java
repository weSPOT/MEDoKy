package at.tugraz.kmi.medokyservice.fca;

import org.codehaus.jackson.map.ObjectMapper;
/**
 * Singleton Object to JSON Mapper
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 *
 */
public class JSONMapper extends ObjectMapper {
  private static JSONMapper instance = null;

  private JSONMapper() {
  }

  /**
   * 
   * @return JSONMapper instance
   */
  public static JSONMapper getInstance() {
    if (instance == null)
      instance = new JSONMapper();
    return instance;
  }

}
