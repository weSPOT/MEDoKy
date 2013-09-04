package at.tugraz.kmi.medokyservice.fca.db.usermodel;

import java.util.HashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.User;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Course;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Domain;

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

  /**
   * @see User
   */
  public Learner(String externalUid, String name, String description) {
    super(externalUid, name, description);

    courseIDs = new HashSet<Long>();
    domains = new HashSet<LearnerDomain>();
  }

  public Set<LearnerDomain> getDomains() {
    return domains;
  }

  public Set<Long> getCoursIDs() {
    return courseIDs;
  }

  /**
   * Adds a {@link Course} to the set of this learners courses and also adds
   * this learner to the course's list of participants
   * 
   * @param course
   */
  public void addCourse(Course course) {
    courseIDs.add(course.getId());
    for (Domain d : course.getDomains()) {
      domains.add(new LearnerDomain(d));
    }
  }

}
