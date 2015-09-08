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
package at.tugraz.kmi.medokyservice.fca.db.domainmodel;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerDomain;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;

/**
 * Class representing a Knowledge Domain. Every Domain has an owner an {@link IncidenceMatrix} and a {@link Lattice}
 * computed from the IncidenceMatrix.
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public class Domain extends DataObject {

  /**
   * 
   */
  private static final long                   serialVersionUID = -9169088905113687870L;
  private IncidenceMatrix                     mapping;
  private Lattice                             formalContext;
  private Set<User>                           owners;
  private boolean                             global;
  private boolean                             approved;

  @JsonIgnore
  private Map<Long, Map<Long, LearnerDomain>> learnerDomains;

  /**
   * Creates a new domain form an {@link IncidenceMatrix} computing a {@link Lattice}.
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
  public Domain(String name, String description, IncidenceMatrix matrix, User owner, boolean global) {
    super(name, description);
    mapping = matrix;
    formalContext = new Lattice(matrix);
    owners = new HashSet<User>();
    owners.add(owner);
    this.global = global;
    this.approved = false;
    this.learnerDomains = Collections.synchronizedMap(new HashMap<Long, Map<Long, LearnerDomain>>());
  }

  public Domain(String name, String description, IncidenceMatrix matrix, Set<User> owner, boolean global) {
    super(name, description);
    mapping = matrix;
    formalContext = new Lattice(matrix);
    owners = new HashSet<User>();
    owners.addAll(owner);
    this.global = global;
    this.approved = false;
    this.learnerDomains = Collections.synchronizedMap(new HashMap<Long, Map<Long, LearnerDomain>>());
  }

  /**
   * Returns the {@link IncidenceMatrix} the domain is based upon
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

  public Set<User> getOwners() {
    return owners;
  }

  public void setOwners(Set<User> owners) {
    this.owners = owners;
  }

  //TODO Test this method
  public Map<Long, Map<Long, LearnerDomain>> getLearnerDomains() {
	Map<Long, Map<Long, LearnerDomain>> filteredLearnerDomains = new LinkedHashMap<Long, Map<Long,LearnerDomain>>();  
	for (Map.Entry<Long,Map<Long, LearnerDomain>> entry  : this.learnerDomains.entrySet()){
		Map<Long, LearnerDomain> domains = new LinkedHashMap<Long, LearnerDomain>();
		Set<Long> learners = this.filterAdmins(entry.getValue().keySet());
		for (Long learner : learners){
			domains.put(learner, entry.getValue().get(learner));
		}
		filteredLearnerDomains.put(entry.getKey(), domains);
	}
   	return filteredLearnerDomains;
  }

  public boolean containsLearnerDomain(long userID, long courseID) {
    return learnerDomains.containsKey(courseID) && learnerDomains.get(courseID).containsKey(userID);
  }

  public LearnerDomain getLearnerDomain(long userID, long courseID) {
    return learnerDomains.get(courseID).get(userID);
  }

  public void addLearnerDomain(long userID, long courseID, LearnerDomain domain) {
    if (!learnerDomains.containsKey(courseID))
      learnerDomains.put(courseID, new HashMap<Long, LearnerDomain>());
    learnerDomains.get(courseID).put(userID, domain);

  }

  private Set<Long> filterAdmins(Set<Long> learnerIDs) {
    Set<Long> adminIDs = new HashSet<Long>();
    for (User u : owners)
      adminIDs.add(u.getId());
    learnerIDs.removeAll(adminIDs);
    return learnerIDs;
  }

  public Set<Long> getLearnerIDs() {
    Set<Long> result = new HashSet<Long>();
    for (Map<Long, LearnerDomain> d : learnerDomains.values()) {
      result.addAll(d.keySet());
    }
    return filterAdmins(result);
  }

  public boolean isGlobal() {
    return global;
  }

  public boolean isApproved() {
    return approved;
  }

  public void setApproved(boolean approved) {
    this.approved = approved;
  }

  public void setMetadata() {
    mapping.getAttributes();
  }

  public void updateLattice() {
    formalContext = new Lattice(getMapping());
  }

}
