package at.tugraz.kmi.medokyservice.bl;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.codehaus.jackson.map.ObjectMapper;

import at.tugraz.kmi.medokyservice.datapreprocessing.Course;
import at.tugraz.kmi.medokyservice.datapreprocessing.Courses;
import at.tugraz.kmi.medokyservice.recommendations.Recommendations;
import at.tugraz.kmi.medokyservice.resources.Info;
import at.tugraz.kmi.medokyservice.resources.LMSLayerIO;
import at.tugraz.kmi.medokyservice.resources.RecommendationId;


public class CoreLogic {

	private static CoreLogic coreLogic = null;
	private ReadWriteLock recommendation;
	private HashMap<String, Recommendations> userRecommendations; 
	private Courses courses;
	private LMSFactory lmsFactory;
	
	private CoreLogic(){
		userRecommendations = new HashMap<String, Recommendations>();
	    recommendation = new ReentrantReadWriteLock();
	    courses = new Courses();
	    lmsFactory = new LMSFactory();
	}
	
	// FIXME: find a good way to handle the multiple access to Courses -> update, get, ...
	public synchronized Courses getCourses(){
		return this.courses;
	}
	
	public static CoreLogic getInstance(){
		if (coreLogic == null){
			coreLogic = new CoreLogic();
		}
		return coreLogic;
	}

	
	private String generateRecommendationId(){
		String uuid = UUID.randomUUID().toString();
		while (this.userRecommendations.containsKey(uuid))
			uuid = UUID.randomUUID().toString();
		return uuid;
	}
	
	
	public RecommendationId triggerUserClassification(String userId, String courseId, String environment) {
		String uuid = this.generateRecommendationId();
		this.userRecommendations.put(uuid, new Recommendations());
		new SimpleUserClassificationThread(userId, courseId, environment, uuid).start();
		return new RecommendationId(uuid);
	}
	
	
	public void setRecommendation(String id, Recommendations recommendations){
					
		recommendation.writeLock().lock();
		try{
			userRecommendations.put(id, recommendations);
		}
		finally{
			recommendation.writeLock().unlock();
		}
	}

	
	public Recommendations getRecommendation(String recId) {
		//FIXME: add courseId to request
		Recommendations rec = new Recommendations();
		rec.setStatus(recId+" not in DB: "+userRecommendations.keySet().toString());
		// FIXME: find a good position for this!!
		
//		addRecommendation("No Recommendation for "+recommendationId+" available");
//		
//		if (recommendationId.equals("-1"))
//			return new Recommendations().getError();
//		
		recommendation.readLock().lock();
		
		try{
			if (userRecommendations.containsKey(recId))
				rec = userRecommendations.get(recId);
		}
		finally{
			recommendation.readLock().unlock();
		}
		
		return rec;
	}

	
	public Info triggerRecommendationCycle(String sourceId, String userId, String courseId) {
		//return "for source "+sourceId+" : "+triggerUserClassification(userId, courseId);
		Info info = new Info();
		LMSLayerIO lms = this.lmsFactory.getLMSInterface(sourceId);
		if (lms == null){
			info.setInfo("Source is not supported.");
			return info;
		}
		new UserClassificationThread(courseId, userId, this.lmsFactory.getLMSInterface(sourceId)).start();
		
		info.setInfo("User Classification for user "+userId+" in course "+courseId+" started.");
		return info;
	}
	
	
	public static void main(String[] args) throws Exception {
			CoreLogic logic = CoreLogic.getInstance();
			//Course course = logic.courses.getCourses().get(0);
			Course course = logic.courses.getCourse("chikul13");
			String studentName = course.getEnrolledStudents().get(0);
			ObjectMapper omapper = new ObjectMapper();
			
			//Info info =logic.triggerRecommendationCycle("TestLMS", studentName, course.name);
			Info info =logic.triggerRecommendationCycle("TestLMS", studentName, "chikul13");
			System.out.println(info.getInfo());
			
//			RecommendationId id = logic.triggerUserClassification(studentName, course.name, "mobile");
//			Recommendations r = new Recommendations();
//			System.out.println("id = "+id);
//			while (!r.getStatus().equals("complete")){
//				Thread.sleep(1000); 
//				r = logic.getRecommendation(id.getRecommendationId());
//				System.err.println(omapper.writeValueAsString(r));
//			}	
			
//			System.out.println(omapper.writeValueAsString(r));
			
			
			/*
			StepUpIO io = new StepUpIO();
			//io.getStudents("thesis12");
			//io.getStudentInfo("thesis12", "Menno Hochstenbach");
			Courses courses = logic.getCourses();
			Students allStudents = courses.getStudents();
			//courses.addCourses(io.getCourses());
			
			List<Course> courseList = courses.getCourses();
			int count = 0;
			System.out.println("\n///////////////////////////////////////////////////////////////\n");
			for (Course c : courseList){
				count++;
				System.out.println(count+" Course: "+c.name+" of "+courseList.size());
				
				ArrayList<String> students = c.getEnrolledStudents();
				count = 0;
				for (String student : students){
					count++;
					Student s = allStudents.getStudent(student);
					if(!s.userName.equals(student))
						throw new Exception("username wrong");
					Activities activities = s.getCourseActivities(c.name);
					int events = activities.getNumberOfEvents();
					System.out.println(count+" Student: "+student+" of "+students.size()+"with eventcount: "+events);
					System.out.println("comments: "+activities.getComments().size()+", posts: "+activities.getPosts().size()+", tweets: "+activities.getTweets().size()+", spend: "+activities.getSpends().size());
				}
			System.out.println("\n///////////////////////////////////////////////////////////////\n");
			}
			
			*/
			
//			Course course = courses.getCourse("thesis12");
//			System.out.println("\n///////////////////////////////////////////////////////////////\n");
//			ArrayList<String> students = course.getEnrolledStudents();
//			count = 0;
//			for (String student : students){
//				count++;
//				System.out.println(count+" Student: "+student+" of "+students.size());
//			}
//			System.out.println("\n///////////////////////////////////////////////////////////////\n");
//			
//			count =0;
//			Collection<Student> studentCollection = allStudents.getStudents();
//			for (Student single : studentCollection){
//				count++;
//				System.out.println(count+" Student: "+single.userName+" of "+studentCollection.size());
//			}	
//			//logic.triggerUserClassification("bram_gotink");
//			logic.tri ggerUserClassification("bram_gotinkdd");
//			String recommendation = null;
//			String recommendationTwo = null;
//			while (recommendation == null){
//				try {
//					System.out.println("Recommendation pending ...");
//					Thread.sleep(10000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				recommendation = logic.getRecommendation("bram_gotinkdd", "1");
//				recommendationTwo = logic.getRecommendation("bram", "1");
//			}
//			System.out.println(recommendation);
//			System.out.println(recommendationTwo);
	}
}
