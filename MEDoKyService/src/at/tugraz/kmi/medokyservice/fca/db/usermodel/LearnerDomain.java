package at.tugraz.kmi.medokyservice.fca.db.usermodel;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Domain;

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
  private Domain domain;
  private LearnerLattice lattice;

  /**
   * Creates a new LearnerDomain based upona {@link Domain}
   * 
   * @param domain
   *          the {@link Domain} it is based upon
   */
  public LearnerDomain(Domain domain) {
    super(domain.getName(), domain.getDescription());
    this.domain = domain;
    this.lattice = new LearnerLattice(domain.getFormalContext());
  }

  public Domain getDomain() {
    return domain;
  }

  public LearnerLattice getLattice() {
    return lattice;
  }

}
