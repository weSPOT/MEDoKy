package at.tugraz.kmi.medokyservice.fca.db.domainmodel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerDomain;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;

/**
 * Class representing a Knowledge Domain. Every Domain has an owner an
 * {@link IncidenceMatrix} and a {@link Lattice} computed from the
 * IncidenceMatrix.
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 */
public class Domain extends DataObject {

  /**
   * 
   */
  private static final long serialVersionUID = -9169088905113687870L;
  private IncidenceMatrix mapping;
  private Lattice formalContext;
  private User owner;
  private boolean global;
  @JsonIgnore
  private Map<Long, LearnerDomain> learnerDomains;

  /**
   * Creates a new domain form an {@link IncidenceMatrix} computing a
   * {@link Lattice}.
   * 
   * @param name
   *          The domain name
   * @param description
   *          the domain description
   * @param matrix
   *          the {@link IncidenceMatrix} the domain is based upon.
   * @param owner
   *          the domain owner
   */
  public Domain(String name, String description, IncidenceMatrix matrix,
      User owner, boolean global) {
    super(name, description);
    mapping = matrix;
    formalContext = new Lattice(matrix);
    this.owner = owner;
    this.global=global;
    this.learnerDomains = Collections
        .synchronizedMap(new HashMap<Long, LearnerDomain>());
  }

  /**
   * Returns the {@link IncidenceMatrix} teh domain is based upon
   * 
   * @return The {@link IncidenceMatrix}
   */
  public IncidenceMatrix getMapping() {
    return mapping;
  }

  /**
   * Returns the {@link Lattice} of this domain
   * 
   * @return the lattice of the domain
   */
  public Lattice getFormalContext() {
    return formalContext;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public Map<Long, LearnerDomain> getLearnerDomains() {
    return learnerDomains;
  }

  public void addLearnerDomain(long userID, LearnerDomain domain) {
    learnerDomains.put(userID, domain);
  }

  public boolean isGlobal() {
    return global;
  }

}
