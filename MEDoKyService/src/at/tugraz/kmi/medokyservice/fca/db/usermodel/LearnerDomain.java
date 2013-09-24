package at.tugraz.kmi.medokyservice.fca.db.usermodel;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.User;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Domain;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.IncidenceMatrix;

/**
 * A LearnerDomain based upon a {@link Domain} of the domain model. Contains
 * references to the domain it is based upon and a {@link LearnerLattice}.
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 */
public class LearnerDomain extends DataObject {

  /**
   * 
   */
  private static final long serialVersionUID = -5008174895395567756L;
  private IncidenceMatrix mapping;
  private User owner;
  private LearnerLattice formalContext;
  private long domainID;

  /**
   * Creates a new LearnerDomain based upona {@link Domain}
   * 
   * @param domain
   *          the {@link Domain} it is based upon
   */
  public LearnerDomain(User owner, Domain domain) {
    super(domain.getName(), domain.getDescription());
    this.mapping = domain.getMapping();
    this.owner = owner;
    this.domainID = domain.getId();
    this.formalContext = new LearnerLattice(domain.getFormalContext(), owner);
  }

  public LearnerLattice getFormalContext() {
    return formalContext;
  }

  public IncidenceMatrix getMapping() {
    return mapping;
  }

  public User getOwner() {
    return owner;
  }

  public long getDomainID() {
    return domainID;
  }

}
