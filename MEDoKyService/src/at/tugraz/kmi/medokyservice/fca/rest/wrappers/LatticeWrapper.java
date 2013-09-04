package at.tugraz.kmi.medokyservice.fca.rest.wrappers;

import java.util.LinkedList;

import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Concept;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Lattice;

/**
 * Wrapper used by the REST Interface to transmit a {@link Lattice} without any
 * nested or cyclic references. Converting the original Latice to JSON is
 * infeasible because it's internal structure causes all concepts to be
 * serialized multiple times (roughly n(n+1)/2 times) leading to large amounts
 * of data transmitted (up to several megabytes).
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 * @see Lattice
 */
public class LatticeWrapper extends AbstractWrapper {

  public ConceptWrapper bottom, top;
  public LinkedList<ConceptWrapper> concepts;

  public LatticeWrapper() {

  }

  /**
   * @param lattice
   *          the original {@link Lattice} to wrap
   */
  public LatticeWrapper(Lattice lattice) {
    super.name = lattice.getName();
    super.description = lattice.getDescription();
    super.id = lattice.getId();
    concepts = new LinkedList<ConceptWrapper>();

    for (Concept c : lattice.getConcepts()) {
      ConceptWrapper concept = new ConceptWrapper(c);
      concept.setSucessors(c.getSuccessors(), c.getTaxonomySuccessors());
      concepts.add(concept);
      if (c.getPredecessors().isEmpty())
        bottom = concept;
      if (c.getSuccessors().isEmpty())
        top = concept;
    }
  }

}
