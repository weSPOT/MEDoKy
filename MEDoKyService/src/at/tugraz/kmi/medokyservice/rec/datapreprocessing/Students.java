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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class Students {
	JsonFactory jsonFactory;
	//private HashMap<String, Student> studentsPerCourse;
	private HashMap<String, Student> studentsPerName;
	
	public Students(){
		jsonFactory = new JsonFactory();
		//studentsPerCourse = new HashMap<String, Student>();
		studentsPerName = new HashMap<String, Student>();
	}
	
	public Collection<Student> getStudents() {
		return studentsPerName.values();
	}

	public Student getStudent(String name){
		return studentsPerName.get(name);
	}
	
	public List<String> addStudents(String course, String json){
	
		ArrayList<String> studentNames = new ArrayList<String>();
		
		try {
			JsonParser jp = jsonFactory.createJsonParser(json);
			jsonFactory.enable(JsonParser.Feature.ALLOW_COMMENTS);
			jp.nextToken();			
			while (jp.nextToken() != JsonToken.END_ARRAY){
				Student student = null;
				while (jp.nextToken() != JsonToken.END_OBJECT) {
					String fieldname = jp.getCurrentName();
					if ("username".equals(fieldname)) {
						jp.nextToken();
						String name = jp.getText();
						if (studentsPerName.containsKey(name))
							student = studentsPerName.get(name);
						else{
							student = new Student();
							student.setUserName(name);
						}	
					}
					else if ("full_count".equals(fieldname)){
						jp.nextToken();
						if (student!= null)
							student.setFull_count(jp.getText());
					}
				}
				if (student!= null){
					String name = student.userName;
					student.initCourseActivities(course);
					studentsPerName.put(name, student);
					studentNames.add(name);
				}
			}
			} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return studentNames;
	}
	
	
		
 public String toString(){
	 String studentsString = "";
	 int count = 0;
	 for (Student student : this.studentsPerName.values()){
		 count++;
		 studentsString = studentsString+"\n Student nbr "+count+" :";
		 studentsString = studentsString.concat(student.toString());
	 }
	 return studentsString;
 }	
 }
