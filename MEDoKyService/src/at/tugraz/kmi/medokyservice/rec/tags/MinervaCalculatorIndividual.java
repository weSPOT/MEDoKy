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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

import at.tugraz.kmi.medokyservice.fca.FCAException;
import at.tugraz.kmi.medokyservice.fca.FCAInterface;
import at.tugraz.kmi.medokyservice.rec.datapreprocessing.ContentTags;
import at.tugraz.kmi.medokyservice.rec.io.SolrDBClient;

public class MinervaCalculatorIndividual extends MinervaCalculator implements TagRecommender {

	@Override
	public List<String> getRankedTagList(String inquiryId, String userId,
			String url, int number) throws SolrServerException {
		return this.getSemanticFeatures(inquiryId);
	}

	
	@Override
	public List<String> getRankedTagList(String inquiryId, String userId,
			String url, String semanticFeatures, int number)
			throws SolrServerException {
				
		SolrDBClient dbClient = new SolrDBClient(url);
		List<ContentTags> userTags = dbClient.getContentFeatureTagsByUser(userId);
		return this.calculateRankedTagList(semanticFeatures, userTags, number);
	}

	@Override
	public String getType() {
		return "MinervaIndividual";
	}

	@Override
	public boolean hasFeatures() {
		return true;
	}

}
