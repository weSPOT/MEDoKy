package at.tugraz.kmi.medokyservice.fca.tests;

import java.io.IOException;
import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;
import at.tugraz.kmi.medokyservice.fca.rest.FCAService;
import at.tugraz.kmi.medokyservice.fca.rest.wrappers.DomainWrapper;

public class nullPointer {


	
	public static void main(String[] args) {
		long id = -1125899906810046L;
		FCAService service = new FCAService();
		service.getDomainHeaders("1125899906810046L");
		try {
			Set<User> learners = service.getLearnersForDomain(id);
			for (User learner: learners)
				System.out.println(learner.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

}
