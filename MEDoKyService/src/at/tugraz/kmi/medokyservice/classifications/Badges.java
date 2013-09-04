package at.tugraz.kmi.medokyservice.classifications;

import java.util.ArrayList;

import at.tugraz.kmi.medokyservice.bl.CoreLogic;
import at.tugraz.kmi.medokyservice.datapreprocessing.Activities;
import at.tugraz.kmi.medokyservice.datapreprocessing.Course;
import at.tugraz.kmi.medokyservice.datapreprocessing.Courses;
import at.tugraz.kmi.medokyservice.datapreprocessing.Student;
import at.tugraz.kmi.medokyservice.datapreprocessing.Students;
import at.tugraz.kmi.medokyservice.recommendations.Recommendation;

public class Badges extends UserClassification{

	Recommendation recommendation;
	
	public Badges(){
		this.recommendation = new Recommendation();
	}
	
	@Override
	public Recommendation calculate(String courseId, String userId) {
		Courses courses = CoreLogic.getInstance().getCourses();
		Students allStudents = courses.getStudents();
		Course course = courses.getCourse(courseId);
	
		if(course==null){
			recommendation.setRecommendation("Sorry, there is no Information about this course available!");
		return recommendation;
		}
	
		ArrayList<String> students = course.getEnrolledStudents();
		Student currentStudent = allStudents.getStudent(userId);
	
		if (currentStudent==null){
			recommendation.setRecommendation("Sorry, you are currently not enrolled in "+courseId+"!");
			return recommendation;
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
		return this.recommendation;
	}
	
}
