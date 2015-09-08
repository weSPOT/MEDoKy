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
package at.tugraz.kmi.medokyservice.fca.rest.conf;

public interface RestConfig {

  public static final String PATH_FCASERVICE        = "/FCATool";

  public static final String KEY_RELATION           = "relation";
  public static final String KEY_NAME               = "name";
  public static final String KEY_DESCRIPTION        = "description";
  public static final String KEY_ID                 = "id";
  public static final String KEY_DOMAIN             = "domain";
  public static final String KE_VAL                 = "value";
  public static final String KEY_EXTERNALUID        = "externalUID";
  public static final String KEY_CONCEPT            = "concept";
  public static final String KEY_DOMAINID           = "domainID";
  public static final String KEY_EXTERNALCOURSEID   = "externalCourseID";
  public static final String KEY_USER               = "user";
  public static final String KEY_USERID             = "uid";
  public static final String KEY_ISTEACHER          = "isTeacher";

  public static final String PATH_DOMAINHEADERS     = "domainHeaders";
  public static final String PATH_DOMAINS           = "domains";
  public static final String PATH_LEARNERS          = "learners";
  public static final String PATH_DOMAIN            = "domain";
  public static final String PATH_OBJECT            = "object";
  public static final String PATH_OBJECTS           = "objects";
  public static final String PATH_ATTRIBUTE         = "attribute";
  public static final String PATH_ATTRIBUTES        = "attributes";
  public static final String PATH_LEARNINGOBJECTS   = "learningObjects";
  public static final String PATH_FORMALCONTEXT     = "formalContext";
  public static final String PATH_IDENTIFY          = "identify";
  public static final String PATH_CONCEPT           = "concept";
  public static final String PATH_VALUATIONS        = "valuations";
  public static final String PATH_COURSE_DOMAINS    = "courseDomains";
  public static final String PATH_LEARNERDOMAIN     = "courses/{" + KEY_EXTERNALCOURSEID + "}/" + PATH_DOMAINS + "/{"
                                                        + KEY_DOMAINID + "}/" + PATH_LEARNERS + "/{" + KEY_EXTERNALUID
                                                        + "}";
  public static final String PATH_LEARNERLATTICE    = PATH_LEARNERDOMAIN+"/lattice";
  public static final String PATH_UPDATEATTRIBUTE   = PATH_DOMAIN + "/{" + KEY_DOMAINID + "}/" + PATH_ATTRIBUTE;
  public static final String PATH_UPDATEOBJECT      = PATH_DOMAIN + "/{" + KEY_DOMAINID + "}/" + PATH_OBJECT;
  public static final String PATH_UPDATEATTRIBUTES  = PATH_DOMAIN + "/{" + KEY_DOMAINID + "}/" + PATH_ATTRIBUTES;
  public static final String PATH_UPDATEOBJECTS     = PATH_DOMAIN + "/{" + KEY_DOMAINID + "}/" + PATH_OBJECTS;
  public static final String PATH_UPDATEDOMAIN      = PATH_DOMAIN + "/{" + KEY_DOMAINID + "}";
  public static final String PATH_DOMAIN_APPROVE    = PATH_DOMAIN + "/{" + KEY_DOMAINID + "}/approve";
  public static final String PATH_DOMAIN_SHARE      = PATH_DOMAIN + "/{" + KEY_DOMAINID + "}/shareTo/{"
                                                        + KEY_EXTERNALCOURSEID + "}/{" + KEY_NAME + "}";
  public static final String PATH_DOMAIN_VALUATIONS = "learnerDomain/{" + KEY_ID + "}/" + PATH_VALUATIONS;
  public static final String PATH_VIEWCONCEPT       = PATH_CONCEPT + "/{" + KEY_ID + "}/view";

}
