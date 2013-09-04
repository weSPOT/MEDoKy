package at.tugraz.kmi.medokyservice.datapreprocessing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import at.tugraz.kmi.medokyservice.io.StepUpIO;



public class Student {

	JsonFactory jsonFactory;
	public String userName;
	public String full_count;
	private HashMap<String, Activities> activitiesPerCourse;
	//private HashMap<String, StudentInfo> infoPerActivity;
	private StepUpIO reader;
	
	public Student(){
		this.userName = "";
		this.full_count = "";
		jsonFactory = new JsonFactory();
		this.reader = new StepUpIO();
		this.activitiesPerCourse = new HashMap<String, Activities>();
	}
		
	public Activities getCourseActivities(String courseName){
		return this.activitiesPerCourse.get(courseName);
	}
	
	public void setUserName(String text) {
		this.userName = text;
	}

	public void setFull_count(String text) {
		this.full_count = text;
	}
	
	public String toString(){
		return "username: "+userName+", full_count: "+full_count;
	}
	
	public void initCourseActivities(String course){
		ArrayList<String> infos = this.reader.getStudentInfo(course, this.userName);
		Activities activities = new Activities();
		for(String info : infos){
			this.addInfo(info, activities);
		}
		this.activitiesPerCourse.put(course, activities);
	}	
	
	public boolean addInfo(String json, Activities activities){
		int counter = 0;
		try {
			JsonParser jp = jsonFactory.createJsonParser(json);
			jsonFactory.enable(JsonParser.Feature.ALLOW_COMMENTS);
			jp.nextToken();			
			while (jp.nextToken() != JsonToken.END_ARRAY){
				StudentInfo info = new StudentInfo();
				counter++;
				while (jp.nextToken() != JsonToken.END_OBJECT) {
					String fieldname = jp.getCurrentName();
					if ("verb".equals(fieldname)) {
						//student = new Student();
						jp.nextToken();
						//student.setUserName(jp.getText());
						info.setVerb(jp.getText());
					}
					else if ("object".equals(fieldname)){
						jp.nextToken();
						info.setObject(jp.getText());
					}
					else if ("starttime".equals(fieldname)){
						jp.nextToken();
						info.setStarttime(jp.getText());
					}
					else if ("endtime".equals(fieldname)){
						jp.nextToken();
						info.setEndtime(jp.getText());
					}
					else if ("target".equals(fieldname)){
						jp.nextToken();
						info.setTarget(jp.getText());
					}
					else if ("location".equals(fieldname)){
						jp.nextToken();
						info.setLocation(jp.getText());
					}
					else if ("context".equals(fieldname)){
						jp.nextToken();
						info.setContext(jp.getText());
					}
					else if ("originalrequest".equals(fieldname)){
						jp.nextToken();
						info.setOriginal(jp.getText());
					}
				}
				if (info!= null && info.verb!=null){
					activities.add(info);
				}
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		
		if (counter<=1)
			return false;
		
		return true;
	}
}
