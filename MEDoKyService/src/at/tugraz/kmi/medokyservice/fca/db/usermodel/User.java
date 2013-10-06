package at.tugraz.kmi.medokyservice.fca.db.usermodel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.IDGenerator;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Concept;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Course;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;

/**
 * A User Object linked to an external database through an external UID Users
 * contain a map of objects to object valuations as well as attributes to
 * attribute valuations used to compute concept-based valuations
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 */
public class User extends DataObject {

  private static final long serialVersionUID = -6963436680153937339L;
  private String externalUid;
  private HashMap<FCAObject, Float> objects;
  private HashMap<FCAAttribute, Float> attributes;
  private Set<Course> coursesOwned;
  private Set<Long> coursesAttendingIDs;

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
    objects = new HashMap<FCAObject, Float>();
    attributes = new HashMap<FCAAttribute, Float>();
    coursesOwned = new LinkedHashSet<Course>();
    coursesAttendingIDs = new HashSet<Long>();
  }

  public String getExternalUid() {
    return externalUid;
  }

  public void setExternalUid(String external_uid) {
    this.externalUid = external_uid;
  }

  /**
   * Adds a concepts objects and attributes to the mappings of
   * objects/attributes to valuations
   * 
   * @param concepts the concepts whose objects/Attributes are to be added
   */
  @SuppressWarnings("rawtypes")
  public void addItems(Set<Concept> concepts) {
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

  public Set<Course> getCoursesOwned() {
    return coursesOwned;
  }

  /**
   * Adds a course to the set of managed courses. Sets the course's ownerId to
   * the id of this teacher.
   * 
   * @param course
   */
  public void addCourseOwned(Course course) {
    coursesOwned.add(course);
    course.setOwnerId(id);
  }

  public Set<Long> getCoursesAttendingIDs() {
    return coursesAttendingIDs;
  }

  public void addCouserAttending(long id) {
    coursesAttendingIDs.add(id);
  }
}
