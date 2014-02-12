package at.tugraz.kmi.medokyservice.fca.rest.conf;

public interface RestConfig {

  public static final String PATH_FCASERVICE = "/FCATool";

  public static final String KEY_RELATION = "relation";
  public static final String KEY_NAME = "name";
  public static final String KEY_DESCRIPTION = "description";
  public static final String KEY_ID = "id";
  public static final String KEY_DOMAIN = "domain";
  public static final String KE_VAL = "value";
  public static final String KEY_EXTERNALUID = "externalUID";
  public static final String KEY_CONCEPT = "concept";
  public static final String KEY_DOMAINID = "domainID";
  public static final String KEY_USER = "user";
  public static final String KEY_USERID = "uid";
  public static final String KEY_ISTEACHER = "isTeacher";

  public static final String PATH_DOMAINHEADERS = "domainHeaders";
  public static final String PATH_DOMAINS = "domains";
  public static final String PATH_LEARNERDOMAIN = "learnerDomain";
  public static final String PATH_DOMAIN = "domain";
  public static final String PATH_OBJECT = "object";
  public static final String PATH_OBJECTS = "objects";
  public static final String PATH_ATTRIBUTE = "attribute";
  public static final String PATH_ATTRIBUTES = "attributes";
  public static final String PATH_LEARNINGOBJECTS = "learningObjects";
  public static final String PATH_FORMALCONTEXT = "formalContext";
  public static final String PATH_IDENTIFY = "identify";
  public static final String PATH_CONCEPT = "concept";
  public static final String PATH_VALUATIONS = "valuations";
  public static final String PATH_UPDATEATTRIBUTE = PATH_DOMAIN + "/{" + KEY_DOMAINID + "}/" + PATH_ATTRIBUTE;
  public static final String PATH_UPDATEOBJECT = PATH_DOMAIN + "/{" + KEY_DOMAINID + "}/" + PATH_OBJECT;
  public static final String PATH_UPDATEATTRIBUTES = PATH_DOMAIN + "/{" + KEY_DOMAINID + "}/" + PATH_ATTRIBUTES;
  public static final String PATH_UPDATEOBJECTS = PATH_DOMAIN + "/{" + KEY_DOMAINID + "}/" + PATH_OBJECTS;

}
