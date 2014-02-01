package at.tugraz.kmi.medokyservice.fca.db.domainmodel;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;

/**
 * A Learning object referencing an external resource
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public class LearningObject extends DataObject {

  /**
   * 
   */ 
  private static final long serialVersionUID = -773144330881908191L;
  private User owner;
  private String data;

  /**
   * Creates a new learning object referencing the resource contained in the
   * {@literal data} parameter
   * 
   * @param name
   *          the Learning objects name
   * @param description
   *          description of this learning object
   * @param data
   *          a string containing a reference to an external resource
   * @param owner
   *          the owner (creator) of this learning object
   */
  public LearningObject(String name, String description, String data, User owner) {
    super(name, description);
    this.owner = owner;
    this.data = data;
  }

  public User getOwner() {
    return owner;
  }

  public String getData() {
    return data;
  }

  public void setUri(String data) {
    this.data = data;
  }

}
