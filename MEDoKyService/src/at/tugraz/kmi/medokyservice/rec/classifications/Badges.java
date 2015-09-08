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
package at.tugraz.kmi.medokyservice.rec.classifications;

import java.util.ArrayList;
import java.util.Set;

import at.tugraz.kmi.medokyservice.rec.Recommendation;
import at.tugraz.kmi.medokyservice.rec.RecommendationClassification;
import at.tugraz.kmi.medokyservice.rec.bl.CoreLogic;
import at.tugraz.kmi.medokyservice.rec.datapreprocessing.Activities;
import at.tugraz.kmi.medokyservice.rec.datapreprocessing.Course;
import at.tugraz.kmi.medokyservice.rec.datapreprocessing.Courses;
import at.tugraz.kmi.medokyservice.rec.datapreprocessing.Student;
import at.tugraz.kmi.medokyservice.rec.datapreprocessing.Students;
import at.tugraz.kmi.medokyservice.rec.resources.UserClassification;

public class Badges extends UserClassification{


	public Badges(String userId){
		//FIXME: ADD PROPER TYPE
		super(userId, RecommendationClassification.LearningActivity);
	}
	
	@Override
	public Set<Recommendation> calculate(String courseId) {
		Courses courses = CoreLogic.getInstance().getCourses();
		Students allStudents = courses.getStudents();
		Course course = courses.getCourse(courseId);
		Recommendation recommendation = new Recommendation(courseId, type, "undefined");

		
		if(course==null){
			recommendation.setRecommendation("Sorry, there is no Information about this course available!");
			this.recommendations.add(recommendation);
			return this.recommendations;
		}
	
		ArrayList<String> students = course.getEnrolledStudents();
		Student currentStudent = allStudents.getStudent(userId);
	
		if (currentStudent==null){
			recommendation.setRecommendation("Sorry, you are currently not enrolled in "+courseId+"!");
			this.recommendations.add(recommendation);
			return this.recommendations;
		}
		
		int studentNum = students.size();
		int sum = 0;
		int max = 0;
		
		for (String student : students){
			Student s = allStudents.getStudent(student);
			Activities activities = s.getCourseActivities(course.name);
			int numb = activities.getAwards().size(); 
			sum += numb;
			if (numb>max)
				max  =numb;
		}
		
		double average = sum/studentNum;  
		
		
		if (max == 0)
			return null;
				
		int currentStudentAwards = currentStudent.getCourseActivities(course.name).getAwards().size(); 
		
		if (currentStudentAwards<average)
			recommendation.setRecommendation("The average Student achieved a number of "+average+" awards. You've got "+currentStudentAwards+". Keep up!");
		else
			recommendation.setRecommendation("The average Student achieved a number of "+average+" awards. You've got "+currentStudentAwards+". Well done!");
		this.recommendations.add(recommendation);
		return this.recommendations;
	}
	
}
