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

import at.tugraz.kmi.medokyservice.rec.io.SolrDBClient;

public class RandomTags implements TagRecommender{

	@Override
	public List<String> getRankedTagList(String groupId, String userId, 
			String url, int number) throws SolrServerException {
		
		LinkedList<String> recTags = new LinkedList<String>();
		Map<String, Long> relevantTags;
		SolrDBClient dbClient = new SolrDBClient(url);
		relevantTags = dbClient.getTagsByGroupOrUser(groupId, userId);
		
		String[] tagArray = relevantTags.keySet().toArray(new String[0]);
		ArrayList<Integer> usedIndizes = new ArrayList<Integer>();
		
		for (int i=0; i < relevantTags.size() && i<number; i++){
			int rnd;
			do{
				rnd = (int) (Math.random() * relevantTags.size());
			 }while(usedIndizes.contains(rnd));
			
			usedIndizes.add(rnd);
			recTags.add(tagArray[rnd]);
		}
		return recTags;
	}

	@Override
	public String getType() {
		return "Random";
	}
	
	@Override
	public boolean hasFeatures() {
		return false;
	}
	
	public static void main (String[] args){
		RandomTags tags = new RandomTags();
		String url = "http://localhost:8080/solr";
		BLLCalculatorGroup bllTags = new BLLCalculatorGroup();
		MostPopularTagsGroup mpTags = new MostPopularTagsGroup();
		
		try {
			List<String> list = tags.getRankedTagList("43044", "weSPOT_demo", url, 6);
			System.out.println(tags.getType());
			System.out.println(list);
			list = bllTags.getRankedTagList("43044", "weSPOT_demo", url, 6);
			System.out.println(bllTags.getType());
			System.out.println(list);
			list = mpTags.getRankedTagList("43044", "weSPOT_demo", url, 6);
			System.out.println(mpTags.getType());
			System.out.println(list);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	@Override
	public List<String> getRankedTagList(String inquiryId, String userId,
			String url, String semanticFeatures, int number)
			throws SolrServerException {
		return this.getRankedTagList(inquiryId, userId, url, number);
	}

}
