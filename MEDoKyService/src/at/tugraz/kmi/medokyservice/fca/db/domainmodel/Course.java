package at.tugraz.kmi.medokyservice.fca.db.domainmodel;

import java.util.HashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.Learner;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerDomain;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.Teacher;

/**
 * Class representing a course. A Course must have an owner ({@link Teacher})
 * id, a (possibly empty) set of {@link Learner}s and a(possibly empty) set of
 * {@link Domain}s. Only the teacher may add/remove domains and learners.
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 */
public class Course extends DataObject {
  /**
   * 
   */
  private static final long serialVersionUID = 4916381247554634834L;
  private Set<Domain> domains;
  private Set<Learner> participants;
  private long ownerId;

  /**
   * @param name
   *          the curse name
   * @param description
   *          the description of the course
   * @param owner
   *          The teacher responsible for this course
   */
  public Course(String name, String description, long ownerId) {
    super(name, description);
    domains = new HashSet<Domain>();
    participants = new HashSet<Learner>();
    this.ownerId = ownerId;
  }

  public Set<Domain> getDomains() {
    return domains;
  }

  /**
   * Adds a domain to the course
   * 
   * @param domain
   *          the domain to add
   */
  public void addDomain(Domain domain) {
    domains.add(domain);
    

  }

  public Set<Learner> getParticipants() {
    return participants;
  }

  /**
   * adds a learner to the course
   * 
   * @param learner
   *          the learner to add
   */
  public void addParticipant(Learner learner) {
    participants.add(learner);
  }

  public long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(long owner) {
    this.ownerId = owner;
  }

}
