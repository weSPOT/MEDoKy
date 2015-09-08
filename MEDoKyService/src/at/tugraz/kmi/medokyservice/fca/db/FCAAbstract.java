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
package at.tugraz.kmi.medokyservice.fca.db;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;

/**
 * Base class of {@link FCAObject} and {@link FCAAttribute}. Those classes serve semantics. Every FCAAbstract contains a
 * set of {@link LearningObject}s
 *
 * @author Bernd Prünster <mail@berndpruenster.org>
 *
 */
public class FCAAbstract extends DataObject {

  /**
   *
   */
  private static final long   serialVersionUID = -6632084060702601592L;
  private Set<LearningObject> learningObjects;
  private Set<LearningObject> learningObjectsByLearners;

  private String              creationId;

  /**
   * @param name
   *          the objects name
   * @param description
   *          the objects description
   * @see DataObject#DataObject(String, String)
   */
  public FCAAbstract(String name, String description, String creationId) {
    super(name, description);
    this.creationId = creationId;
    learningObjects = Collections.synchronizedSet(new HashSet<LearningObject>());
    learningObjectsByLearners = Collections.synchronizedSet(new HashSet<LearningObject>());
  }

  public Set<LearningObject> getLearningObjects() {
    synchronized (learningObjects) {
      return learningObjects;
    }
  }

  public void setLearningObjects(Set<LearningObject> learningObjects) {
    if (learningObjects != null)
      this.learningObjects = Collections.synchronizedSet(learningObjects);
    else
      this.learningObjects = Collections.synchronizedSet(new HashSet<LearningObject>());
  }

  public String getCreationId() {
    return creationId;
  }

  public boolean containsLearningObject(LearningObject obj) {
    synchronized (learningObjects) {
      return learningObjects.contains(obj) || learningObjectsByLearners.contains(obj);
    }
  }

  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.writeObject(getName());
    oos.writeObject(getDescription());
    oos.writeObject(getCreationId());
    oos.writeLong(id);
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    setName((String) in.readObject());
    setDescription((String) in.readObject());
    this.creationId = (String) in.readObject();
    this.id = in.readLong();
    this.learningObjects = Collections.synchronizedSet(new HashSet<LearningObject>());
    this.learningObjectsByLearners = Collections.synchronizedSet(new HashSet<LearningObject>());
  }

  public String toDebugString() {
    StringBuilder bld = new StringBuilder(super.toString());
    bld.append("\nLearningObjects: [");
    for (LearningObject o : learningObjects)
      bld.append(o.toString()).append(", ");
    return bld.append("]").toString();

  }

  public Set<LearningObject> getLearningObjectsByLearners() {
    return learningObjectsByLearners;
  }

  public void setLearningObjectsByLearners(Set<LearningObject> learningObjectsByLearners) {
    if (learningObjectsByLearners != null)
      this.learningObjectsByLearners = Collections.synchronizedSet(learningObjectsByLearners);
    else
      this.learningObjectsByLearners = Collections.synchronizedSet(new HashSet<LearningObject>());
  }

  public Set<LearningObject> getAllLearningObjects() {
    Set<LearningObject> allLos = new HashSet<LearningObject>(learningObjectsByLearners);
    allLos.addAll(this.learningObjects);
    return allLos;
  }
}
