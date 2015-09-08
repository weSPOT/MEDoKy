/*
 * 
 * MEDoKyService:
 * A web service component for learner modelling and learning recommendations.
 * Copyright (C) 2015 KTI, TUGraz, Contact: simone.kopeinik@tugraz.at
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Affero General Public License for more details.  
 * For more information about the GNU Affero General Public License see <http://www.gnu.org/licenses/>.
 * 
 */
package at.tugraz.kmi.medokyservice.fca.db.usermodel;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Domain;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.IncidenceMatrix;

/**
 * A LearnerDomain based upon a {@link Domain} of the domain model. Contains
 * references to the domain it is based upon and a {@link LearnerLattice}.
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public class LearnerDomain extends DataObject {

  private static final long serialVersionUID = -5008174895395567756L;
  private IncidenceMatrix   mapping;
  private User              owner;
  private LearnerLattice    formalContext;
  private long              domainID;
  private final boolean     global;

  /**
   * Creates a new LearnerDomain based upona {@link Domain}
   * 
   * @param owner
   *          the user this domain belongs to
   * 
   * @param domain
   *          the {@link Domain} it is based upon
   */
  public LearnerDomain(User owner, Domain domain) {
    super(domain.getName(), domain.getDescription());
    this.mapping = domain.getMapping();
    this.owner = owner;
    this.domainID = domain.getId();
    this.global = domain.isGlobal();
    this.formalContext = new LearnerLattice(domain.getFormalContext());
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

  public void setMetadata() {
    mapping.getAttributes();
  }

  public boolean isGlobal() {
    return global;
  }

}
