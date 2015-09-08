/*
 * 
 * MEDoKyService:
 * A web service component for learner modelling and learning recommendations.
 * Copyright (C) 2015 KTI, TUGraz, Contact: simone.kopeinik@tugraz.at
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Affero General Public License for more details.  
 * For more information about the GNU Affero General Public License see <http://www.gnu.org/licenses/>.
 * 
 */
package at.tugraz.kmi.medokyservice.fca.rest.wrappers;

import java.util.HashSet;
import java.util.LinkedList;

import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Concept;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Lattice;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerConcept;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerLattice;

/**
 * Wrapper used by the REST Interface to transmit a {@link Lattice} without any
 * nested or cyclic references. Converting the original Latice to JSON is
 * infeasible because it's internal structure causes all concepts to be
 * serialized multiple times (roughly n(n+1)/2 times) leading to large amounts
 * of data transmitted (up to several megabytes).
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 * @see Lattice
 */
public class LatticeWrapper extends AbstractWrapper {

  public ConceptWrapper             bottom, top;
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
      ConceptWrapper concept = new ConceptWrapper(c, new HashSet<LearningObject>());
      concept.setSucessors(c.getSuccessors(), c.getTaxonomySuccessors(), new HashSet<LearningObject>());
      concepts.add(concept);
      if (c.getPredecessors().isEmpty())
        bottom = concept;
      if (c.getSuccessors().isEmpty())
        top = concept;
    }
  }

  /**
   * @param lattice
   *          the original {@link Lattice} to wrap
   */
  public LatticeWrapper(LearnerLattice lattice) {
    super.name = lattice.getName();
    super.description = lattice.getDescription();
    super.id = lattice.getId();
    concepts = new LinkedList<ConceptWrapper>();
    for (LearnerConcept c : lattice.getConcepts()) {
      ConceptWrapper concept = new ConceptWrapper(c, lattice.getClickedLearningObjects());
      concept.setSucessors(c.getSuccessors(), c.getTaxonomySuccessors(), lattice.getClickedLearningObjects());
      concepts.add(concept);
      if (c.getPredecessors().isEmpty())
        bottom = concept;
      if (c.getSuccessors().isEmpty())
        top = concept;
    }
  }

}
