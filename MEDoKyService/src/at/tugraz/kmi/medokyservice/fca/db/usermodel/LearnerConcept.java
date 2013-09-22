package at.tugraz.kmi.medokyservice.fca.db.usermodel;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
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
  private LinkedHashMap<FCAObject, Float> objects;
  private LinkedHashMap<FCAAttribute, Float> attributes;

  @JsonIgnore
  private Set<LearnerConcept> predecessors;
  private Set<LearnerConcept> successors;
  private Set<LearnerConcept> taxonomySuccessors;

  /**
   * Creates a new LearnerConcept based upon a {@link Concept} of the domain
   * model
   * 
   * @param c
   *          the {@link Concept} this LearnerConcept is based upon
   */
  @SuppressWarnings("rawtypes")
  public LearnerConcept(Concept c) {
    super(c.getName(), c.getDescription());
    domainConceptId = c.getId();

    objects = new LinkedHashMap<FCAObject, Float>();
    for (Comparable o : c.getObjects()) {
      objects.put((FCAObject) o, 0f);
    }

    attributes = new LinkedHashMap<FCAAttribute, Float>();
    for (Comparable a : c.getAttributes()) {
      attributes.put((FCAAttribute) a, 0f);
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

  public HashMap<FCAObject, Float> getObjects() {
    return objects;
  }

  public HashMap<FCAAttribute, Float> getAttributes() {
    return attributes;
  }

  public void setAttributeValuations(
      Map<FCAAttribute, Float> attributeValuations) throws Exception {
    if (!(this.attributes.keySet().containsAll(attributeValuations.keySet()))
        || !(attributeValuations.keySet().containsAll(this.attributes.keySet())))
      throw new Exception("Invalid Valuations!");
    this.attributes.putAll(attributeValuations);
  }

  public void setObjectValuations(Map<FCAObject, Float> objectValuations)
      throws Exception {
    if (!(this.objects.keySet().containsAll(objectValuations.keySet()))
        || !(objectValuations.keySet().containsAll(this.objects.keySet())))
      throw new Exception("Invalid Valuations!");
    this.objects.putAll(objectValuations);
  }

  public float[] getPercentagedValuations() {
    float attr = 0f, obj = 0f;
    for (float f : objects.values())
      obj += f;
    for (float f : attributes.values())
      attr += f;
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
