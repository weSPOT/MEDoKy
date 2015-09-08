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

import java.util.ArrayList;
import java.util.HashMap;

// one ActivityManager per classification

public class ActivityManager {
	HashMap<ActivityClassifier, ArrayList<Activity>> categoryToActivity;
	ArrayList<Activity> sortedActivities;
	HashMap<ActivityClassifier, ArrayList<String>> activityToSource;
		
	public ActivityManager(){
		categoryToActivity = new HashMap<ActivityClassifier, ArrayList<Activity>>();
		sortedActivities = new ArrayList<Activity>();
		activityToSource = new HashMap<ActivityClassifier, ArrayList<String>>();
	}
	
	public void addActivity(Activity activity){
		sortedActivities.add(activity);
		if (!categoryToActivity.containsKey(activity.classifier)){
			ArrayList<Activity> list = new ArrayList<Activity>();
			list.add(activity);
			categoryToActivity.put(activity.getClassifier(), list);
			return;
		}
		
	}

	public ArrayList<Activity> getActivities(){
		return sortedActivities;
	}
	
	public ArrayList<Activity> getActivitiesByCategory(ActivityClassifier classifier){
		if (this.activityToSource.containsKey(classifier))
			return this.categoryToActivity.get(classifier);
		return null;
	}
	
	public ArrayList<String> getUsedSourcesForActivity(ActivityClassifier classifier){
		if (this.activityToSource.containsKey(classifier))
			return this.activityToSource.get(classifier);
		return null;
	}
}
