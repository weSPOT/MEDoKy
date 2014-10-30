package at.tugraz.kmi.medokyservice.fca.rest.wrappers;

import java.util.HashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Domain;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.IncidenceMatrix;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerDomain;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;

/**
 * Wrapper user for (de-)serializing a domain from/to JSON
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public class DomainWrapper extends AbstractWrapper {

  public Set<User> owners;
  public LatticeWrapper formalContext;
  public IncidenceMatrix mapping;
  public boolean global;
  public boolean approved;

  public DomainWrapper() {
  }

  public DomainWrapper(Domain domain) {
    super.id = domain.getId();
    super.name = domain.getName();
    super.description = domain.getDescription();
    owners = domain.getOwners();
    formalContext = new LatticeWrapper(domain.getFormalContext());
    mapping = domain.getMapping();
    global=domain.isGlobal();
    approved=domain.isApproved();
    
  }

  public DomainWrapper(LearnerDomain domain) {
    super.id = domain.getId();
    super.name = domain.getName();
    super.description = domain.getDescription();
    owners = new HashSet<User>();
    owners.add(domain.getOwner());
    formalContext = new LatticeWrapper(domain.getFormalContext());
    mapping = domain.getMapping();
    System.out.println(domain.isGlobal());
    global=domain.isGlobal();
  }

}
