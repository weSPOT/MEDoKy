package at.tugraz.kmi.medokyservice.classifications;


import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import at.tugraz.kmi.medokyservice.fca.FCAInterface; 
import at.tugraz.kmi.medokyservice.fca.db.FCAAbstract;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerConcept;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerLattice;

public class FCARecommendation {

	FCAInterface fca;
	HashMap<FCAAbstract, Float> attributes; 
	Map<FCAAbstract, Float> objects;
	// a cncept can be either dominated by Attributes or Objects
	boolean isAttributeConcept;
	
	public FCARecommendation(){
		fca = new FCAInterface();
		
	}
	
	public void calculate(long inquiryId, long learnerId){
		Collection<LearnerLattice> learnerModel = fca.getLearnerModel(inquiryId, learnerId);
		for (LearnerLattice lattice : learnerModel){
			if (learnerModel.size()>1 && lattice.getName().equals("IBL"))
			 break;
			Set<LearnerConcept> learnerConcepts = lattice.getConcepts();	
			this.initConceptVariables(learnerConcepts);
			//Compare Num Objects and Num Attributes
			// -> object concept, attribute concept 
			// getLearnerModel()
						
			// if (no LOs consumed)
			//LearnerConcept selectedConcept = this.identifyCentralConceptLOs(learnerConcepts);
			Set<LearningObject> los = this.identifyCentralConceptLOs(learnerConcepts);
			
			
			// if LO already consumed
			this.stepTwo(learnerConcepts);
			
		}
	}
	

/*	private Set<LearningObject> getLearningObjects(LearnerConcept selectedConcept) {
		Set<LearningObject> los = new HashSet<LearningObject>();
		if (this.isAttributeConcept){
			for (FCAObject object : selectedConcept.getObjects().keySet()){
				los.addAll((Collection)object.getLearningObjects());
			}
		}	
		return los;
	}*/

	private Set<LearningObject> identifyCentralConceptLOs(Set<LearnerConcept> concepts){
		LearnerConcept centralConcept = null;
		int average = 0; 
		int min = 0;
		Set<LearningObject> los = new HashSet();
		// if there are more attributes than objects, concept with a medium number of attributes. 
		// learn LOs assigned to objects of this concept.
		if (this.isAttributeConcept) // lerne LOs aligned to objects
		{
			average = this.attributes.size()/2;
			min =  average;
			for (LearnerConcept concept : concepts){
				int deviation = Math.abs(average - this.attributes.size());  
				if (deviation<=min){
					 if (deviation<min){
						 centralConcept = concept;
						 break;
					 }
					// TODO: decide randomly
				 }
			  } 
			
			for (FCAObject object : centralConcept.getObjects().keySet())
				los.addAll((Collection)object.getLearningObjects());
			return los;
		}
		
		average = this.objects.size()/2;
		min = average;
		for (LearnerConcept concept : concepts){
			 int deviation = Math.abs(average - this.objects.size()); 
			 if (deviation<=min){
				 if (deviation<min){
					 centralConcept = concept;
					 break;
				 }
				 // TODO: decide randomly
			 } 
		  } 

		for (FCAAttribute attribute : centralConcept.getAttributes().keySet())
			los.addAll((Collection)attribute.getLearningObjects());
		return los;
	}
	
	private void stepTwo(Set<LearnerConcept>learnerConcepts) {
		TreeMap<FCAAbstract,Float> sorted_map;
		ValueComparator vc;
		Set<LearningObject> los = new HashSet<LearningObject>();
		
		if (this.isAttributeConcept){
			vc =  new ValueComparator(this.objects);
			sorted_map = new TreeMap<FCAAbstract,Float>(vc);
	        for (Entry<FCAAbstract, Float> entry : sorted_map.entrySet()){
	        	
	        }

//			while (!los.size()>0)
	//			los = sorted_map.firstKey().getLearningObjects();
			
		} 
	}

	
	
	private void initConceptVariables(Set<LearnerConcept> concepts){
		  this.attributes = new HashMap<FCAAbstract, Float>();
		  this.objects = new HashMap<FCAAbstract, Float>();
		  
			for (LearnerConcept concept : concepts){
			  this.attributes.putAll(concept.getAttributes());
			  this.objects.putAll(concept.getObjects());
		  } 
			
		 if (this.attributes.size()>=this.objects.size())
			 this.isAttributeConcept = true;
	  }
}


class ValueComparator implements Comparator<FCAAbstract> {
	
    Map<FCAAbstract, Float> base;
    public ValueComparator(Map<FCAAbstract, Float> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(FCAAbstract a, FCAAbstract b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
