package at.tugraz.kmi.medokyservice.fca.db.usermodel;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.User;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Concept;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Course;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;

/**
 * Class Representing a Learner. Learners participate in {@link Course}s and are
 * granted read-only access to courses.
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 */
public class Learner extends User {

  /**
   * 
   */
  private static final long serialVersionUID = 6932447589319635751L;
  private Set<Long> courseIDs;
  private Set<LearnerDomain> domains;
  private LinkedHashMap<FCAObject, Float> objects;
  private LinkedHashMap<FCAAttribute, Float> attributes;

  /**
   * @see User
   */
  public Learner(String externalUid, String name, String description) {
    super(externalUid, name, description);

    courseIDs = new HashSet<Long>();
    domains = new HashSet<LearnerDomain>();
    objects = new LinkedHashMap<FCAObject, Float>();
    attributes = new LinkedHashMap<FCAAttribute, Float>();

  }

  public Set<LearnerDomain> getDomains() {
    return domains;
  }

  public Set<Long> getCoursIDs() {
    return courseIDs;
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

  public void AddAttributes(Set<Concept> concepts) {

  }
}
