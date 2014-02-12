package at.tugraz.kmi.medokyservice.fca.bl;

import java.util.Map;

import at.tugraz.kmi.medokyservice.fca.db.Database;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerConcept;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerDomain;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerLattice;

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
   * @param objectValuationUpdates
   *          the object updates
   * @param attributeValuationUpdates
   *          the attribute updates
   * @return an updates version of the affected domain's lattice
   * @throws Exception
   *           when saving the database failed
   */
  public static synchronized LearnerLattice update(LearnerDomain domain, Map<FCAObject, Float> objectValuationUpdates,
      Map<FCAAttribute, Float> attributeValuationUpdates) throws Exception {

    for (LearnerConcept c : domain.getFormalContext().getConcepts()) {
      FCAAttribute[] domainAttrs = c.getAttributes().keySet().toArray(new FCAAttribute[c.getAttributes().size()]);
      for (FCAAttribute a : domainAttrs) {
        Float valuation = attributeValuationUpdates.get(a);
        if (valuation == null)
          valuation = 0f;
        c.getAttributes().put(a, valuation);
      }
      FCAObject[] domainObjs = c.getObjects().keySet().toArray(new FCAObject[c.getObjects().size()]);
      for (FCAObject o : domainObjs) {
        Float valuation = objectValuationUpdates.get(o);
        if (valuation == null)
          valuation = 0f;
        c.getObjects().put(o, valuation);
      }
    }
    Database.getInstance().save();
    return domain.getFormalContext();

  }
}
