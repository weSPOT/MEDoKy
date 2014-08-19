package at.tugraz.kmi.medokyservice.classifications;

import java.util.ArrayList;
import java.util.Set;

import at.tugraz.kmi.medokyservice.bl.CoreLogic;
import at.tugraz.kmi.medokyservice.datapreprocessing.Activities;
import at.tugraz.kmi.medokyservice.datapreprocessing.Course;
import at.tugraz.kmi.medokyservice.datapreprocessing.Courses;
import at.tugraz.kmi.medokyservice.datapreprocessing.Student;
import at.tugraz.kmi.medokyservice.datapreprocessing.Students;
import at.tugraz.kmi.medokyservice.recommendations.Recommendation;
import at.tugraz.kmi.medokyservice.recommendations.RecommendationClassification;

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
		Recommendation recommendation = new Recommendation(courseId, type);

		
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
