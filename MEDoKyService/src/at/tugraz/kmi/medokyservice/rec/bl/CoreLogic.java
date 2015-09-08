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
package at.tugraz.kmi.medokyservice.rec.bl;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import old.LMSLayerIO;

import org.codehaus.jackson.map.ObjectMapper;

import at.tugraz.kmi.medokyservice.ErrorLogNotifier;
import at.tugraz.kmi.medokyservice.rec.Recommendation;
import at.tugraz.kmi.medokyservice.rec.Recommendations;
import at.tugraz.kmi.medokyservice.rec.datapreprocessing.Courses;
import at.tugraz.kmi.medokyservice.rec.resources.Info;
import at.tugraz.kmi.medokyservice.rec.resources.RecommendationId;
import at.tugraz.kmi.medokyservice.rec.tags.TagRecommendations;




/**
 * @author Simone Kopeinik
 * This class is implemented as a singleton. It is the container class that handles web-service requests, 
 * the reading of data from the LMS and the Recommendation calculation.	
 *
 */


public class CoreLogic {

	private static CoreLogic coreLogic = null;
	private ReadWriteLock recommendation;
	private HashMap<String, Recommendations> userRecommendations; 
	private Courses courses;
	private LMSFactory lmsFactory;
	
	private CoreLogic(){
		userRecommendations = new HashMap<String, Recommendations>();
	    recommendation = new ReentrantReadWriteLock();
	    //TODO: update data retrieval and init this. 
	    //courses = new Courses();
	    lmsFactory = new LMSFactory();
	}
	
	// FIXME: find a good way to handle the multiple access to Courses -> update, get, ...
	/**
	 * @return a list of Courses information is available for
	 */
	public synchronized Courses getCourses(){
		return this.courses;
	}
	
	/**
	 * @return the Core Logic object 
	 */
	public static CoreLogic getInstance(){
		if (coreLogic == null){
			coreLogic = new CoreLogic();
		}
		return coreLogic;
	}

	
	/**
	 * @return a generated UUID to identify the recommendation request. this id is given to enquirer and need to be passed in 
	 * the next request.
	 */
	private String generateRecommendationId(){
		String uuid = UUID.randomUUID().toString();
		while (this.userRecommendations.containsKey(uuid))
			uuid = UUID.randomUUID().toString();
		return uuid;
	}
	
	
	/**
	 * @param userId 
	 * @param courseId
	 * @param number - the number of recommendations requested
	 * @param environment - specifies the type of learning environment (mobile, desktop)
	 * @return a unique id to identify the recommendation request in the next call
	 */
	public RecommendationId triggerUserClassification(String userId, String courseId, int number, String environment) {
		String uuid = this.generateRecommendationId();
		this.userRecommendations.put(uuid, new Recommendations());
		new SimpleUserClassificationThread(userId, courseId, number, environment, uuid).start();
		return new RecommendationId(uuid);
	}
	
	/**
	 * @param userId 
	 * @param courseId
	 * @param number - the number of recommendations requested
	 * @param environment - specifies the type of learning environment (mobile, desktop)
	 * @param type
	 * @return a unique id to identify the recommendation request in the next call
	 */

	public RecommendationId triggerUserClassification(String userId,
			String courseId, String environment, String type) {
		String uuid = this.generateRecommendationId();
		this.userRecommendations.put(uuid, new Recommendations());
		new SimpleUserClassificationThread(userId, courseId, 3, environment, uuid).start();
		//new SpecifiedUserClassificationThread(userId, courseId, environment, type, uuid).start();
		return new RecommendationId(uuid);
	}

	
	
	/**
	 * @param id
	 * @param recommendations
	 */
	public void setRecommendation(String id, Recommendations recommendations){
					
		recommendation.writeLock().lock();
		try{
			userRecommendations.put(id, recommendations);
		}
		finally{
			recommendation.writeLock().unlock();
		}
	}

	
	/**
	 * @param recId - the id that was returned by the method {@link triggerUserClassification(String userId, String courseId, int number, String environment)}
	 * @return List of Recommendation objects calculated for a user
	 */
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

	
	/**
	 * A recommendation cycle is started that recalls the source identified by the given sourceId, after the user has been 
	 * classified and useful recommendations identified. 
	 * @param sourceId - specifies the system that will be recalled with the recommendations.
	 * @param userId
	 * @param courseId
	 * @return a text that specifies whether the cycle has been started successfully or not
	 */
	public Info triggerRecommendationCycle(String sourceId, String userId, String courseId) {
		//return "for source "+sourceId+" : "+triggerUserClassification(userId, courseId);
		Info info = new Info();
		LMSLayerIO lms = this.lmsFactory.getLMSInterface(sourceId);
		if (lms == null){
			info.setInfo("Source is not supported.");
			return info;
		}
		ErrorLogNotifier.error("In Core Logic, calls UserClassificationThread. This should not be happening!!");
		new UserClassificationThread(courseId, userId, this.lmsFactory.getLMSInterface(sourceId)).start();
		info.setInfo("User Classification for user "+userId+" in course "+courseId+" started.");
		return info;
	}

/*	public TagRecommendations getTagRecommendations(String userId,
			String courseId) {
		// TODO Auto-generated method stub
		TagRecommendations tr = new TagRecommendations();
		tr.setRecommendations();
		return tr;
	}*/
	
	
	public static void main(String[] args) throws Exception {
	
		CoreLogic logic = CoreLogic.getInstance();
		RecommendationId id = logic.triggerUserClassification("10", "3", 7, "elgg");
		
		Recommendations recs = logic.getRecommendation(id.getRecommendationId());
		
		
		for (Recommendation rec : recs.getRecommendations()){
			System.out.println(rec.getRecommendation()+" because "+rec.getExplanation());
		}
		
		RecommendationId secid = logic.triggerUserClassification("10", "20", 6, "mobile");
		Recommendations r = new Recommendations();
		ObjectMapper omapper = new ObjectMapper();
		System.out.println("id = "+secid);
		while (!r.getStatus().equals("complete")){
			Thread.sleep(1000); 
			r = logic.getRecommendation(secid.getRecommendationId());
			System.err.println(omapper.writeValueAsString(r));
		}	
		
		
		System.out.println(omapper.writeValueAsString(r));
		
	//	TagRecommendations tr = logic.getTagRecommendations("n", "e");
	//	System.out.println(omapper.writeValueAsString(tr));
		
	/*	
			StepUpIO io = new StepUpIO();
			String param = URLEncoder.encode("http://wespot.kmi.open.ac.uk/profile/michellebachler", "UTF-8");

			io.getStudentInfo("elgg", "test");
		
			CoreLogic logic = CoreLogic.getInstance();
			//Course course = logic.courses.getCourses().get(0);
			Course course = logic.courses.getCourse("chikul13");
			String studentName = course.getEnrolledStudents().get(0);
			ObjectMapper omapper = new ObjectMapper();
			
			//Info info =logic.triggerRecommendationCycle("TestLMS", studentName, course.name);
			Info info =logic.triggerRecommendationCycle("TestLMS", studentName, "chikul13");
			System.out.println(info.getInfo());
	*/		
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
