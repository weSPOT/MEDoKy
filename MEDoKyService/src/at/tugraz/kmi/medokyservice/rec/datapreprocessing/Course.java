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

import old.StepUpIO;


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
		
		@Override
		public String toString(){
			return "context: "+this.name+", full_count: "+full_count;
		}

		public void initStudents(Students students) {
			ArrayList<String> courseStudents = reader.getStudents(this.name);
	
			for (String json : courseStudents){
				this.enrolledStudents.addAll(students.addStudents(this.name, json));
			}
		}
}
