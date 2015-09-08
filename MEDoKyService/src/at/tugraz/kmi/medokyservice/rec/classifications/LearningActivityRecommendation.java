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
package at.tugraz.kmi.medokyservice.rec.classifications;

import java.util.Set;

import at.tugraz.kmi.medokyservice.rec.Recommendation;
import at.tugraz.kmi.medokyservice.rec.RecommendationClassification;
import at.tugraz.kmi.medokyservice.rec.resources.UserClassification;

public class LearningActivityRecommendation extends UserClassification {

	public LearningActivityRecommendation(String userId) {
		super(userId, RecommendationClassification.LearningActivity);
	}

	@Override
	public Set<Recommendation> calculate(String courseId) {
		// TODO Auto-generated method stub
		Recommendation recommendation = new Recommendation(courseId, this.type, "undefined");
		int rnd = (int) (Math.random() * ( 3 - 0));
		switch (rnd) {
            case 0: recommendation.setRecommendation("Be sure to consider Data from different sources!");
    				recommendation.setExplanation("To get balanced information it is important to consider different data from different sources");
    				recommendation.setLink("http://scholar.google.com");
    				recommendation.setLinkTitle("Search engine");
                    break; 
            case 1: 
        			recommendation.setRecommendation("Before continuing in this phase you may read the help manual!");
        			recommendation.setExplanation("Your initial test scores indicated that you are a novice in this area.");
        			recommendation.setLink("http://dev.inquiry.wespot.net/pages/view/2314/phase-2-operationalisation");
        			recommendation.setLinkTitle("Help");
        			break;
            case 2:	
            	recommendation.setRecommendation("Engage in a blog discussion.");
    			recommendation.setExplanation("You have been very quite lately. Discussing your ideas with other might bring new insights.");
    			recommendation.setLink("http://dev.inquiry.wespot.net/discussion/owner/35744");
    			recommendation.setLinkTitle("Discussion");
		 }
		this.recommendations.add(recommendation);	
		return this.recommendations;
	}

}
