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
package at.tugraz.kmi.medokyservice.rec.resources;

import java.util.Random;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;
import at.tugraz.kmi.medokyservice.rec.Recommendation;
import at.tugraz.kmi.medokyservice.rec.RecommendationClassification;

public class LearningObjectRecommendation extends UserClassification {

	private int number;
	
	public LearningObjectRecommendation(String userId, int number){
		super(userId, RecommendationClassification.LearningResource);
		this.number = number;
	}
	
	//TODO: Refactor this!!
	
	@Override
	public Set<Recommendation> calculate(String inquiryId) {
		//Recommendation recommendation = new Recommendation();
		int id = Integer.valueOf(inquiryId);
		int even = 0;
		
		if (id%2==0)
			even = 1;
		
		switch (even) {
	            case 0: this.runFCARecommendation(inquiryId);
	            		break;
	            case 1: this.runRandomRecommendation(inquiryId);
	            		break;
	            //case 2: this.runFCARecommendation(inquiryId);
	            //case 3: this.runFCARecommendation(inquiryId);
	            default:this.runFCARecommendation(inquiryId);
	            		break;
			 }
		return this.recommendations;
	}
	
	
	
	private void runRandomRecommendation(String inquiryId) {
		System.out.println("In Random Recommendation");

		RandomRecommendation randomRec = new RandomRecommendation(); 
		// TODO: check whether inquiry and userId really are long values??
		Random rand = new Random(System.currentTimeMillis());
		Set<LearningObject> los = randomRec.calculateLoRecommendation(inquiryId, this.userId);
		
		if (los.size()==0)
			return;
		
		for (int n =0; n<number && los.size()>0; n++)
		{
		    Object[] loArray = los.toArray();
		    System.out.println(loArray.length+" length set"+los.size() );
		    LearningObject lo = (LearningObject) loArray[rand.nextInt(loArray.length)];
			Recommendation recommendation = new Recommendation(inquiryId, this.type, "Random");
			recommendation.setRecommendation("Take a look at this resourse.");
			recommendation.setExplanation("This resource is selected randomly.");
			recommendation.setLink(lo.getData());
			recommendation.setLinkTitle(lo.getName());
			recommendation.setLearningObjectId(lo.getId());
			this.recommendations.add(recommendation);
			los.remove(lo);
		}
		
	}

	private void runFCARecommendation(String inquiryId){
		FCARecommendation fcaRec = new FCARecommendation(); 
		// TODO: check whether inquiry and userId really are long values??
		System.out.println("In FCA Recommendation");
		Set<LearningObject> los = fcaRec.calculateLoRecommendation(inquiryId, this.userId);
		
		for (LearningObject lo : los){
			Recommendation recommendation = new Recommendation(inquiryId, this.type, "FCA");
			recommendation.setRecommendation("Take a look at this resourse.");
			recommendation.setExplanation("This resource is related to a concept in your learning context you have not learned yet.");
			recommendation.setLink(lo.getData());
			recommendation.setLinkTitle(lo.getName());
			recommendation.setLearningObjectId(lo.getId());
			this.recommendations.add(recommendation);
		}
	} 

}
