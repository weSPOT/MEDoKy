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

public class Activity {
	String time;
	String source;	
	String description;
	ActivityClassifier classifier;

	public Activity(ActivityClassifier classifier){
		this.classifier = classifier; 
	}
	
//	public Activity(ActivityClassifier classifier, String source, String description, String time){
//		this.source = source;
//		this.description = description;
//		this.time = time;
//		this.classifier = classifier;
//	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public ActivityClassifier getClassifier() {
		return classifier;
	}

	public void setClassifier(ActivityClassifier classifier) {
		this.classifier = classifier;
	}

}
