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

import java.util.HashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.rec.Recommendation;
import at.tugraz.kmi.medokyservice.rec.RecommendationClassification;

public abstract class UserClassification {
	
	protected String userId;
	protected RecommendationClassification type;
	protected Set<Recommendation> recommendations;
	
	public UserClassification(String userId, RecommendationClassification type){
		this.userId = userId;
		this.type = type;
		this.recommendations = new HashSet<Recommendation>();
	}
	
	public abstract Set<Recommendation> calculate(String courseId); 
}
