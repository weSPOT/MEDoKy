package at.tugraz.kmi.medokyservice.io;

import at.tugraz.kmi.medokyservice.datapreprocessing.Courses;
import at.tugraz.kmi.medokyservice.datapreprocessing.Student;
import at.tugraz.kmi.medokyservice.datapreprocessing.Students;
import at.tugraz.kmi.medokyservice.resources.Info;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class JsonRestClient {
	
	Client client; 
	
	public JsonRestClient () {
		client = Client.create();
	}
	
	public Courses getCourses(){
		String input = "{\"pag\":\"0\"}";
		String response = this.performPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getCourses", input);
		return new Courses();//new Courses(response);
	}
	
	public Students getStudents(){
		String input = "{\"pag\":\"0\"}";
		String response = this.performPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getCourses/thesis12", input);
		return new Students();//new Students(response);
	}
	
	public Student getStudentInfo(){
		String input = "{\"pag\":\"0\"}";
		String response = this.performPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getCourses/thesis12/Vienna", input);
		Student student = new Student();
		//student.addInfo(response);
		return student;
	}
	
	public void testMedoky(){
		WebResource webResource = client.resource("http://css-kmi.tugraz.at:8080/MEDoKyService/rest/triggerRecommendationCycle/sourceId/ARLearn/userId/stefaan.ternier@gmail.com/courseId/10");
		Info response = webResource.type("application/json").get(Info.class);
		System.out.println(response.getInfo());
		
	}
	
	
	public void sendRecommendationARLearn(){
		String input = "{\"recommendations\":[{\"recommendation\":\"work more\"}]}";
		this.performPost("http://wespot-arlearn.appspot.com/rest/MEDoKyCallback/userId/stefaan.ternier@gmail.com/courseId/123", input);
	}
	
	private String performPost(String address, String input){
	
		ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);
		WebResource webResource = client.resource(address);
		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
		     + response.getStatus());
		}

		System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);
		return output;	
	}
		
	
	public static void main(String[] args) {
	
		JsonRestClient myClient = new JsonRestClient();
		//myClient.testMedoky();
	//	myClient.init();
		Courses courses = myClient.getCourses();
		Students students = myClient.getStudents();
	//	StudentInfo info = myClient.getStudentInfo().getInfo();
		//myClient.sendRecommendationARLearn();
		System.out.println("did it "+courses.toString());
		System.out.println("did it "+students.toString());
//		System.out.println("did it "+info.toString());
//		ObjectMapper mapper = new ObjectMapper();
		//User user = mapper.readValue(new File("c:\\user.json"), User.class);
	}		 


}
