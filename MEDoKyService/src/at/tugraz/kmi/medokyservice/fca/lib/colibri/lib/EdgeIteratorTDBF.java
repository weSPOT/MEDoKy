package at.tugraz.kmi.medokyservice.fca.lib.colibri.lib;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Concept;
import at.tugraz.kmi.medokyservice.fca.lib.colibri.lib.Agenda;





/**
 * An iterator over the edges (i.e.&nbsp;pairs of neighboring concepts)
 * of the underlying lattice. Traverses the lattice <i>top-down</i> 
 * and <i>breadth-first</i>.
 * @author Daniel N. Goetzmann
 * @version 1.0
 */
class EdgeIteratorTDBF implements Iterator<Edge> {
	
	private Lattice lattice;
	private Comparator<Concept> comparator;
	private Agenda<Concept> agenda;
	private Concept current;
	private Iterator<Concept> localIterator;
	
	
	/**
	 * Constructs an iterator.
	 * @param lattice the underlying lattice.
	 * @param order the order to be used to compare compare
	 * the concepts.
	 */
	EdgeIteratorTDBF(Lattice lattice, ConceptOrder order) {
		this.lattice = lattice;
		
		switch (order) {
		case OBJ_STD:
		case OBJ_SIZEFIRST:
			comparator = new ConceptComparator(order, -1);
			break;
		case ATTR_STD:
		case ATTR_SIZEFIRST:
			comparator = new ConceptComparator(order);
			break;
		default:
			throw new IllegalArgumentException("This edge order is not supported.");
		}
		
		agenda = new Agenda<Concept>(comparator);
		current = lattice.top();
		localIterator  = lattice.lowerNeighbors(current);
	}
	
	
	/**
	 * Returns <code>true</code> iff there is
	 * an edge that has not been returned by a previous
	 * call of the <code>next</code> method.
	 * @return <code>true</code> if there is another edge
	 * that has not been returned yet.
	 */
	public boolean hasNext() {
		if (localIterator.hasNext()) {			
			return true;
		}
		else {
			if (!agenda.isEmpty()) {
				current =  agenda.pop();
				
				localIterator = lattice.lowerNeighbors(current);
				
				return (localIterator != null && localIterator.hasNext());
			}
			else {
				return false;
			}
		}
	}
	
	
	/**
	 * Returns the next edge in the iteration.
	 * @return the next edge in the iteration.
	 */
	public Edge next() {
		if (!hasNext())
			throw new NoSuchElementException();
		
		Concept lower = localIterator.next();
		agenda.add(lower);

		return new Edge(current, lower);
	}
	
	
	/**
	 * Throws an <code>UnsupportedOperationException</code>
	 * since edges may not be removed from the lattice.
	 * @throws <code>UnsupportedOperationException</code>.
	 */
	public void remove() {
		throw new UnsupportedOperationException("Edges can not be removed from the lattice.");
	}
	
}
