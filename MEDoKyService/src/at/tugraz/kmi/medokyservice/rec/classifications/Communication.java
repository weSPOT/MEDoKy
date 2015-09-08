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

public class Communication extends UserClassification{

	
	public Communication(String userId) {
		super(userId, RecommendationClassification.LearningActivity);

	}

	public Set<Recommendation> calculate(String courseId){
		Courses courses = CoreLogic.getInstance().getCourses();
		Students allStudents = courses.getStudents();
		Course course = courses.getCourse(courseId);
		Recommendation recommendation = new Recommendation(courseId, type, "undefined");
		
		
		if(course==null){
			recommendation.setRecommendation("Sorry, there is no Information about this course available!");
			// TODO Error text should be handled with exceptions
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
	
		double studentNum = students.size();
		double sum = 0;
		ArrayList<Double> varianzBasis = new ArrayList<Double>();		
		for (String student : students){
			Student s = allStudents.getStudent(student);
			Activities activities = s.getCourseActivities(course.name);
			int numb = activities.getNumberOfEvents(); 
			sum += numb;
			varianzBasis.add((double) numb);	
		}
	
		double average = sum/studentNum;
		double min =0;
		double max = 0;
		double var =0;
		for (double val : varianzBasis){
			if (val<min)
				min = val;
			if (val>max)
				max = val;
			
			var += Math.pow((val-average), 2);
		}
	
		var = var/studentNum; 
		double std = Math.sqrt(var);
	
		int currentStudentEvents = currentStudent.getCourseActivities(courseId).getNumberOfEvents();
	
		String verb = "activities";
		if(currentStudentEvents<=1)
			verb = "activity";
	
		if (currentStudentEvents<(max-std))
			recommendation.setRecommendation("Use Tweets and Blogs to be more active. You only have "+currentStudentEvents+" "+verb+". Other users contributed up to "+max+" times.");
	
//	System.out.println("min"+min+" max"+max);
//	System.out.println("sum "+sum+", students "+studentNum+", average: "+average+", varianz: "+var+", std: "+std);
//	
	
		this.recommendations.add(recommendation);
		return this.recommendations;
	
	}
}
