package at.tugraz.kmi.medokyservice.datapreprocessing;

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
