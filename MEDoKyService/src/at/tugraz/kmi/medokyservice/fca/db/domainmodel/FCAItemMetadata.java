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
import java.util.LinkedHashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.FCAAbstract;

public class FCAItemMetadata extends DataObject {
  private static final long   serialVersionUID = 9161590397595291989L;

  private Set<LearningObject> learningObjects;
  private Set<LearningObject> learningObjectByLearner;
  private final long          itemID;

  public FCAItemMetadata(FCAAbstract obj) {
    super(null, obj.getDescription());
    this.itemID = obj.getId();
    this.learningObjects = Collections.synchronizedSet(new LinkedHashSet<LearningObject>(obj.getLearningObjects()));
    this.learningObjectByLearner = Collections.synchronizedSet(new LinkedHashSet<LearningObject>(obj
        .getLearningObjectsByLearners()));
  }

  public FCAItemMetadata(String description, long itemID, Set<LearningObject> learningObjects,
      Set<LearningObject> learningObjectsByLearners) {
    super(null, description);
    this.itemID = itemID;
    this.learningObjects = Collections.synchronizedSet(new LinkedHashSet<LearningObject>(learningObjects));
    this.learningObjectByLearner = Collections.synchronizedSet(new LinkedHashSet<LearningObject>(learningObjectsByLearners));
  }

  public long getItemID() {
    return itemID;
  }

  public Set<LearningObject> getLearningObjects() {
    return learningObjects;
  }

  public void setLearningObjects(Set<LearningObject> learningObjects) {
    this.learningObjects = learningObjects;
  }

  public Set<LearningObject> getLearningObjectByLearner() {
    return learningObjectByLearner;
  }

  public void setLearningObjectByLearner(Set<LearningObject> learningObjectByLearner) {
    this.learningObjectByLearner = learningObjectByLearner;
  }
}
