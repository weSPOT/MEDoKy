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
package at.tugraz.kmi.medokyservice.rec.datapreprocessing;

public enum ActivityClassifier {

	blog, 
	twitter,
	stepup,
	atinyarm,
	awarded,
	undefined;

	public static ActivityClassifier getFromString(String activityType) {
		if (activityType.equals("blog"))
			return blog;
		if (activityType.equals("twitter"))
			return twitter;
		if (activityType.equals("stepup"))
			return stepup;
		if (activityType.equals("atinyarm"))
			return atinyarm;
		if (activityType.equals("awarded"))
			return awarded;
			
		return undefined;
	}
}
