package at.tugraz.kmi.medokyservice.datapreprocessing;

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
