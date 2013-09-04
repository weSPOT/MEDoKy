package at.tugraz.kmi.medokyservice.resources;

import java.util.ArrayList;

public interface LALayerIO {

	public ArrayList<String> getCourses();
	public ArrayList<String> getStudents(String courseName);
	public ArrayList<String> getStudentInfo(String courseName, String studentName);
}
