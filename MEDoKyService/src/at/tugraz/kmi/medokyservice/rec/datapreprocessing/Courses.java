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
import java.util.List;

import old.StepUpIO;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;



public class Courses {

		JsonFactory jsonFactory;
		private List<Course> courses;
		private HashMap<String, Course> coursesByName;
		Students students;
		StepUpIO io; 
		
		public Courses(){
			this.jsonFactory = new JsonFactory();
			this.courses = new ArrayList<Course>();
			this.coursesByName = new HashMap<String, Course>();
			this.students = new Students();
			io = new StepUpIO();
			this.addCourses(io.getCourses());
		}
	
		
		public Students getStudents(){
			return this.students;
		}
		
		public List<Course> getCourses() {
			return courses;
		}

		public Course getCourse(String courseName){
			return this.coursesByName.get(courseName);
		}
		
		public void addCourses(String json){
			this.parseCourses(json);
		}
		
		public void addCourses(ArrayList<String> jsonPages){
			for (String json: jsonPages)
				this.parseCourses(json);
		}
		
		public void setCourses(List<Course> course) {
			this.courses = course;
		}
	
		private boolean parseCourses(String json){
			
			int counter = 0;
			
			try {
				JsonParser jp = jsonFactory.createJsonParser(json);
				jsonFactory.enable(JsonParser.Feature.ALLOW_COMMENTS);
				jp.nextToken();			
				while (jp.nextToken() != JsonToken.END_ARRAY){
					counter++;
					Course course = null;
					while (jp.nextToken() != JsonToken.END_OBJECT) {
						String fieldname = jp.getCurrentName();
						if ("context".equals(fieldname)) {
							course = new Course();
							jp.nextToken();
							String name = jp.getText();
							int start = name.indexOf(":");
							int end = name.indexOf(",");
							System.out.println("all "+name+" start: "+start+" end "+end);
							if (start!=-1 && end!=-1){
								name = name.substring(start+2, end-1);	
							}
							course.name = name;						
							System.out.println("NAAME ::: !!!! "+course.name);
						}
						else if ("full_count".equals(fieldname)){
							jp.nextToken();
							if (course!= null)
								course.setFull_count(jp.getText());
						}
					}
					if (course!= null){
						course.initStudents(this.students);
						if (!courses.contains(course))
							courses.add(course);
						System.out.println(course.toString());
						coursesByName.put(course.name, course);	
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
		
	 public String toString(){
		 String coursesString = "";
		 for (Course course : this.courses){
			 coursesString = coursesString+"\n course: ";
			 coursesString = coursesString.concat(course.toString());
		 }
		 return coursesString;
	 }	
}
