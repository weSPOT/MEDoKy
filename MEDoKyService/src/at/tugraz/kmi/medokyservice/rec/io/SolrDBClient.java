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
package at.tugraz.kmi.medokyservice.rec.io;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import at.tugraz.kmi.medokyservice.rec.datapreprocessing.ContentTags;
import at.tugraz.kmi.medokyservice.rec.tags.BLLCalculatorGroup;
import at.tugraz.kmi.medokyservice.rec.tags.BLLCalculatorIndividual;
import at.tugraz.kmi.medokyservice.rec.tags.MinervaCalculator;
import at.tugraz.kmi.medokyservice.rec.tags.MinervaCalculatorGroup;
import at.tugraz.kmi.medokyservice.rec.tags.MinervaCalculatorIndividual;



public class SolrDBClient extends HttpSolrServer{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SolrDBClient(String url){
		super(url); 
	}

	
	public List<ContentTags> getContentTagsByUser(String username) throws SolrServerException{
		
		List<ContentTags> tagList = new LinkedList<ContentTags>();		
		
		SolrQuery query = new SolrQuery("username:"+username)
				  	.setFields("tags, timestamp")
				  	.setFilterQueries("tags:['' TO *]")
				  	.setRows(10000000);
				  
				  QueryResponse rsp = this.query( query );
				  
				  SolrDocumentList docs = rsp.getResults();
				  
				  for (SolrDocument doc : docs) {
					  Date date = (Date) doc.getFieldValue("timestamp");
					  if (date == null || doc.getFieldValue("tags")==null)
						  continue;
					  
					  ContentTags tagEntry = new ContentTags(date);
					  tagEntry.tags = ((ArrayList<String>) doc.getFieldValue("tags"));
					  tagList.add(tagEntry);
				  }
			  
		return tagList;
	}
	
	//TODO: test if this returns the correct tags
	public List<ContentTags> getContentTagsByGroup(String inquiryId) throws SolrServerException {
		List<ContentTags> tagList = new LinkedList<ContentTags>();
		
		SolrQuery query = new SolrQuery("course:"+inquiryId)
	  	.setFields("tags, timestamp")
	  	.setFilterQueries("tags:['' TO *]")
	  	.setRows(10000000);
	  
	  QueryResponse rsp = this.query( query );
	  
	  SolrDocumentList docs = rsp.getResults();
	  
	  for (SolrDocument doc : docs) {
		  Date date = (Date) doc.getFieldValue("timestamp");
		  if (date == null || doc.getFieldValue("tags")==null)
			  continue;
		  
		  ContentTags tagEntry = new ContentTags(date);
		  tagEntry.tags = ((ArrayList<String>) doc.getFieldValue("tags"));
		  tagList.add(tagEntry);
	  }
  
	  return tagList;
	}
	
	public List<ContentTags> getContentFeatureTagsByGroup(String inquiryId) throws SolrServerException {
		List<ContentTags> tagList = new LinkedList<ContentTags>();
		
		SolrQuery query = new SolrQuery("course:"+inquiryId)
	  	.setFields("tags, features, timestamp")
	  	.setFilterQueries("tags:['' TO *] AND features:['' TO *] ")
	  	.setRows(10000000);
	  
	  QueryResponse rsp = this.query( query );
	  
	  SolrDocumentList docs = rsp.getResults();
	  
	  for (SolrDocument doc : docs) {
		  Date date = (Date) doc.getFieldValue("timestamp");
		  if (date == null || doc.getFieldValue("tags")==null || doc.getFieldValue("features")==null)
			  continue;
		  ContentTags tagEntry = new ContentTags(date);
		  tagEntry.tags = ((ArrayList<String>) doc.getFieldValue("tags"));
		  tagEntry.setFeatures((ArrayList<String>) doc.getFieldValue("features"));
		  tagList.add(tagEntry);
	  }
  
	  return tagList;
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Long> getTagCountByUser(String username) throws SolrServerException{
		
		LinkedHashMap<String, Long> userTagCount = new LinkedHashMap<String, Long>();
		SolrQuery query = new SolrQuery("username:"+username)
		  	.setFields(" ")
		  	.setRows(1)
		  	.setFacet(true)
		  	.setFacetMinCount(1)
		  	.addFacetField("tags");
		
		 QueryResponse rsp = this.query( query );	
		 FacetField ff = rsp.getFacetField("tags");
		 
		 for (Count count : ff.getValues()){
			  if (count.getName()==null || count.getName().length()<=0)
				  continue;
		
			 userTagCount.put(count.getName(), count.getCount());
		 }
			 	
		return userTagCount;
	}
	
	@SuppressWarnings("unchecked")
	public Set<String> getTagTextByUser(String username) throws SolrServerException{
		
		Set<String> tagList = new HashSet<String>();		
		
		SolrQuery query = new SolrQuery("username:"+username)
				  	.setFields("tags")
				  	.setRows(10000000);
				  
				  QueryResponse rsp = this.query( query );
				  
				  SolrDocumentList docs = rsp.getResults();
				  
				  for (SolrDocument doc : docs) {
					  if (doc.getFieldValue("tags") != null)
						  tagList.addAll((ArrayList<String>) doc.getFieldValue("tags"));
//					  if (tag == null)
//						  continue;
//					  tagList.add(tag);
					 
				  }
			  
		return tagList;
	}
	
	public Map<String, Long> getTagCountByGroup(String courseId) throws SolrServerException{
		   
		  LinkedHashMap<String, Long> groupTags = new LinkedHashMap<String, Long>();
		  
		  SolrQuery query2 = new SolrQuery("course:"+courseId)
		  	.setFields(" ")
		  	.setRows(1)
		  	.setFacet(true)
		 	.setFacetMinCount(1)
		  	.addFacetField("tags");
		  
		  
		  QueryResponse rsp2 = this.query( query2 );
		  
		  FacetField ff = rsp2.getFacetField("tags");
		  
		  for (Count count : ff.getValues()){
			  if (count.getName()==null || count.getName().length()<=0)
				  continue;
			  groupTags.put(count.getName(), count.getCount());
		  }		  
			  		
		  return groupTags;	
	}
	
	public Map<String, Long> getTagsByGroupOrUser(String groupId, String userId) throws SolrServerException{
		   
		  LinkedHashMap<String, Long> groupOrUserTags = new LinkedHashMap<String, Long>();
		  
		  SolrQuery query2 = new SolrQuery("course:"+groupId+" OR username:"+userId)
		  	.setFields(" ")
		  	.setRows(1)
		  	.setFacet(true)
		 	.setFacetMinCount(1)
		  	.addFacetField("tags");
		  
		  
		  QueryResponse rsp2 = this.query( query2 );
		  
		  FacetField ff = rsp2.getFacetField("tags");
		  
		  for (Count count : ff.getValues()){
			  if (count.getName()==null || count.getName().length()<=0)
				  continue;
			  groupOrUserTags.put(count.getName(), count.getCount());
		  }		  
			  		
		  return groupOrUserTags;	
	}
	
	public List<ContentTags> getContentFeatureTagsByUser(String userId) throws SolrServerException {
		List<ContentTags> tagList = new LinkedList<ContentTags>();
		
		SolrQuery query = new SolrQuery("username:"+userId)
	  	.setFields("tags, features, timestamp")
	  	.setFilterQueries("tags:['' TO *] AND features:['' TO *] ")
	  	.setRows(10000000);
	  
	  QueryResponse rsp = this.query( query );
	  
	  SolrDocumentList docs = rsp.getResults();
	  
	  for (SolrDocument doc : docs) {
		  Date date = (Date) doc.getFieldValue("timestamp");
		  if (date == null || doc.getFieldValue("tags")==null||doc.getFieldValue("features")==null)
			  continue;
		  ContentTags tagEntry = new ContentTags(date);
		  tagEntry.tags = ((ArrayList<String>) doc.getFieldValue("tags"));
		  tagEntry.setFeatures((ArrayList<String>) doc.getFieldValue("features"));
		  tagList.add(tagEntry);
	  }
  
	  return tagList;
	}

	public static void main(String[] args) throws Exception {
		//BLLCalculator calc = new BLLCalculator();
		//List<String> list = calc.getRankedTagList("33", "64", "http://css-kti.tugraz.at/solr", 6);
		//List<String> list = calc.getRankedTagList("weSPOT_demo", "43044", "http://192.168.222.30:8080/solr", 5);
		//System.out.println(list);
		//SolrDBClient cl = new SolrDBClient("http://192.168.222.30:8080/solr");
		//SolrDBClient cl = new SolrDBClient("http://localhost:8080/solr");
	//	Set<String> list = cl.getTagTextByUser("weSPOT_demo");
	//	System.out.println(list);
		//Map<String, Long> userTagCount = cl.getTagCountByUser("weSPOT_demo");
		String url = "http://localhost:8080/solr";
		String inquiryId = "41480"; 
		String userId ="weSPOT_lulu";
		String semanticFeatures ="is able to swim, is toxic";
		int number = 5;
		MinervaCalculatorGroup gcalc = new MinervaCalculatorGroup();
		printList(gcalc.getType(),gcalc.getRankedTagList(inquiryId, userId, url, semanticFeatures, number));
		MinervaCalculatorIndividual icalc = new MinervaCalculatorIndividual();
		printList(icalc.getType(),icalc.getRankedTagList(inquiryId, userId, url, semanticFeatures, number));
		BLLCalculatorIndividual bllCalcI = new BLLCalculatorIndividual();
		printList(bllCalcI.getType(), bllCalcI.getRankedTagList(inquiryId, userId, url, number));
		BLLCalculatorGroup bllCalcG = new BLLCalculatorGroup();
		printList(bllCalcG.getType(), bllCalcG.getRankedTagList(inquiryId, userId, url, number));
		
		//cl.getTagsByGroup("43044");
	}
	
	private static void printList(String algo, List<String> tags){
		System.out.println("----------------------------"+algo+"-------------------------------");
		for (String tag : tags)
			System.out.println("tag "+tag);
	}


}
