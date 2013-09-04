package at.tugraz.kmi.medokyservice.datapreprocessing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import at.tugraz.kmi.medokyservice.io.StepUpIO;



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
							course.name = (jp.getText());
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
