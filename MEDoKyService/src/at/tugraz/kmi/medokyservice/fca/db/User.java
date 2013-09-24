package at.tugraz.kmi.medokyservice.fca.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Concept;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;

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
  private HashMap<FCAObject, Float> objects;
  private HashMap<FCAAttribute, Float> attributes;

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
    objects = new HashMap<FCAObject, Float>();
    attributes = new HashMap<FCAAttribute, Float>();
  }

  public String getExternalUid() {
    return externalUid;
  }

  public void setExternalUid(String external_uid) {
    this.externalUid = external_uid;
  }

  public void addObjects(Set<Concept> concepts) {
    for (Concept c : concepts) {
      for (Comparable o : c.getObjects()) {
        if (!objects.containsKey(o))
          objects.put((FCAObject) o, 0f);
      }

      for (Comparable a : c.getAttributes()) {
        if (!attributes.containsKey(a))
          attributes.put((FCAAttribute) a, 0f);
      }
    }
  }

  public Map<FCAObject, Float> getLearnerObjects() {
    return objects;
  }

  public Map<FCAAttribute, Float> getLearnerAttributes() {
    return attributes;
  }

  public void setObjectValuations(Map<FCAObject, Float> valuations) {
    objects.putAll(valuations);
  }

  public void setAttributeValuations(Map<FCAAttribute, Float> valuations) {
    attributes.putAll(valuations);
  }

}
