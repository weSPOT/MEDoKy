package at.tugraz.kmi.medokyservice.fca.db.domainmodel;

import java.util.HashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;

/**
 * Class representing a course. A Course must have an owner ({@link User}) id, a
 * (possibly empty) set of {@link User}s and a(possibly empty) set of
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
  private Set<User> participants;
  private long ownerId;
  private String externalCourseID;

  /**
   * @param name
   *          the curse name
   * @param description
   *          the description of the course
   * @param ownerId
   *          The id of the user (teacher) responsible for this course
   */
  public Course(String name, String description, long ownerId,
      String externalCourseID) {
    super(name, description);
    domains = new HashSet<Domain>();
    participants = new HashSet<User>();
    this.ownerId = ownerId;
    this.externalCourseID = externalCourseID;
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

  public Set<User> getParticipants() {
    return participants;
  }

  /**
   * adds a learner to the course
   * 
   * @param participant
   *          the learner to add
   */
  public void addParticipant(User participant) {
    participants.add(participant);
  }

  public long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(long owner) {
    this.ownerId = owner;
  }

  public String getExternalCourseID() {
    return externalCourseID;
  }

}
