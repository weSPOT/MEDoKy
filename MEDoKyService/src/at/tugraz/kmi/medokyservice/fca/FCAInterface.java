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

package at.tugraz.kmi.medokyservice.fca;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import at.tugraz.kmi.medokyservice.fca.bl.Magic;
import at.tugraz.kmi.medokyservice.fca.bl.Updater;
import at.tugraz.kmi.medokyservice.fca.db.Database;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Concept;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Course;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Domain;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAItemMetadata;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerConcept;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerDomain;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerLattice;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;

public abstract class FCAInterface {

  public static Collection<LearningObject> getLOsFromInquiry(long inquiryId) {
    synchronized (Database.getInstance()) {
      Course c = Database.getInstance().get(inquiryId);
      HashSet<LearningObject> result = new HashSet<LearningObject>();
      for (Domain d : c.getDomains()) {
        d.setMetadata();
        Collection<FCAItemMetadata> metadata = d.getMapping().getItemMetadata().values();
        for (FCAItemMetadata data : metadata) {
          result.addAll(data.getLearningObjects());
        }
      }
      return result;
    }
  }

  public static Long getLearnerID(String learnerId) {
    User u = Magic.createUnnamedUsersAsNeeded(learnerId);
    try {
      Database.getInstance().save();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return u.getId();
  }

  public static Long getInquiryID(String learnerId) throws FCAException {
    Course c = Database.getInstance().getCourseByExternalID(learnerId);
    if (c == null)
      throw new FCAException("Inquiry does not Exists");
    return c.getId();
  }

  public static Collection<LearnerLattice> getLearnerModel(long inquiryId, long learnerId) throws FCAException {
    Course c = Database.getInstance().get(inquiryId);
    Magic.createLearnerModel(learnerId, inquiryId);
    HashSet<LearnerLattice> result = new HashSet<LearnerLattice>();
    for (Domain d : c.getDomains()) {
      if (d.containsLearnerDomain(learnerId, inquiryId)) {
        LearnerDomain domain = d.getLearnerDomain(learnerId, inquiryId);
        domain.setMetadata();
        result.add(domain.getFormalContext());
      }
    }
    return result;
  }

  public static Collection<Concept> getNextLearningConcepts(long inquiryId, long learnerId) {
    Course c = Database.getInstance().get(inquiryId);
    HashSet<Long> resultIds = new HashSet<Long>();
    for (Domain d : c.getDomains()) {
      if (d.containsLearnerDomain(learnerId, inquiryId)) {
        LearnerLattice lattice = d.getLearnerDomain(learnerId, inquiryId).getFormalContext();
        for (LearnerConcept concept : lattice.getConcepts()) {
          if (concept.getPercentagedValuations()[0] != 0f || concept.getPercentagedValuations()[0] != 0f) {
            resultIds.add(concept.getDomainConceptId());
          }
        }
      }
    }
    return Database.getInstance().get(resultIds);
  }

  public static void setVisitedLO(long inquiryId, long learnerId, long learningObjectId) throws Exception {
    Course c = Database.getInstance().get(inquiryId);
    for (Domain d : c.getDomains()) {
      if (d.containsLearnerDomain(learnerId, inquiryId)) {
        Updater.update(d.getLearnerDomain(learnerId, inquiryId), learningObjectId);
        return;
      }
    }
  }

  public static List<String> getDomainAttributes(long inquiryId) {
	    Course c = Database.getInstance().get(inquiryId);
	    HashSet<FCAAttribute> set = new HashSet<FCAAttribute>();
	    ArrayList<String> attributes = new ArrayList<String>();
	    
	    for (Domain d : c.getDomains()) {
	      set.addAll(d.getMapping().getDomainAttributes());
	    }
	    
	    for (FCAAttribute attr : set){
	    	attributes.add(attr.getName());
	    }
	    return attributes;
  }
  
  public static void setCompletedActivity(long inquiryId, long learnerId, Object activity) {
    // TODO: implement functionality;
  }
}
