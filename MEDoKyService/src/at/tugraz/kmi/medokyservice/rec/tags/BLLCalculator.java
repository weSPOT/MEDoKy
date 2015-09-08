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
package at.tugraz.kmi.medokyservice.rec.tags;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import at.tugraz.kmi.medokyservice.fca.FCAException;
import at.tugraz.kmi.medokyservice.fca.FCAInterface;
import at.tugraz.kmi.medokyservice.rec.datapreprocessing.ContentTags;

public class BLLCalculator {
	
	Map<String, Double> actValues; 
	boolean hasSemanticFeatures;
	
	public BLLCalculator(){
		actValues = new LinkedHashMap<String, Double>();
		hasSemanticFeatures = false;
	}

	protected List<String> getSemanticFeatures(String inquiryId){
		try {
			
			return FCAInterface.getDomainAttributes(FCAInterface.getInquiryID(inquiryId));
							
		} catch (FCAException e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}
	
	public Map<String, Double> calculateNormalizedActValue(List<ContentTags> contentTags) {
		double dVal = 0.5;
		long currentTime = System.currentTimeMillis();
		for (ContentTags tagEntry : contentTags){
			
			Double recency = (double)(currentTime - tagEntry.timestamp + 1.0);
			System.out.println("currentTime: "+currentTime);
			System.out.println("tagEntryTime: "+tagEntry.timestamp+1);
			System.out.println("recency: "+recency);
			for (String tag : tagEntry.tags){
				double newAct = Math.pow(recency, dVal * -1.0);
				double oldAct = 0.0;
				if (this.actValues.containsKey(tag))
					oldAct = this.actValues.get(tag);
				this.actValues.put(tag, oldAct + newAct);
			}
		}

		//normalize
		double sum =0;
		for (Map.Entry<String, Double> entry : this.actValues.entrySet()) {
			 double bi = Math.log(entry.getValue());	
			 sum += bi;
			 entry.setValue(bi);
		}
		for (Map.Entry<String, Double> entry : this.actValues.entrySet()) {
			entry.setValue(entry.getValue()/ sum);
		}
		
		return this.actValues;
	}
	
	public List<String> getRecommendedTags(int number){
		// determine 6 highest values
		// write 6 values to result Map or set TagRecommendations
		StringDoubleComparator actvc = new StringDoubleComparator(this.actValues);
		TreeMap<String, Double> resultMap = new TreeMap<String, Double>(actvc); 
		resultMap.putAll(this.actValues);
		List<String> recTags = new LinkedList<String>();
		int i=0;
		for (Map.Entry<String, Double> entry: resultMap.entrySet()){
			recTags.add(i, entry.getKey());
			i++;
			if (i==number)
				break;
			}
		return recTags;
	}
	
	public  Map<String, Double> calculateNormalizedMostPopular(
			Map<String, Long> tagsByGroup) {

		Map<String, Double> sortedMostPopular = new LinkedHashMap<String, Double>(tagsByGroup.size());
		StringLongComperator mpvc = new StringLongComperator(tagsByGroup);
		TreeMap<String, Long> sorted_map = new TreeMap<String, Long>(mpvc); 
		sorted_map.putAll(tagsByGroup);
		
		// TODO: normalize values - this is one approach CHECK!!!
		// TODO: check whether List is sorted correctly!!
		//normalize
		double sum =0;
		for (Map.Entry<String, Long> entry : sorted_map.entrySet()) {
				sum += entry.getValue();
		}
		for (Map.Entry<String, Long> entry : sorted_map.entrySet()) {
			sortedMostPopular.put(entry.getKey(), entry.getValue()/ sum);
		}
		return sortedMostPopular;
	}
}

class StringLongComperator implements Comparator<String> {
	
    Map<String, Long> base;
    public StringLongComperator(Map<String, Long> base) {
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


class StringDoubleComparator implements Comparator<String> {
	
    Map<String, Double> base;
    
    public StringDoubleComparator(Map<String, Double> base) {
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