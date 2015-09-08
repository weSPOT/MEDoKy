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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.solr.client.solrj.SolrServerException;

import at.tugraz.kmi.medokyservice.fca.FCAException;
import at.tugraz.kmi.medokyservice.fca.FCAInterface;
import at.tugraz.kmi.medokyservice.rec.datapreprocessing.ContentTags;

public class MinervaCalculator {
	
	protected List<String> getSemanticFeatures(String inquiryId){
		try {
			return	FCAInterface.getDomainAttributes(FCAInterface.getInquiryID(inquiryId));
		} catch (FCAException e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}
	
	protected List<String> calculateRankedTagList(String semanticFeatures, List<ContentTags> entries, int number)
			throws SolrServerException {
	
		/*	get all docs with semFeatures and Tags
		 * 	calculate cosine sim. from given semantic Features to Tags
		 * 	Multiply sim. value with vectors
		 *  add activation values of tags
		 *  rank tags
		 */
		
		if (entries.size()<=0 || semanticFeatures.length()<=0)
			return new ArrayList<String>();
		
		HashMap<String, Double> tagValues = new HashMap<String, Double>();
		HashMap<String, Double> baseFeatures = new HashMap<String, Double>();
		List <String> recTags = new LinkedList<String>();
		
		for (String f : semanticFeatures.split(",")){
			baseFeatures.put(f.trim(), 1.0);
		}
				
		for (ContentTags tags : entries){ 
			HashMap<String, Double> oldFeatures = tags.features;
			Double sim = CosineSimilarity.calculateCosineSimilarity(baseFeatures, oldFeatures);
					
			for (String tag : tags.tags){
				if (tagValues.containsKey(tag)){
					tagValues.put(tag, tagValues.get(tag)+sim);
					continue;
				}
				tagValues.put(tag, sim);	
			}
		}
	
		StringDoubleComparator actvc = new StringDoubleComparator(tagValues);
		TreeMap<String, Double> resultMap = new TreeMap<String, Double>(actvc); 
		resultMap.putAll(tagValues);
		
		int i=0;
		for (Map.Entry<String, Double> entry: resultMap.entrySet())
		{
			recTags.add(i, entry.getKey());
			i++;
			if (i==number)
				break;
		}
		return recTags;
	}

}
