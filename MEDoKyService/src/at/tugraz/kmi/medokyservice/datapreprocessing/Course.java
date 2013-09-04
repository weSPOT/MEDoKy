package at.tugraz.kmi.medokyservice.datapreprocessing;

import java.util.ArrayList;

import at.tugraz.kmi.medokyservice.io.StepUpIO;


public class Course {
	    
		public String name;
		private String full_count;
		private ArrayList <String> enrolledStudents;
		private StepUpIO reader;
		
		public Course(){
			reader = new StepUpIO();
			this.enrolledStudents = new ArrayList<String>();
		}
		
		public ArrayList<String> getEnrolledStudents() {
			return enrolledStudents;
		}

		public void setEnrolledStudents(ArrayList<String> enrolledStudents) {
			this.enrolledStudents = enrolledStudents;
		}

			
		public String getFull_count() {
			return full_count;
		}
		
		public void setFull_count(String full_count) {
			this.full_count = full_count;
		}
		
		public String toString(){
			return "context: "+this.name+", full_count: "+full_count;
		}

		public void initStudents(Students students) {
			System.out.println("init Students");
			ArrayList<String> courseStudents = reader.getStudents(this.name);
			System.out.println("got Students");
			for (String json : courseStudents){
		//		System.out.println("course: "+this.context+", json - "+json);
				this.enrolledStudents.addAll(students.addStudents(this.name, json));
			}
		}
}
