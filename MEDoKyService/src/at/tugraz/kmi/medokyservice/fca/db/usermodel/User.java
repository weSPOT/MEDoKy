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

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.IDGenerator;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Course;

/**
 * A User Object linked to an external database through an external UID Users
 * contain a map of objects to object valuations as well as attributes to
 * attribute valuations used to compute concept-based valuations
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public class User extends DataObject {

  private static final long serialVersionUID = -6963436680153937339L;
  private String externalUid;

  /**
   * Creates a new User with a unique id (see {@link IDGenerator}
   * 
   * @param externalUid
   *          the external UID of this user
   * @param name
   *          the username
   * @param description
   *          additional user information
   */
  public User(String externalUid, String name, String description) {
    super(name, description);
    this.externalUid = externalUid;
  }

  public String getExternalUid() {
    return externalUid;
  }

  public void setExternalUid(String external_uid) {
    this.externalUid = external_uid;
  }
}
