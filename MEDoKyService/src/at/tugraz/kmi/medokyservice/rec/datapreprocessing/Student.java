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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import old.StepUpIO;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;



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
