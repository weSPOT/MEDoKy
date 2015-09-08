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

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.solr.client.solrj.SolrServerException;

import at.tugraz.kmi.medokyservice.rec.Recommendations;
import at.tugraz.kmi.medokyservice.rec.bl.CoreLogic;
import at.tugraz.kmi.medokyservice.rec.resources.Info;
import at.tugraz.kmi.medokyservice.rec.resources.RecommendationId;
import at.tugraz.kmi.medokyservice.rec.tags.TagRecFactory;
import at.tugraz.kmi.medokyservice.rec.tags.TagRecommendations;
import at.tugraz.kmi.medokyservice.rec.tags.TagRecommender;



@Path("/")
public class TriggerMedoky{
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("trigger/userId/{userId}/courseId/{courseId}/number/{number}/environment/{environment}")
	public RecommendationId trigger(@DefaultValue("1") @PathParam("userId") String userId, @DefaultValue("1") @PathParam("courseId") String courseId, @DefaultValue("3") @PathParam("number") String number,@DefaultValue("textBased") @PathParam("environment") String environment, @DefaultValue("application/json") @HeaderParam("Accept") String accept) {
		//RecommendationId id = new RecommendationId("test");
		//return id;
		int intNumber = Integer.valueOf(number);
		return CoreLogic.getInstance().triggerUserClassification(userId, courseId, intNumber, environment);
	}
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("triggerRecommendation/userId/{userId}/courseId/{courseId}/environment/{environment}/type/{type}")
	public RecommendationId triggerRecommendation(@DefaultValue("1") @PathParam("userId") String userId, @DefaultValue("1") @PathParam("courseId") String courseId, @DefaultValue("textBased") @PathParam("environment") String environment, @DefaultValue("LearningResource") @PathParam("type") String type, @DefaultValue("application/json") @HeaderParam("Accept") String accept) {
		//RecommendationId id = new RecommendationId("test");
		return CoreLogic.getInstance().triggerUserClassification(userId, courseId, environment, type);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getRecommendation/recommendationId/{recommendationId}")
	public Recommendations getRecommendation(@DefaultValue("-1") @PathParam("recommendationId") String recommendationId, @DefaultValue("application/json") @HeaderParam("Accept") String accept) {
		return CoreLogic.getInstance().getRecommendation(recommendationId);
	}

		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("triggerRecommendationCycle/sourceId/{sourceId}/userId/{userId}/courseId/{courseId}")
	 public Info triggerRecommendationCycle(@DefaultValue("ARLearn") @PathParam("sourceId") String sourceId, @DefaultValue("1") @PathParam("userId") String userId, @DefaultValue("1") @PathParam("courseId") String courseId, @DefaultValue("application/json") @HeaderParam("Accept") String accept) {
		return CoreLogic.getInstance().triggerRecommendationCycle(sourceId, userId, courseId);
	 }

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getTagRecommendations/userId/{userId}/courseId/{courseId}")
	public TagRecommendations getTagRecommendations(@DefaultValue("Unknown") @PathParam("userId") String userId, @DefaultValue("1") @PathParam("courseId") String courseId, @DefaultValue("application/json") @HeaderParam("Accept") String accept) {
		String url = "http://localhost:8080/solr";
		TagRecommendations tr; 
    	TagRecommender calc;
    	int tagNumber = 5;
    	try {
			do{	
				//TODO: check if cast String TO Long works
				calc = TagRecFactory.getRandomTagRecommender(Long.valueOf(courseId));		
				tr = new TagRecommendations(calc.getRankedTagList(courseId, userId, url, tagNumber), calc.getType(), calc.hasFeatures());
			}while(calc.hasFeatures() && tr.getRecommendations().size()<=0);
		} catch (SolrServerException e) {
				e.printStackTrace();
				tr = new TagRecommendations();
		}
    	    	
		return tr;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	//@Path("getTagRecommendationsByType/userId/{userId}/courseId/{courseId}/algorithm/{algorithm}/features/{features}")
	@Path("getTagRecommendationsByType/userId/{userId}/courseId/{courseId}/algorithm/{algorithm}/{features:.*}")
	public TagRecommendations getTagRecommendationsByType(@DefaultValue("Unknown") @PathParam("userId") String userId, @DefaultValue("1") @PathParam("courseId") String courseId,@DefaultValue("Random") @PathParam("algorithm") String algorithm,@DefaultValue("Unknown") @PathParam("features") String features, @DefaultValue("application/json") @HeaderParam("Accept") String accept) {
		String url = "http://localhost:8080/solr";
		features = features.substring(features.indexOf("/")+1, features.length());
		TagRecommendations tr; 
    	TagRecommender calc;
    	int tagNumber = 5;
    	
    	try {
				calc = TagRecFactory.getTagRecommender(algorithm);
				tr = new TagRecommendations(calc.getRankedTagList(courseId, userId, url, features, tagNumber), calc.getType(), calc.hasFeatures());
			
		} catch (SolrServerException e) {
				e.printStackTrace();
				tr = new TagRecommendations();
		}
 		return tr;
	}



}
