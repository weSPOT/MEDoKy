package at.tugraz.kmi.medokyservice.fca.db.usermodel;

import java.util.LinkedHashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.User;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Course;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Domain;

/**
 * Represents a Teacher. Teachers can manages {@link Course}s and create
 * {@link Domain}s.
 * 
 * @see User
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 */
public class Teacher extends User {
  /**
   * 
   */
  private static final long serialVersionUID = -1768676199956425160L;
  private Set<Course> courses;

  /**
   * @see User
   * @param externalUid
   * @param name
   * @param description
   */
  public Teacher(String externalUid, String name, String description) {
    super(externalUid, name, description);
    courses = new LinkedHashSet<Course>();
  }

  public Set<Course> getCourses() {
    return courses;
  }

  /**
   * Adds a course to the set of managed courses. Sets the course's ownerId to
   * the id of this teacher.
   * 
   * @param course
   */
  public void addCourse(Course course) {
    courses.add(course);
    course.setOwnerId(id);
  }
}
