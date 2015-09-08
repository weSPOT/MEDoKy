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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

import at.tugraz.kmi.medokyservice.fca.FCAException;
import at.tugraz.kmi.medokyservice.fca.FCAInterface;
import at.tugraz.kmi.medokyservice.rec.io.SolrDBClient;

public class MostPopularTagsIndividual implements TagRecommender {

	boolean hasFeatures;
	
	public MostPopularTagsIndividual() {
		hasFeatures=false;
	}
	
	
	@Override
	public List<String> getRankedTagList(String inquiryId, String userId,
			String url, int number) throws SolrServerException {
		
		try {
			List<String> features = FCAInterface.getDomainAttributes(FCAInterface.getInquiryID(inquiryId));
			if (features.size()>0){
				this.hasFeatures = true;
				return features;
			}
						
		} catch (FCAException e) {
			e.printStackTrace();
		}
		
		return getRankedTagList(inquiryId, userId, url,"", number);
	}

	@Override
	public List<String> getRankedTagList(String inquiryId, String userId,
			String url, String semanticFeatures, int number)
			throws SolrServerException {
		LinkedList<String> recTags = new LinkedList<String>(); 
		Map<String, Long> relevantTags;
		SolrDBClient dbClient = new SolrDBClient(url);
		
		relevantTags = dbClient.getTagCountByUser(userId);
		int n=0;
		for (Map.Entry<String, Long> entry : relevantTags.entrySet()) {
			recTags.add(entry.getKey());
			n++;
			if (n==number)
				break;
		}
		
		return recTags;
	}

	@Override
	public String getType() {
		return "MPIndividual";
	}

	@Override
	public boolean hasFeatures() {
		return this.hasFeatures;
	}

}
