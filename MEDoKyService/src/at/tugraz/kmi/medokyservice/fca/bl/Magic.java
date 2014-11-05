package at.tugraz.kmi.medokyservice.fca.bl;

import java.util.Set;

import at.tugraz.kmi.medokyservice.fca.FCAException;
import at.tugraz.kmi.medokyservice.fca.db.Database;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Course;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Domain;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerDomain;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;

public abstract class Magic {
  public static void createLearnerModel(User learner, String externalCourseId, String externalCourseOwnerID,
      String courseName) throws FCAException {
    Course c = Database.getInstance().getCourseByExternalID(externalCourseId);
    if (c == null) {

      c = new Course(courseName, "", externalCourseId);
      Database.getInstance().put(c, false);
    }
    createLearnerModelInternal(learner, c);

  }

  public static void createLearnerModel(User learner, String externalCourseId) throws FCAException {
    Course c = Database.getInstance().getCourseByExternalID(externalCourseId);
    if (c == null)
      throw new FCAException("Inquiry Does not Exist");
    createLearnerModelInternal(learner, c);

  }

  public static void createLearnerModel(long learnerId, long inquiryId) throws FCAException {
    Course c = Database.getInstance().get(inquiryId);
    if (c == null)
      throw new FCAException("Inquiry Does not Exist");
    createLearnerModelInternal(Database.getInstance().<User> get(learnerId), c);

  }

  private static void createLearnerModelInternal(User learner, Course c) {
    synchronized (c) {
      Set<Domain> domains = c.getDomains();
      for (Domain domain : domains) {
        domain.setMetadata();
        if (domain.containsLearnerDomain(learner.getId(), c.getId()))
          continue;
        LearnerDomain dom = new LearnerDomain(learner, domain);
        domain.addLearnerDomain(learner.getId(), c.getId(), dom);
        Database.getInstance().put(dom, false);
        try {
          Updater.update(dom);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

    }
  }

}
