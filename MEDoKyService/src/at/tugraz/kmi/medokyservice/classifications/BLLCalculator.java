package at.tugraz.kmi.medokyservice.classifications;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BLLCalculator {
	
	
	public List<String> getRankedTagList(int userID) {
		Map<String, Long> userTag = new LinkedHashMap<String, Long>();
		Map<String, Integer> tagsByGroup = new LinkedHashMap<String, Integer>();
		
		Map<String, Double> actValues = new LinkedHashMap<String, Double>();
		Map<String, Double> mpValues = new LinkedHashMap<String, Double>();
		List <String> recTags = new LinkedList<String>();
		
		
		//getTagsByUser(userId):<tag, timestamp>
		actValues = this.calculateNormalizedActValue(userTag);
				
		//getTagsByGroup(groupId):<tag, frequency>
		mpValues = this.calculateNormalizedMostPopular(tagsByGroup);
		
		
		// add MPValue to act values
		for (Map.Entry<String, Double> entry: actValues.entrySet()){
			if (mpValues.containsKey(entry.getKey()))
				actValues.put(entry.getKey(), entry.getValue()+mpValues.get(entry.getKey()));
		}
		
		
		// determine 6 highest values
		// write 6 values to result Map or set TagRecommendations
		
		ActValueComparator actvc = new ActValueComparator(actValues);
		TreeMap<String, Double> resultMap = new TreeMap<String, Double>(actvc); 
		resultMap.putAll(actValues);
		
		int i=0;
		for (Map.Entry<String, Double> entry: resultMap.entrySet()){
			recTags.add(i, entry.getKey());
			i++;
			if (i==6)
				break;
		}
		return recTags;
	}


	

	private Map<String, Double> calculateNormalizedActValue(Map<String, Long> userTags) {
		double dVal = 0.5;
		long currentTime = System.currentTimeMillis();
		Map<String, Double> actValues = new LinkedHashMap<String, Double>();

		for (Map.Entry<String, Long>tag : userTags.entrySet()){
			Double recency = (double)(currentTime - tag.getValue() + 1.0);
			double newAct = Math.pow(recency, dVal * -1.0);
			double oldAct = 0.0;
			if (actValues.containsKey(tag.getKey()))
				oldAct = actValues.get(tag.getKey());
			actValues.put(tag.getKey(), oldAct + newAct);
		}

		//normalize
		double sum =0;
		for (Map.Entry<String, Double> entry : actValues.entrySet()) {
				sum += entry.getValue();
		}
		for (Map.Entry<String, Double> entry : actValues.entrySet()) {
			entry.setValue(entry.getValue()/ sum);
		}
		return actValues;
	}


	private Map<String, Double> calculateNormalizedMostPopular(
			Map<String, Integer> tagsByGroup) {

		Map<String, Double> sortedMostPopular = new LinkedHashMap<String, Double>(tagsByGroup.size());
		MPValueComparator mpvc = new MPValueComparator(tagsByGroup);
		TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(mpvc); 
		sorted_map.putAll(tagsByGroup);
		
		// TODO: normalize values - this is one approach CHECK!!!
		// TODO: check whether List is sorted correctly!!
		//normalize
		double sum =0;
		for (Map.Entry<String, Integer> entry : sorted_map.entrySet()) {
				sum += entry.getValue();
		}
		for (Map.Entry<String, Integer> entry : sorted_map.entrySet()) {
			sortedMostPopular.put(entry.getKey(), entry.getValue()/ sum);
		}
		return sortedMostPopular;
	}
}


class MPValueComparator implements Comparator<String> {
	
    Map<String, Integer> base;
    public MPValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}


class ActValueComparator implements Comparator<String> {
	
    Map<String, Double> base;
    
    public ActValueComparator(Map<String, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}