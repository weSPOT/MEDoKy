package at.tugraz.kmi.medokyservice.fca.rest.wrappers;

import at.tugraz.kmi.medokyservice.fca.db.User;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Domain;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.IncidenceMatrix;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerDomain;

public class DomainWrapper extends AbstractWrapper {

  public User owner;
  public LatticeWrapper formalContext;
  public IncidenceMatrix mapping;

  public DomainWrapper() {
  }

  public DomainWrapper(Domain domain) {
    super.id = domain.getId();
    super.name = domain.getName();
    super.description = domain.getDescription();
    owner = domain.getOwner();
    formalContext = new LatticeWrapper(domain.getFormalContext());
    mapping = domain.getMapping();
  }
  public DomainWrapper(LearnerDomain domain) {
    super.id = domain.getId();
    super.name = domain.getName();
    super.description = domain.getDescription();
    owner = domain.getOwner();
    formalContext = new LatticeWrapper(domain.getFormalContext());
    mapping = domain.getMapping();
  }

}
