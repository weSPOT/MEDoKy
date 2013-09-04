package at.tugraz.kmi.medokyservice.fca.db.usermodel;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Concept;

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
  private float probability;

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
  public LearnerConcept(Concept c) {
    super(c.getName(), c.getDescription());
    domainConceptId = c.getId();
    probability = 0f;
    predecessors = new LinkedHashSet<LearnerConcept>();
    successors = new LinkedHashSet<LearnerConcept>();
    taxonomySuccessors = new LinkedHashSet<LearnerConcept>();
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
    predecessor.successors.add(successor);
    successor.predecessors.add(predecessor);
  }

  public float getProbability() {
    return probability;
  }

  public void setProbability(float probability) {
    this.probability = probability;
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

}
