package at.tugraz.kmi.medokyservice.fca.rest.wrappers;

import java.util.HashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;

public class ValuationWrapper {

  public float[]   valuations;
  public Set<Long> clickedLearningObjects;

  public ValuationWrapper() {
  }

  public ValuationWrapper(float[] valuations, Set<LearningObject> clickedLearningObjects) {
    this.valuations = valuations;
    this.clickedLearningObjects = new HashSet<Long>();
    for (LearningObject o : clickedLearningObjects) {
      this.clickedLearningObjects.add(o.getId());
    }
  }
}
