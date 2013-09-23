package at.tugraz.kmi.medokyservice.fca.bl;

import java.util.HashMap;
import java.util.Map;

import at.tugraz.kmi.medokyservice.fca.db.Database;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.Learner;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerDomain;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerLattice;

public abstract class Updater {

  public static synchronized LearnerLattice update(LearnerDomain domain,
      Map<FCAObject, Float> objectValuationUpdates,
      Map<FCAAttribute, Float> attributeValuationUpdates, Learner learner)
      throws Exception {
    System.out.println(objectValuationUpdates.size());
    System.out.println(attributeValuationUpdates.size());

    HashMap<FCAAttribute, Float> aVal = new HashMap<FCAAttribute, Float>();
    for (FCAAttribute attr : learner.getLearnerAttributes().keySet()) {
      Float valuation = attributeValuationUpdates.get(attr);
      if (valuation == null) {
        System.out.println("attr");
        valuation = 0f;
      } else {
        System.out.println("attr: " + valuation);
      }
      float val = learner.getLearnerAttributes().get(attr) + valuation;
      val = (val > 1.0f ? 1.0f : val);
      val = (val < -1.0f ? -1.0f : val);
      System.out.println(val);
      aVal.put(attr, val);
    }

    HashMap<FCAObject, Float> oVal = new HashMap<FCAObject, Float>();
    for (FCAObject obj : learner.getLearnerObjects().keySet()) {
      Float valuation = objectValuationUpdates.get(obj);
      if (valuation == null)
        valuation = 0f;
      float val = learner.getLearnerObjects().get(obj) + valuation;
      val = (val > 1.0f ? 1.0f : val);
      val = (val < -1.0f ? -1.0f : val);
      System.out.println(val);
      oVal.put(obj, val);
    }
    learner.setAttributeValuations(aVal);
    learner.setObjectValuations(oVal);
    Database.getInstance().save();
    return domain.getFormalContext();

  }
}
