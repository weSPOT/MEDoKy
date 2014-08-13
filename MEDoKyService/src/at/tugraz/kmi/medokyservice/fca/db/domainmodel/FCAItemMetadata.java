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
