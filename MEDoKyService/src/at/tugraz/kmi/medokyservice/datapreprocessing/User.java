package at.tugraz.kmi.medokyservice.datapreprocessing;

public class User {

	String userName;
	ActivityManager activityManager;
	
	public User(String userName) {
		this.userName = userName;
		this.activityManager = new ActivityManager();
	}

	public void addActivityEvent(Activity activity){
		this.activityManager.addActivity(activity);
	}
		
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public ActivityManager getActivityManager(){
		return this.activityManager;
	}
}
