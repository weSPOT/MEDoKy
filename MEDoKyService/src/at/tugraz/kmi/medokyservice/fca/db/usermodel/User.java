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
    coursesOwned = new LinkedHashSet<Course>();
    coursesAttendingIDs = new HashSet<Long>();
  }

  public String getExternalUid() {
    return externalUid;
  }

  public void setExternalUid(String external_uid) {
    this.externalUid = external_uid;
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
