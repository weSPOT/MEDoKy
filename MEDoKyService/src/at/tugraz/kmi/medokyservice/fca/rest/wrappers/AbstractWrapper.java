package at.tugraz.kmi.medokyservice.fca.rest.wrappers;

import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;

/**
 * Base class for all wrappers used by the FCA REST inteface
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public abstract class AbstractWrapper {
  public String name, description;
  public long id;
  public long domainID;
  public Set<LearningObject> learningObjects;

}
