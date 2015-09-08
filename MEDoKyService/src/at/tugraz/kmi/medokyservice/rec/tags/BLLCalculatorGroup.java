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

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.solr.client.solrj.SolrServerException;

import at.tugraz.kmi.medokyservice.rec.datapreprocessing.ContentTags;
import at.tugraz.kmi.medokyservice.rec.io.SolrDBClient;



public class BLLCalculatorGroup extends BLLCalculator implements TagRecommender {
	

	
	@Override
	public boolean hasFeatures() {
		return this.hasSemanticFeatures;
	}
	
	public List<String> getRankedTagList(String inquiryId, String userID, String url, int number) throws SolrServerException {
		List<String> features = this.getSemanticFeatures(inquiryId);
		if (features.size()>0){
		 this.hasSemanticFeatures = true;
		 return features;
		}
		return this.getRankedTagList(inquiryId, userID, url,"", number);
	}


	@Override
	public String getType() {
		return "BLLGroup";
	}


	@Override
	public List<String> getRankedTagList(String inquiryId, String userId,
			String url, String semanticFeatures, int number)
			throws SolrServerException {
		SolrDBClient dbClient = new SolrDBClient(url);
		List<ContentTags> groupTags = new LinkedList<ContentTags>();
		groupTags = dbClient.getContentTagsByGroup(inquiryId);
		this.calculateNormalizedActValue(groupTags);
		return this.getRecommendedTags(number);
	}

}


