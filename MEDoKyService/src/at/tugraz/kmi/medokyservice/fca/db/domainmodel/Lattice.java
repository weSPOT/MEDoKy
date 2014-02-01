package at.tugraz.kmi.medokyservice.fca.db.domainmodel;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;
import java.util.logging.Level;

import com.sun.istack.logging.Logger;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.lib.colibri.lib.HybridLattice;
import at.tugraz.kmi.medokyservice.fca.lib.colibri.lib.Traversal;

/**
 * Lattice for a formal context consisting of a set of {@link Concept}s. Each
 * concept contains a set of its predecessors and its successors defining the
 * lattice's structure.
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */

public class Lattice extends DataObject {

  /**
   * 
   */
  private static final long serialVersionUID = -2097176586538411056L;
  private Set<Concept> concepts;
  private Concept bottom, top;

  /**
   * Creates a Lattice from an {@link IncidenceMatrix} by using a
   * {@link at.tugraz.kmi.medokyservice.fca.lib.colibri.lib.Lattice}. Since the
   * Lattice used for this process implements a reverse search it is necessary
   * to dispose of duplicate generated concepts and update references to already
   * existing objects since the structure of the lattice is defined through sets
   * of predecessors and successors of each concept.
   * 
   * @param matrix
   *          the IncidenceMatrix the lattice is based upon
   */
  public Lattice(final IncidenceMatrix matrix) {
    super("Lattice for " + matrix.getName(), matrix.getDescription());

    concepts = Collections.synchronizedSet(new LinkedHashSet<Concept>());
    synchronized (concepts) {
      int errorcount = 0;
      boolean error = true;
      while (error && errorcount <= 15) {
        HybridLattice lattice = new HybridLattice(matrix.getRelation());
        concepts.clear();

        error = false;
        Iterator<Concept> conceptsIt = lattice
            .conceptIterator(Traversal.BOTTOM_OBJSIZE);
        while (conceptsIt.hasNext()) {
          Concept c = conceptsIt.next();
          Iterator<Concept> lower = lattice.lowerNeighbors(c);
          for (Concept tmp : concepts) {
            if (tmp.equals(c)) {
              c = tmp;
              break;
            }
          }
          concepts.add(c);
          while (lower.hasNext()) {
            Concept lo = lower.next();
            for (Concept tmp : concepts) {
              if (tmp.equals(lo)) {
                lo = tmp;
                break;
              }
            }

            if (!lo.equals(c))// WTF??
              Concept.relate(lo, c);
            else {
              error = true;
              ++errorcount;
              Logger
                  .getLogger(Lattice.class)
                  .log(Level.WARNING,
                      "A problem was encountered creating the required lattice - retrying...");
              continue;
            }
          }
        }

        if (!error) {
          lattice = null;
          for (Concept c : concepts) {
            if (c.getPredecessors().size() == 0) {
              c.setAttributes(matrix.getAttributes().keySet());
              bottom = c;
            } else if (c.getSuccessors().size() == 0) {
              c.setObjects((matrix.getObjects().keySet()));
              top = c;
            }
            c.filterCharacteristics();
            c.disallowChanges();
          }
          concepts = Collections.unmodifiableSet(concepts);
        }
        updateTaxonomy();
      }
      if (error) {
        Logger.getLogger(Lattice.class).log(Level.SEVERE,
            "Failed to create correct lattice, this needs to be fixed!");
      }
    }
  }

  /**
   * Returns the bottom concept
   * 
   * @return the bottom concept
   */
  public Concept getBottom() {
    return bottom;
  }

  /**
   * Returns the top concept
   * 
   * @return the top concept
   */
  public Concept getTop() {
    return top;
  }

  /**
   * Return a String representation of this object. The string value returned is
   * subject to change and therefore only suitable for debugging purposes.
   * 
   * @return s String representation of this object
   */
  public String toString() {
    StringBuffer buf = new StringBuffer(getName());
    for (Concept c : concepts) {
      buf.append("\n").append(c.getId());
      for (Concept u : c.getSuccessors())
        buf.append("\n\t\t").append(u.getId());
    }
    return buf.toString();
  }

  /**
   * Updates the successor relations of all concepts wrt. the taxonomy
   */
  public void updateTaxonomy() {
    LinkedList<Concept> concepts = new LinkedList<Concept>();
    concepts.addAll(this.concepts);
    ListIterator<Concept> it = concepts.listIterator(concepts.size());
    while (it.hasPrevious()) {
      it.previous().updateTaxonomySuccessors();
    }
  }

  public Set<Concept> getConcepts() {
    synchronized (concepts) {
      return concepts;
    }
  }

}
