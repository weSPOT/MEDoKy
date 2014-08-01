package at.tugraz.kmi.medokyservice.classifications;

import java.util.ArrayList;

import at.tugraz.kmi.medokyservice.bl.CoreLogic;
import at.tugraz.kmi.medokyservice.datapreprocessing.Activities;
import at.tugraz.kmi.medokyservice.datapreprocessing.Course;
import at.tugraz.kmi.medokyservice.datapreprocessing.Courses;
import at.tugraz.kmi.medokyservice.datapreprocessing.Student;
import at.tugraz.kmi.medokyservice.datapreprocessing.Students;
import at.tugraz.kmi.medokyservice.recommendations.Recommendation;
import at.tugraz.kmi.medokyservice.recommendations.RecommendationClassification;

public class Communication extends UserClassification{

	
	public Communication(String userId) {
		super(userId, RecommendationClassification.LearningActivity);

	}

	public Recommendation calculate(String courseId){
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
	
		return recommendation;
	
	}
}
