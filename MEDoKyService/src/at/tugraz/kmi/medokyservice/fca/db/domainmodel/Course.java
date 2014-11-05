package at.tugraz.kmi.medokyservice.fca.db.domainmodel;

import java.util.HashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;

/**
 * Class representing a course. A Course must have an owner ({@link User}) id, a (possibly empty) set of {@link User}s
 * and a(possibly empty) set of {@link Domain}s. Only the teacher may add/remove domains and learners.
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public class Course extends DataObject {
  /**
   * 
   */
  private static final long serialVersionUID = 4916381247554634834L;
  private Set<Domain>       domains;
  private Set<User>         participants;
  // private Set<User> owners;
  private String            externalCourseID;

  /**
   * @param name
   *          the curse name
   * @param description
   *          the description of the course
   */
  public Course(String name, String description, String externalCourseID) {
    super(name, description);
    domains = new HashSet<Domain>();
    participants = new HashSet<User>();
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

  //
  // public Set<User> getOwners() {
  // return owners;
  // }
  //
  // public void setOwners(Set<User> owners) {
  // this.owners = owners;
  // }

  public String getExternalCourseID() {
    return externalCourseID;
  }

}
