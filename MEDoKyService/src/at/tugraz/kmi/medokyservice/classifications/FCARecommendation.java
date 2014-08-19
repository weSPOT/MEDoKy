package at.tugraz.kmi.medokyservice.classifications;


import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import at.tugraz.kmi.medokyservice.fca.FCAInterface;
import at.tugraz.kmi.medokyservice.fca.db.FCAAbstract;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerConcept;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerLattice;

public class FCARecommendation {

	HashMap<FCAAbstract, Float> attributes; 
	Map<FCAAbstract, Float> objects;
	// a concept can be either dominated by Attributes or Objects
	boolean isAttributeConcept;
	
	public FCARecommendation(){}
	
	public Set<LearningObject> calculateLoRecommendation(long inquiryId, long learnerId){
		Collection<LearnerLattice> learnerModel = FCAInterface.getLearnerModel(inquiryId, learnerId);
		Set<LearningObject> learningObjects = new HashSet<LearningObject>();
		for (LearnerLattice lattice : learnerModel){
			if (learnerModel.size()>1 && lattice.getName().equals("IBL"))
			 break;
			learningObjects.addAll(this.processLearnerLattice(lattice));
		}
		//TODO: think about whether to include the IBL domain or not
		return learningObjects;
	}
	

	private Set<LearningObject> processLearnerLattice(LearnerLattice lattice){
		
		Set<LearningObject> clickedLearningObjects = lattice.getClickedLearningObjects();
		Set<LearnerConcept> learnerConcepts = lattice.getConcepts();	
		Set<LearningObject> selectedLos = new HashSet<LearningObject>();
		
		this.initConceptVariables(learnerConcepts);
		
		// step one - init
		if (clickedLearningObjects.size()<=0){
			    if (!this.isAttributeConcept){
			    	selectedLos = this.identifyAttributeLOs(learnerConcepts);	
			    	if (selectedLos.size()>0)
			    		return selectedLos;
			    	return	this.identifyObjectLOs(learnerConcepts);	
			    }
				selectedLos = this.identifyObjectLOs(learnerConcepts);	
		    	if (selectedLos.size()>0)
		    		return selectedLos;
		    	return	this.identifyAttributeLOs(learnerConcepts);
			}	
		
		
		// step two
		if (!this.isAttributeConcept){
			selectedLos = this.getLOsFromRankedItems(this.attributes, clickedLearningObjects);
			if (selectedLos.size()>0)
				return selectedLos; 
			//find out whether there are LOs already consumed for objects
			Collection<Float> values = this.objects.values();
			values.removeAll(Collections.singleton(0));
			if(values.isEmpty())
				return	this.identifyObjectLOs(learnerConcepts);
			
			return this.getLOsFromRankedItems(this.objects, clickedLearningObjects);
		}
		
		selectedLos = this.getLOsFromRankedItems(this.objects, clickedLearningObjects);
		if (selectedLos.size()>0)
			return selectedLos; 
		//find out whether there are LOs already consumed for objects
		Collection<Float> values = this.attributes.values();
		values.removeAll(Collections.singleton(0));
		if(values.isEmpty())
			return	this.identifyObjectLOs(learnerConcepts);
		
		return this.getLOsFromRankedItems(this.attributes, clickedLearningObjects);
	}
	
	
	private Set<LearningObject> identifyObjectLOs(Set<LearnerConcept> concepts){
		HashMap<LearnerConcept, Double> base = new HashMap<LearnerConcept, Double>(); 
		DoubleValueComparator dvc; 
		TreeMap<LearnerConcept,Double> sorted_map; 
		Set<LearningObject> los = new HashSet<LearningObject>();
		double average = this.attributes.size()/2;
		
		for (LearnerConcept concept : concepts){
			
			
			Double deviation = Math.abs(average - concept.getAttributes().size());  
			base.put(concept, deviation);
			}
		dvc = new DoubleValueComparator(base);
		sorted_map = new TreeMap<LearnerConcept, Double>(dvc);
		
		for (LearnerConcept concept : sorted_map.keySet()){
			for (FCAObject object : concept.getObjects().keySet()){
				los.addAll(object.getLearningObjects());
				if (los.size()>0)
					return los;
			}	
		}
		return los;
	}

	private Set<LearningObject> identifyAttributeLOs(Set<LearnerConcept> concepts){
		HashMap<LearnerConcept, Double> base = new HashMap<LearnerConcept, Double>(); 
		DoubleValueComparator dvc; 
		TreeMap<LearnerConcept,Double> sorted_map; 
		Set<LearningObject> los = new HashSet<LearningObject>();
		double average = this.objects.size()/2;
		
		for (LearnerConcept concept : concepts){
			Double deviation = Math.abs(average - concept.getObjects().size());  
			base.put(concept, deviation);
			}
		dvc = new DoubleValueComparator(base);
		sorted_map = new TreeMap<LearnerConcept, Double>(dvc);
		
		for (LearnerConcept concept : sorted_map.keySet()){
			for (FCAAttribute attribute : concept.getAttributes().keySet()){
				los.addAll(attribute.getLearningObjects());
				if (los.size()>0)
					return los;
			}	
		}
		return los;
	}
	
	private Set<LearningObject>	getLOsFromRankedItems(Map<FCAAbstract, Float> items, Set<LearningObject> clickedLearningObjects) {
		TreeMap<FCAAbstract,Float> sorted_map;
		ValueComparator vc;	
		Set<LearningObject> los = new HashSet<LearningObject>();
		// sort entry set according to values
		vc =  new ValueComparator(items);
		sorted_map = new TreeMap<FCAAbstract,Float>(vc);
	    // remove Objects with value 1 - 1 means all Los are already consumed
		sorted_map.values().removeAll(Collections.singleton(1));
		
		for (FCAAbstract item : sorted_map.keySet()){
			los = item.getAllLearningObjects(); 
			los.removeAll(clickedLearningObjects);
			if (los.size()>0)
				return los;
		}
		return los;
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

class DoubleValueComparator implements Comparator<LearnerConcept> {
	
    Map<LearnerConcept, Double> base;
    public DoubleValueComparator(Map<LearnerConcept, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(LearnerConcept a, LearnerConcept b) {
        if (base.get(a) < base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}