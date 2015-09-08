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
