package at.tugraz.kmi.medokyservice.fca.db;

/**
 * A User Object linked to an external database through an external UID
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 */
public class User extends DataObject {
  /**
   * 
   */
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
   *          additiona user information
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
