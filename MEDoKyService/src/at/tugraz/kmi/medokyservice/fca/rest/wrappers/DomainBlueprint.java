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
package at.tugraz.kmi.medokyservice.fca.rest.wrappers;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;

/**
 * Wrapper used for serving domain metadata (name, description id) and for
 * creating domains based on a mapping of {@link FCAObject}s to
 * {@link FCAAttribute}s.
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public class DomainBlueprint extends AbstractWrapper {
  public DomainBlueprint() {
  }

  public DomainBlueprint(String name, String description, Set<User> owners, boolean approved) {
    super.name = name;
    super.description = description;
    this.owners = owners;
    this.approved = approved;
  }

  /**
   * external UID of the teacher that created this domain
   */
  public Set<String>                                   externalUIDs;

  public String                                   externalCourseID;

  public String                                   courseName;

  /**
   * the set of {@link FCAAttribute} IDs part of this domain
   */
  public LinkedHashSet<Long>                      attributes;

  /**
   * the set of {@link FCAObject} IDs part of this domain
   */
  public LinkedHashSet<Long>                      objects;

  /**
   * Mapping of {@link FCAObject} IDs to FCAAttribute IDs
   */
  public LinkedHashMap<Long, LinkedHashSet<Long>> mapping;

  /**
   * The Domain's owner/creator
   */
  public Set<User>                                owners;

  /**
   * Flag indicating whether this domain is approved
   */
  public boolean                                  approved;

}
