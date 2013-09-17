package at.tugraz.kmi.medokyservice.fca.bl;

import java.util.HashMap;
import java.util.Map;

import at.tugraz.kmi.medokyservice.fca.db.Database;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerConcept;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerDomain;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerLattice;

public abstract class Updater {

  public static synchronized LearnerLattice update(long learnerDomainId,
      Map<FCAObject, Float> objectValuationUpdates,
      Map<FCAAttribute, Float> attributeValuationUpdates) throws Exception {

    LearnerDomain domain = Database.getInstance().get(learnerDomainId);
    LearnerLattice lattice = domain.getLattice();
    for (LearnerConcept c : lattice.getConcepts()) {
      HashMap<FCAAttribute, Float> aVal = new HashMap<FCAAttribute, Float>();
      for (FCAAttribute attr : c.getAttributes().keySet()) {
        float val = c.getAttributes().get(attr)
            + attributeValuationUpdates.get(attr);
        val = (val > 1.0f ? 1.0f : val);
        val = (val < -1.0f ? -1.0f : val);
        aVal.put(attr, val);
      }

      HashMap<FCAObject, Float> oVal = new HashMap<FCAObject, Float>();
      for (FCAObject obj : c.getObjects().keySet()) {
        float val = c.getObjects().get(obj) + objectValuationUpdates.get(obj);
        val = (val > 1.0f ? 1.0f : val);
        val = (val < -1.0f ? -1.0f : val);
        oVal.put(obj, val);
      }
      c.setAttributeValuations(aVal);
      c.setObjectValuations(oVal);
    }
    Database.getInstance().save();
    return lattice;

  }
}
