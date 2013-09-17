package at.tugraz.kmi.medokyservice.fca.rest.wrappers;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.Database;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Concept;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerConcept;

/**
 * Wrapper class resembling a {@link Concept} but without cyclic or nested
 * references used by {@link LatticeWrapper}.
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * @see LatticeWrapper
 * @see Concept
 */
public class ConceptWrapper extends AbstractWrapper {
  public long domainId;

  public boolean partOfTaxonomy;
  public boolean objectConcept;

  public LinkedHashMap<FCAAttribute, Float> attributes;
  public LinkedHashMap<FCAObject, Float> objects;

  public LinkedHashSet<FCAObject> uniqueObjects;
  public LinkedHashSet<FCAAttribute> uniqueAttributes;

  public HashSet<ConceptWrapper> successors;
  public HashSet<ConceptWrapper> taxonomySuccessors;

  public ConceptWrapper() {
  }

  /**
   * 
   * @param concept
   *          the original {@link Concept} to wrap
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public ConceptWrapper(Concept concept) {
    super.id = concept.getId();
    super.name = concept.getName();
    super.description = concept.getDescription();

    partOfTaxonomy = concept.isPartOfTaxonomy();
    objectConcept = concept.isObjectConcept();

    attributes = new LinkedHashMap<FCAAttribute, Float>();
    for (Comparable a : concept.getAttributes()) {
      attributes.put((FCAAttribute) a, 0.0f);
    }

    objects = new LinkedHashMap<FCAObject, Float>();
    for (Comparable o : concept.getObjects()) {
      objects.put((FCAObject) o, 0.0f);
    }
    uniqueAttributes = new LinkedHashSet<FCAAttribute>(
        (Collection<? extends FCAAttribute>) concept.getUniqueAttributes());
    uniqueObjects = new LinkedHashSet<FCAObject>(
        (Collection<? extends FCAObject>) concept.getUniqueAttributes());

    successors = new HashSet<ConceptWrapper>();
    taxonomySuccessors = new HashSet<ConceptWrapper>();
  }

  @SuppressWarnings("unchecked")
  public ConceptWrapper(LearnerConcept concept) {
    super.id = concept.getId();
    super.name = concept.getName();
    super.description = concept.getDescription();
    Concept c = Database.getInstance().get(concept.getDomainConceptId());
    partOfTaxonomy = c.isPartOfTaxonomy();
    objectConcept = c.isObjectConcept();

    attributes = new LinkedHashMap<FCAAttribute, Float>(concept.getAttributes());
    objects = new LinkedHashMap<FCAObject, Float>(concept.getObjects());

    uniqueAttributes = new LinkedHashSet<FCAAttribute>(
        (Collection<? extends FCAAttribute>) c.getUniqueAttributes());
    uniqueObjects = new LinkedHashSet<FCAObject>(
        (Collection<? extends FCAObject>) c.getUniqueAttributes());

    successors = new HashSet<ConceptWrapper>();
    taxonomySuccessors = new HashSet<ConceptWrapper>();
  }

  /**
   * Creates references successor Concepts
   * 
   * @param successors
   *          this concept's successors
   * @param taxonomySuccessors
   *          successors also part of the taxonomy
   */
  void setSucessors(Set<Concept> successors, Set<Concept> taxonomySuccessors) {
    for (Concept s : successors) {
      this.successors.add(new ConceptWrapper(s));
    }
    for (Concept s : taxonomySuccessors) {
      this.taxonomySuccessors.add(new ConceptWrapper(s));
    }
  }

}
