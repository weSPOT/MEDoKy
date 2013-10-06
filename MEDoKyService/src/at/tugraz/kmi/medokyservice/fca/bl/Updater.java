package at.tugraz.kmi.medokyservice.fca.bl;

import java.util.HashMap;
import java.util.Map;

import at.tugraz.kmi.medokyservice.fca.db.Database;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerDomain;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerLattice;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;

/**
 * Updater class receiving indicators/updates as a consequence of a user action
 * computing valuations of concepts.
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
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
   * @param user
   *          the affected user
   * @return an updates version of the affected domain's lattice
   * @throws Exception
   *           when saving the database failed
   */
  public static synchronized LearnerLattice update(LearnerDomain domain,
      Map<FCAObject, Float> objectValuationUpdates,
      Map<FCAAttribute, Float> attributeValuationUpdates, User user)
      throws Exception {
    System.out.println(objectValuationUpdates.size());
    System.out.println(attributeValuationUpdates.size());

    HashMap<FCAAttribute, Float> aVal = new HashMap<FCAAttribute, Float>();
    for (FCAAttribute attr : user.getLearnerAttributes().keySet()) {
      Float valuation = attributeValuationUpdates.get(attr);
      if (valuation == null) {
        System.out.println("attr");
        valuation = 0f;
      } else {
        System.out.println("attr: " + valuation);
      }
      float val = user.getLearnerAttributes().get(attr) + valuation;
      val = (val > 1.0f ? 1.0f : val);
      val = (val < -1.0f ? -1.0f : val);
      System.out.println(val);
      aVal.put(attr, val);
    }

    HashMap<FCAObject, Float> oVal = new HashMap<FCAObject, Float>();
    for (FCAObject obj : user.getLearnerObjects().keySet()) {
      Float valuation = objectValuationUpdates.get(obj);
      if (valuation == null)
        valuation = 0f;
      float val = user.getLearnerObjects().get(obj) + valuation;
      val = (val > 1.0f ? 1.0f : val);
      val = (val < -1.0f ? -1.0f : val);
      System.out.println(val);
      oVal.put(obj, val);
    }
    user.setAttributeValuations(aVal);
    user.setObjectValuations(oVal);
    Database.getInstance().save();
    return domain.getFormalContext();

  }
}
