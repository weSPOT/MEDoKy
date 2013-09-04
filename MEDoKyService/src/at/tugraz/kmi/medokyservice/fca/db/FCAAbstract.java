package at.tugraz.kmi.medokyservice.fca.db;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;

/**
 * Base class of {@link FCAObject} and {@link FCAAttribute}. Those classes serve
 * semantics. Every FCAAbstract contains a Set of {@link LearningObject}s
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 */
public class FCAAbstract extends DataObject {

  /**
   * 
   */
  private static final long serialVersionUID = -6632084060702601592L;
  private Set<LearningObject> learningObjects;

  /**
   * @param name
   *          the objects name
   * @param description
   *          the objects description
   * @see DataObject#DataObject(String, String)
   */
  public FCAAbstract(String name, String description) {
    super(name, description);
    learningObjects = Collections
        .synchronizedSet(new HashSet<LearningObject>());
  }

  public Set<LearningObject> getLearningObjects() {
    synchronized (learningObjects) {
      return learningObjects;
    }
  }

  public void setLearningObjects(Set<LearningObject> learningObjects) {
    this.learningObjects = Collections.synchronizedSet(learningObjects);
  }
}
