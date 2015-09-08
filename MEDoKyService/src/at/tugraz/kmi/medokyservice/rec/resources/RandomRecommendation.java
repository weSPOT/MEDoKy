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
package at.tugraz.kmi.medokyservice.rec.resources;


import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import at.tugraz.kmi.medokyservice.fca.FCAException;
import at.tugraz.kmi.medokyservice.fca.FCAInterface;
import at.tugraz.kmi.medokyservice.fca.db.FCAAbstract;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerConcept;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerLattice;
import at.tugraz.kmi.medokyservice.rec.bl.SimpleUserClassificationThread;

public class RandomRecommendation {

	HashMap<FCAAbstract, Float> attributes; 
	Map<FCAAbstract, Float> objects;
	
	public RandomRecommendation(){}
	
	public Set<LearningObject> calculateLoRecommendation(String inquiryId, String learnerId){
		System.out.println("course: "+inquiryId+" learner "+learnerId);
		Collection<LearnerLattice> learnerModel;
		try {
			learnerModel = FCAInterface.getLearnerModel(FCAInterface.getInquiryID(inquiryId), FCAInterface.getLearnerID(learnerId));
			
		} catch (FCAException e) {
			// TODO Auto-generated catch block
			System.out.println("error in random recommendation");
			e.printStackTrace();
			return new HashSet<LearningObject>();
		}
		
		Set<LearningObject> learningObjects = new HashSet<LearningObject>();
		for (LearnerLattice lattice : learnerModel){
			if (learnerModel.size()>1 && lattice.getName().equals("IBL"))
			 break;
			learningObjects.addAll(this.processLearnerLattice(lattice));
		}

		return learningObjects;
	}
	
	
	private Set<LearningObject> processLearnerLattice(LearnerLattice lattice){
		
		Set<LearningObject> clickedLearningObjects = lattice.getClickedLearningObjects();
		Set<LearnerConcept> concepts = lattice.getConcepts();
		Set<LearningObject> selectedLos = new HashSet<LearningObject>();
		selectedLos.addAll(this.identifyAttributeLOs(lattice.getAttributes())); 
		selectedLos.addAll(this.identifyObjectLOs(lattice.getObjects()));
		selectedLos.removeAll(clickedLearningObjects);
		return selectedLos;
	}
	

	private Set<LearningObject> identifyAttributeLOs(Map<FCAAttribute, Float> items){
		Set<LearningObject> los = new HashSet<LearningObject>();
		for (FCAAbstract item : items.keySet()){
				los.addAll(item.getAllLearningObjects());
		}	
		System.out.println("Attribute Los"+los.size());
		return los;
	}
	
	private Set<LearningObject> identifyObjectLOs(Map<FCAObject, Float> items){
		Set<LearningObject> los = new HashSet<LearningObject>();
		for (FCAObject item : items.keySet()){
				los.addAll(item.getAllLearningObjects());
		}	
		System.out.println("Object Los"+los.size());
		return los;
	}
	public static void main(String[] args) throws Exception {
		new SimpleUserClassificationThread("44412", "43096", 3, "textBased", "14").start();
		//rec.calculateLoRecommendation("43096", "44412");
	}	
}




