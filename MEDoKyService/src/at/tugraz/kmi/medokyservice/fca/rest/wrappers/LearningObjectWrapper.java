package at.tugraz.kmi.medokyservice.fca.rest.wrappers;

import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;

/**
 * Wrapper class to create {@link LearningObject}s
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public class LearningObjectWrapper extends AbstractWrapper {
  /**
   * External UID of the user that created this LearningObject
   */
  public String externalUID;
  public String data;
  public User owner;
  public boolean byLearner;
}
