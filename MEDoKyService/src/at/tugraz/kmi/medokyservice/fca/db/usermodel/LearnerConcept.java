package at.tugraz.kmi.medokyservice.fca.db.usermodel;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.User;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Concept;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;

/**
 * A LearnerConcept linked to a {@link Concept} of the domain model. TODO:
 * probability
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 */
public class LearnerConcept extends DataObject {
  /**
   * 
   */
  private static final long serialVersionUID = -1379190857847955644L;
  private long domainConceptId;

  @JsonIgnore
  private Set<LearnerConcept> predecessors;
  private Set<LearnerConcept> successors;
  private Set<LearnerConcept> taxonomySuccessors;
  private Set<FCAObject> objects;
  private Set<FCAAttribute> attributes;
  @JsonIgnore
  private User learner;

  /**
   * Creates a new LearnerConcept based upon a {@link Concept} of the domain
   * model
   * 
   * @param c
   *          the {@link Concept} this LearnerConcept is based upon
   */
  @SuppressWarnings("rawtypes")
  public LearnerConcept(Concept c, User learner) {
    super(c.getName(), c.getDescription());
    domainConceptId = c.getId();
    this.learner = learner;
    objects = new LinkedHashSet<FCAObject>();
    attributes = new LinkedHashSet<FCAAttribute>();

    for (Comparable o : c.getObjects()) {
      objects.add((FCAObject) o);

    }

    for (Comparable a : c.getAttributes()) {
      attributes.add((FCAAttribute) a);
    }

    predecessors = new LinkedHashSet<LearnerConcept>();
    successors = new LinkedHashSet<LearnerConcept>();
    taxonomySuccessors = new LinkedHashSet<LearnerConcept>();
    c.addLearnerConcept(this);
  }

  /**
   * Creates a bidirectional relation between the {@literal predecessor} and the
   * {@literal successor}
   * 
   * @param predecessor
   *          the predecessor
   * @param successor
   *          the successor
   */
  public static void relate(LearnerConcept predecessor, LearnerConcept successor) {
    predecessor.addSuccessor(successor);
    successor.addPredecessor(predecessor);
  }

  private void addPredecessor(LearnerConcept predecessor) {
    predecessors.add(predecessor);

  }

  private void addSuccessor(LearnerConcept successor) {
    successors.add(successor);

  }

  public Map<FCAObject, Float> getObjects() {
    LinkedHashMap<FCAObject, Float> result = new LinkedHashMap<FCAObject, Float>();
    Map<FCAObject, Float> obj = learner.getLearnerObjects();
    for (FCAObject o : objects) {
      result.put(o, obj.get(o));
    }
    return result;
  }

  public Map<FCAAttribute, Float> getAttributes() {
    LinkedHashMap<FCAAttribute, Float> result = new LinkedHashMap<FCAAttribute, Float>();
    Map<FCAAttribute, Float> attr = learner.getLearnerAttributes();
    for (FCAAttribute a : attributes) {
      result.put(a, attr.get(a));
    }
    return result;
  }

  public float[] getPercentagedValuations() {
    Map<FCAObject, Float> objects = getObjects();
    Map<FCAAttribute, Float> attributes = getAttributes();
    float attr = 0f, obj = 0f;
    for (float f : objects.values())
      obj += f;
    for (float f : attributes.values())
      attr += f;
    obj = objects.isEmpty() ? obj : obj / (float) objects.size();
    attr = attributes.isEmpty() ? attr : attr / (float) attributes.size();
    float[] result = { obj, attr };
    return result;
  }

  public Set<LearnerConcept> getPredecessors() {
    return predecessors;
  }

  public Set<LearnerConcept> getSuccessors() {
    return successors;
  }

  public Set<LearnerConcept> getTaxonomySuccessors() {
    return taxonomySuccessors;
  }

  public long getDomainConceptId() {
    return domainConceptId;
  }

  /**
   * Disallow making changes to the sets of successors and predecessors. This
   * operation is irreversible.
   * 
   */
  public void disallowChanges() {
    successors = Collections.unmodifiableSet(successors);
    predecessors = Collections.unmodifiableSet(predecessors);
  }

  public String toString() {
    return Long.toString(getId());
  }

}
