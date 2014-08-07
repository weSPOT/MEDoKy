package at.tugraz.kmi.medokyservice.fca.bl;

import java.util.HashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.Database;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerConcept;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerDomain;

/**
 * Updater class receiving indicators/updates as a consequence of a user action
 * computing valuations of concepts.
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public abstract class Updater {

  /**
   * 
   * @param domain
   *          the affected domain
   * @param source
   *          the id of the learning object responsible for the update
   * @return an updates version of the affected domain's lattice
   * @throws Exception
   *           when saving the database failed
   */
  public static synchronized void update(LearnerDomain domain, long source) throws Exception {
    domain.setMetadata();
    LearningObject clickedObject = Database.getInstance().get(source);
    for (LearnerConcept c : domain.getFormalContext().getConcepts()) {
      FCAAttribute[] domainAttrs = c.getAttributes().keySet().toArray(new FCAAttribute[c.getAttributes().size()]);
      for (FCAAttribute a : domainAttrs) {
        if (a.containsLearningObject(clickedObject)) {
          c.addClickedLearningObject(clickedObject);
          Set<LearningObject> intersection = new HashSet<LearningObject>(c.getClickedLearningObjects());
          intersection.retainAll(a.getLearningObjects());
          float valuation = ((float) (intersection.size())) / ((float) (a.getLearningObjects().size()));
          c.getAttributes().put(a, valuation);
        }
      }
      FCAObject[] domainObjs = c.getObjects().keySet().toArray(new FCAObject[c.getObjects().size()]);
      for (FCAObject o : domainObjs) {
        if (o.containsLearningObject(clickedObject)) {
          c.addClickedLearningObject(clickedObject);
          Set<LearningObject> intersection = new HashSet<LearningObject>(c.getClickedLearningObjects());
          intersection.retainAll(o.getLearningObjects());
          float valuation = ((float) (intersection.size())) / ((float) (o.getLearningObjects().size()));
          c.getObjects().put(o, valuation);
        }
      }
    }
    Database.getInstance().save();
  }

  public static synchronized void update(LearnerDomain domain) throws Exception {
    domain.setMetadata();
    for (LearnerConcept c : domain.getFormalContext().getConcepts()) {
      FCAAttribute[] domainAttrs = c.getAttributes().keySet().toArray(new FCAAttribute[c.getAttributes().size()]);
      for (FCAAttribute a : domainAttrs) {
        Set<LearningObject> intersection = new HashSet<LearningObject>(c.getClickedLearningObjects());
        intersection.retainAll(a.getLearningObjects());
        float valuation = a.getLearningObjects().isEmpty() ? 0f : ((float) (intersection.size()))
            / ((float) (a.getLearningObjects().size()));
        c.getAttributes().put(a, valuation);
      }
      FCAObject[] domainObjs = c.getObjects().keySet().toArray(new FCAObject[c.getObjects().size()]);
      for (FCAObject o : domainObjs) {
        Set<LearningObject> intersection = new HashSet<LearningObject>(c.getClickedLearningObjects());
        intersection.retainAll(o.getLearningObjects());
        float valuation = o.getLearningObjects().isEmpty() ? 0f : ((float) (intersection.size()))
            / ((float) (o.getLearningObjects().size()));
        c.getObjects().put(o, valuation);
      }
    }
    Database.getInstance().save();
  }
}
