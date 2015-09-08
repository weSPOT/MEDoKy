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
package at.tugraz.kmi.medokyservice.fca.bl;



import java.util.HashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.ErrorLogNotifier;
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
    	if(!domain.isApproved())
    		continue;
        domain.setMetadata();
        if (domain.containsLearnerDomain(learner.getId(), c.getId()))
          continue;
        LearnerDomain dom = new LearnerDomain(learner, domain);
        domain.addLearnerDomain(learner.getId(), c.getId(), dom);
        Database.getInstance().put(dom, false);
        try {
          Updater.update(dom);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    }
  }

  public static User createUnnamedUsersAsNeeded(String externalUID) {
    return createUserIfNeeded(externalUID, null, null, false);
  }

  public static User createOrIdentifyUser(String externalUID, String name, String description) {
    return createUserIfNeeded(externalUID, name, description, true);
  }

  private static User createUserIfNeeded(String externalUID, String name, String description, boolean setInfo) {
    User u = Database.getInstance().getUserByExternalUID(externalUID);
    if (u == null) {
      u = new User(externalUID, name, description);
    } else {
      if (setInfo) {
        u.setName(name);
        u.setDescription(description);
      }
    }
    Database.getInstance().put(u, false);
    return u;
  }

  public static void setDomainAdmins(String externalCourseID, String[] courseAdminIDs) {
    Course c = Database.getInstance().getCourseByExternalID(externalCourseID);
    if (c == null)
      return;
    Set<User> owners = new HashSet<User>();
    for (String eUID : courseAdminIDs) {
      if (eUID != null)	
    	  owners.add(createUnnamedUsersAsNeeded(eUID));
      else
    	  ErrorLogNotifier.errorEmail("admin with ID null appeared in method setDomainAdmins");
    }
    for (Domain d : c.getDomains()) {	
      d.setOwners(owners);
    }
  }
}
