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

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;

/**
 * A Learning object referencing an external resource
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public class LearningObject extends DataObject {

  /**
   * 
   */
  private static final long serialVersionUID = -773144330881908191L;
  private User              owner;
  private String            data;

  /**
   * Creates a new learning object referencing the resource contained in the
   * {@literal data} parameter
   * 
   * @param name
   *          the Learning objects name
   * @param description
   *          description of this learning object
   * @param data
   *          a string containing a reference to an external resource
   * @param owner
   *          the owner (creator) of this learning object
   */
  public LearningObject(String name, String description, String data, User owner) {
    super(name, description);
    this.owner = owner;
    this.data = data;
  }

  public User getOwner() {
    return owner;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

}
