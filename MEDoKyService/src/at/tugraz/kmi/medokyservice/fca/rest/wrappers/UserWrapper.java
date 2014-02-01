package at.tugraz.kmi.medokyservice.fca.rest.wrappers;

/**
 * Wrapper class for creating a User throigh the REST interface
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public class UserWrapper extends AbstractWrapper {
  /**
   * a flag indicating whether this user is a teacher or a learner
   */
  public boolean teacher;
  /**
   * the external UID of the user to be created
   */
  public String externalUID;
}
