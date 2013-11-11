package at.tugraz.kmi.medokyservice.fca.rest.wrappers;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.Database;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Concept;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerConcept;

/**
 * Wrapper class resembling a {@link Concept} or a {@link LearnerConcept} but
 * without cyclic or nested references used by {@link LatticeWrapper}.
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * @see LatticeWrapper
 * @see Concept
 */
public class ConceptWrapper extends AbstractWrapper {
  /**
   * ID of the domain the concept belongs to
   */
  public long domainId;

  /**
   * flag indicating whether the concept is part of the taxonomy
   */
  public boolean partOfTaxonomy;

  /**
   * flag indicating whether the concept is an object concept
   */
  public boolean objectConcept;

  /**
   * flag indicating whether the concept is an attribute concept
   */
  public boolean attributeConcept;

  /**
   * attributes with valuations mapped to them
   */
  public LinkedHashMap<FCAAttribute, Float> attributes;

  /**
   * objects with valuations mapped to them
   */
  public LinkedHashMap<FCAObject, Float> objects;

  /**
   * @see Concept
   */
  public LinkedHashSet<FCAObject> uniqueObjects;

  /**
   * @see Concept
   */
  public LinkedHashSet<FCAAttribute> uniqueAttributes;

  /**
   * @see Concept
   */
  public HashSet<ConceptWrapper> successors;

  /**
   * @see Concept
   */
  public HashSet<ConceptWrapper> taxonomySuccessors;

  /**
   * @see LearnerConcept
   */
  public float[] valuations = { 0f, 0f };

  public ConceptWrapper() {
  }

  public <E extends DataObject> ConceptWrapper(E concept) {
    super.id = concept.getId();
    super.name = concept.getName();
    super.description = concept.getDescription();
    if (concept instanceof Concept)
      init((Concept) concept);
    else if (concept instanceof LearnerConcept)
      init((LearnerConcept) concept);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private void init(Concept concept) {

    partOfTaxonomy = concept.isPartOfTaxonomy();
    objectConcept = concept.isObjectConcept();
    attributeConcept = concept.isAttributeConcept();

    attributes = new LinkedHashMap<FCAAttribute, Float>();
    for (Comparable a : concept.getAttributes()) {
      attributes.put((FCAAttribute) a, 0.0f);
    }

    objects = new LinkedHashMap<FCAObject, Float>();
    for (Comparable o : concept.getObjects()) {
      objects.put((FCAObject) o, 0.0f);
    }
    uniqueAttributes = new LinkedHashSet<FCAAttribute>();
    uniqueAttributes.addAll((Collection<? extends FCAAttribute>) concept
        .getUniqueAttributes());

    uniqueObjects = new LinkedHashSet<FCAObject>();
    uniqueObjects.addAll((Collection<? extends FCAObject>) concept
        .getUniqueObjects());

    successors = new HashSet<ConceptWrapper>();
    taxonomySuccessors = new HashSet<ConceptWrapper>();

  }

  @SuppressWarnings("unchecked")
  private void init(LearnerConcept concept) {

    Concept c = Database.getInstance().get(concept.getDomainConceptId());
    partOfTaxonomy = c.isPartOfTaxonomy();
    objectConcept = c.isObjectConcept();
    attributeConcept = c.isAttributeConcept();

    attributes = new LinkedHashMap<FCAAttribute, Float>(concept.getAttributes());
    objects = new LinkedHashMap<FCAObject, Float>(concept.getObjects());

    uniqueAttributes = new LinkedHashSet<FCAAttribute>();
    uniqueAttributes.addAll((Collection<? extends FCAAttribute>) c
        .getUniqueAttributes());

    uniqueObjects = new LinkedHashSet<FCAObject>();
    uniqueObjects
        .addAll((Collection<? extends FCAObject>) c.getUniqueObjects());

    successors = new HashSet<ConceptWrapper>();
    taxonomySuccessors = new HashSet<ConceptWrapper>();
    valuations = concept.getPercentagedValuations();
    System.out.println(valuations[0] + ", " + valuations[1]);
  }

  /**
   * Creates references successor Concepts
   * 
   * @param <E>
   * 
   * @param successors
   *          this concept's successors
   * @param taxonomySuccessors
   *          successors also part of the taxonomy
   */
  <E extends DataObject> void setSucessors(Set<E> successors,
      Set<E> taxonomySuccessors) {
    for (E s : successors) {
      this.successors.add(new ConceptWrapper(s));
    }
    for (E s : taxonomySuccessors) {
      this.taxonomySuccessors.add(new ConceptWrapper(s));
    }
  }

}
