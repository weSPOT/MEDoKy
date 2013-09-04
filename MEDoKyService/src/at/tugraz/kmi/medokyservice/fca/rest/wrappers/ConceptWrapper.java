package at.tugraz.kmi.medokyservice.fca.rest.wrappers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Concept;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;

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

  public HashSet<FCAAttribute> attributes;
  public HashSet<FCAObject> objects;

  public HashSet<FCAObject> uniqueObjects;
  public HashSet<FCAAttribute> uniqueAttributes;

  public HashSet<ConceptWrapper> successors;
  public HashSet<ConceptWrapper> taxonomySuccessors;

  public ConceptWrapper() {
  }

  /**
   * 
   * @param concept
   *          the original {@link Concept} to wrap
   */
  @SuppressWarnings("unchecked")
  public ConceptWrapper(Concept concept) {
    super.id = concept.getId();
    super.name = concept.getName();
    super.description = concept.getDescription();

    partOfTaxonomy = concept.isPartOfTaxonomy();
    objectConcept = concept.isObjectConcept();

    attributes = new HashSet<FCAAttribute>(
        (Collection<? extends FCAAttribute>) concept.getAttributes());
    objects = new HashSet<FCAObject>(
        (Collection<? extends FCAObject>) concept.getObjects());

    uniqueAttributes = new HashSet<FCAAttribute>(
        (Collection<? extends FCAAttribute>) concept.getUniqueAttributes());
    uniqueObjects = new HashSet<FCAObject>(
        (Collection<? extends FCAObject>) concept.getUniqueAttributes());

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
