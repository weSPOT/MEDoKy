package at.tugraz.kmi.medokyservice.fca.db.domainmodel;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerConcept;
import at.tugraz.kmi.medokyservice.fca.lib.colibri.lib.ComparableHashSet;
import at.tugraz.kmi.medokyservice.fca.lib.colibri.lib.ComparableSet;

/**
 * Representation of a formal concept, i.e.&nbsp;a pair of an object set and an
 * attribute set. Contains references to its predecessors, successors and
 * successors wrt. a taxonomy comprising if a subset of a formal context (
 * {@link Lattice}). Furthermore it contains a set referencing all
 * {@link LearnerConcept}s based upun this concept in order to propagate
 * updates.
 * 
 * @author Daniel N. Goetzmann
 * @author Bernd Prünster
 * @version 1.1
 */

public class Concept extends DataObject {

  private static final long serialVersionUID = -1074009257495237124L;
  private ComparableSet objects = null;
  private ComparableSet attributes = null;

  private ComparableSet uniqueObjects = null;
  private ComparableSet uniqueAttributes = null;
  private boolean objectConcept;
  private boolean attributeConcept;
  private boolean partOfTaxonomy;
  @JsonIgnore
  private Set<Concept> predecessors;
  private Set<Concept> successors;
  private Set<Concept> taxonomySuccessors;

  @JsonIgnore
  private Set<LearnerConcept> learnerConcepts;

  /**
   * Constructs a formal concept.
   * 
   * @param objects
   *          the set of objects of this concept.
   * @param attributes
   *          the set of attributes of this concept.
   * @throws IllegalArgumentException
   *           if <code>objects</code> or <code>attributes</code> is
   *           <code>null</code>.
   */
  public Concept(ComparableSet objects, ComparableSet attributes) {
    super(objects.toString(), attributes.toString());
    if (objects == null || attributes == null) {
      throw new IllegalArgumentException();
    }
    objectConcept = true;
    partOfTaxonomy = false;
    predecessors = new LinkedHashSet<Concept>();
    successors = new LinkedHashSet<Concept>();
    taxonomySuccessors = new LinkedHashSet<Concept>();
    learnerConcepts = new HashSet<LearnerConcept>();
    this.objects = new ComparableHashSet();
    this.objects = (ComparableSet) objects;
    this.attributes = (ComparableSet) attributes;

    uniqueObjects = new ComparableHashSet();
    uniqueAttributes = new ComparableHashSet();
    this.objects.disallowChanges();
    this.attributes.disallowChanges();
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
  public static void relate(Concept predecessor, Concept successor) {
    predecessor.addSuccessor(successor);
    successor.addPredecessor(predecessor);
  }

  /**
   * Adds a successor to the set of successors without creating a bidirectional
   * relation
   * 
   * @param successor
   *          the successor
   */
  public void addSuccessor(Concept successor) {
    successors.add(successor);
  }

  /**
   * Adds a predecessor to the set of predecessors without creating a
   * bidirectional relation
   * 
   * @param predecessor
   *          the predecessor
   */
  public void addPredecessor(Concept predecessor) {
    predecessors.add(predecessor);
  }

  public Set<Concept> getPredecessors() {
    synchronized (predecessors) {
      return predecessors;
    }
  }

  public Set<Concept> getSuccessors() {
    synchronized (successors) {
      return successors;
    }
  }

  /**
   * Returns the set of objects belonging to this concept.
   * 
   * @return the set of objects belonging to this concept.
   */
  public ComparableSet getObjects() {
    return objects;
  }

  @SuppressWarnings("rawtypes")
  public <T extends Comparable> void setObjects(Set<T> objects) {
    this.objects = new ComparableHashSet();
    this.objects.addAll(objects);
  }

  /**
   * Returns the set of attributes belonging to this concept.
   * 
   * @return the set of attributes belonging to this concept
   */
  public ComparableSet getAttributes() {
    return attributes;
  }

  @SuppressWarnings("rawtypes")
  public <T extends Comparable> void setAttributes(Set<T> attributes) {
    this.attributes = new ComparableHashSet();
    this.attributes.addAll(attributes);
  }

  /**
   * Returns a string representation of this concept.
   * 
   * @return a string representation of this concept
   */
  public String toString() {
    return ("objects:" + objects.toString() + ", attributes:" + attributes
        .toString());
  }

  /**
   * Compares the specified object with this concept for equality. Returns
   * {@literal true} iff the specified object is also a {@link Concept} and the
   * object set and the attribute set of this concept are equal to the object
   * set and the attribute set of the other concept, respectively.
   * 
   * @param object
   *          the object to be compared for equality with this concept.
   * @return {@literal true} iff the specified object is equal to this concept.
   */
  public boolean equals(Object object) {
    if (!(object instanceof Concept)) {
      return false;
    } else {
      if (objects == null || attributes == null) {
        return false;
      }
      return (objects.equals(((Concept) object).objects) && attributes
          .equals(((Concept) object).attributes));
    }
  }

  /**
   * Returns the hash code value for this concept.
   * <p>
   * The hash code value of a <code>Concept<code> is defined as the sum of the
   * hash code values of its object and attribute sets.
   * 
   * @return the hash code value for this concept.
   */
  public int hashCode() {
    int hash = 0;
    if (objects != null)
      hash += objects.hashCode();
    if (attributes != null)
      hash += attributes.hashCode();
    return hash;

  }

  /**
   * Indicates whether this concept has any objects associated with it
   * 
   * @return {@literal true} if the concept has any objects associated with it
   */
  public boolean isObjectConcept() {
    return objectConcept;
  }

  /**
   * Indicates whether this concept has any attributes associated with it
   * 
   * @return {@literal true} if the concept has any attributes associated with it
   */
  public boolean isAttributeConcept() {
    return attributeConcept;
  }

  /**
   * Computes a reduced labelling for this objects according to FCA
   */
  @SuppressWarnings("rawtypes")
  public void filterCharacteristics() {
    ComparableSet sucAttribs = new ComparableHashSet();
    partOfTaxonomy = false;
    for (Concept s : successors) {
      sucAttribs.addAll(s.getAttributes());
    }
    uniqueAttributes.clear();
    uniqueAttributes.addAll(attributes);
    uniqueAttributes.removeAll(sucAttribs);

    if (uniqueAttributes.isEmpty())
      attributeConcept = false;
    else{
      attributeConcept = true;
      partOfTaxonomy = true;
    }

    ComparableSet preObjects = new ComparableHashSet();
    for (Concept s : predecessors) {
      preObjects.addAll(s.getObjects());

    }
    uniqueObjects.clear();
    uniqueObjects.addAll(objects);
    uniqueObjects.removeAll(preObjects);
    if (uniqueObjects.isEmpty()) {
      objectConcept = false;
    } else {
      partOfTaxonomy = true;
      objectConcept = true;
    }

    StringBuilder name = new StringBuilder("{");
    for (Comparable o : uniqueObjects) {
      FCAObject obj = (FCAObject) o;
      name.append(" ").append(obj.getName()).append(",");
    }
    if (name.length() > 1)
      setName(name.substring(0, name.length() - 1) + " }");
    else
      setName("");
    StringBuilder desc = new StringBuilder("{");
    for (Comparable o : uniqueAttributes) {
      FCAAttribute a = (FCAAttribute) o;
      desc.append(" ").append(a.getName()).append(",");
    }
    if (desc.length() > 1)
      setDescription(desc.substring(0, desc.length() - 1) + " }");
    else
      setDescription("");
  }

  /**
   * Computes the successors wrt. the taxonomy this concept is part of. This
   * update is also populated to all {@link LearnerConcept}s this concept is
   * based upon.
   * 
   * @see Lattice
   */
  public void updateTaxonomySuccessors() {
    calculateTaxonomySuccessors();
    Set<Concept> targets = new LinkedHashSet<Concept>();
    calculateTaxonomyTargets(this, targets);
    Set<Concept> invalid = new HashSet<Concept>();
    for (Concept t : targets) {
      if (isReachable(this, t))
        invalid.add(t);
    }
    targets.removeAll(invalid);
    taxonomySuccessors.addAll(targets);
    for (LearnerConcept c : learnerConcepts) {
      c.getTaxonomySuccessors().clear();
      for (Concept t : taxonomySuccessors) {
        for (LearnerConcept s : t.learnerConcepts)
          if (c.getSuccessors().contains(s))
            c.getTaxonomySuccessors().add(s);
      }
    }
  }

  private void calculateTaxonomySuccessors() {
    taxonomySuccessors.clear();
    for (Concept c : successors) {
      if (c.isPartOfTaxonomy())
        taxonomySuccessors.add(c);
    }
  }

  private static void calculateTaxonomyTargets(Concept concept,
      Set<Concept> successors) {
    for (Concept c : concept.successors) {
      if (c.isPartOfTaxonomy()) {
        successors.add(c);
      } else {
        calculateTaxonomyTargets(c, successors);
      }
    }
  }

  private static boolean isReachable(Concept source, Concept target) {
    if (source == target)
      return true;
    if (!source.partOfTaxonomy)
      return false;
    if (source.taxonomySuccessors.contains(target))
      return true;
    for (Concept s : source.taxonomySuccessors)
      if (isReachable(s, target))
        return true;
    return false;
  }

  public ComparableSet getUniqueObjects() {
    return uniqueObjects;
  }

  public ComparableSet getUniqueAttributes() {
    return uniqueAttributes;
  }

  public Set<Concept> getTaxonomySuccessors() {
    return taxonomySuccessors;
  }

  /**
   * Disallow making changes to the sets of successors and predecessors. This
   * operation is irreversible.
   */
  public void disallowChanges() {
    successors = Collections.unmodifiableSet(successors);
    predecessors = Collections.unmodifiableSet(predecessors);
  }

  /**
   * DO NOT USE -- only for internal use!
   * 
   * @param id
   *          the new id
   * @throws Exception
   *           always -- DO NOT USE!
   */
  void setId(long id) throws Exception {
    this.id = id;
    throw new Exception("Let's hope you know what you are doing!");
  }

  /**
   * indicates whether this concept is part of the taxonomy.
   * 
   * @return {@literal true} if this concept is part of the taxonomy
   */
  public boolean isPartOfTaxonomy() {
    return partOfTaxonomy;
  }

  public void setPartOfTaxonomy(boolean partOfTaxonomy) {
    this.partOfTaxonomy = partOfTaxonomy;
  }

  /**
   * Sets the name of this concept and all linked {@link LearnerConcept}s
   */
  @Override
  public void setName(String name) {
    super.setName(name);
    for (LearnerConcept c : learnerConcepts) {
      c.setName(name);
    }
  }

  /**
   * Sets the description of this concept and all linked {@link LearnerConcept}s
   */
  @Override
  public void setDescription(String description) {
    super.setDescription(description);
    for (LearnerConcept c : learnerConcepts) {
      c.setDescription(description);
    }
  }

  public void addLearnerConcept(LearnerConcept c) {
    learnerConcepts.add(c);
  }

 

}
