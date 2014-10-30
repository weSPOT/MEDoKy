package at.tugraz.kmi.medokyservice.fca.db.usermodel;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.IDGenerator;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Course;

/**
 * A User Object linked to an external database through an external UID Users
 * contain a map of objects to object valuations as well as attributes to
 * attribute valuations used to compute concept-based valuations
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public class User extends DataObject {

  private static final long serialVersionUID = -6963436680153937339L;
  private String externalUid;

  /**
   * Creates a new User with a unique id (see {@link IDGenerator}
   * 
   * @param externalUid
   *          the external UID of this user
   * @param name
   *          the username
   * @param description
   *          additional user information
   */
  public User(String externalUid, String name, String description) {
    super(name, description);
    this.externalUid = externalUid;
  }

  public String getExternalUid() {
    return externalUid;
  }

  public void setExternalUid(String external_uid) {
    this.externalUid = external_uid;
  }
}
