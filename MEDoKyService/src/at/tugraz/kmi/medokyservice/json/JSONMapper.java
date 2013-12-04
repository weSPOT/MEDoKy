package at.tugraz.kmi.medokyservice.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Singleton Object to JSON Mapper
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 */
public abstract class JSONMapper {
  private static Gson instance = null;

  /**
   * 
   * @return Gson instance
   */
  public static Gson getInstance() {
    if (instance == null)
      instance = new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy()).create();
    return instance;
  }

}
