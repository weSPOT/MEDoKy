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

public class LearningPeerRecommendation extends UserClassification {

	public LearningPeerRecommendation(String userId) {
		super(userId, RecommendationClassification.LearningPeer);
	}

	@Override
	public Set<Recommendation> calculate(String courseId) {
			Recommendation recommendation = new Recommendation(courseId, this.type, "undefined"); 
			recommendation.setRecommendation("Why don't you discuss your findings with Jane Doe");
			recommendation.setExplanation("Jane Doe has read similar resorces than you. You may find it helpful to share your perspectives");
			this.recommendations.add(recommendation);
		return this.recommendations;
	}

}
