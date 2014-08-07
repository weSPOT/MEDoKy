package at.tugraz.kmi.medokyservice.fca.rest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import at.tugraz.kmi.medokyservice.fca.bl.Updater;
import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.Database;
import at.tugraz.kmi.medokyservice.fca.db.FCAAbstract;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Concept;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Course;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Domain;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.IncidenceMatrix;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerConcept;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerDomain;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;
import at.tugraz.kmi.medokyservice.fca.rest.conf.RestConfig;
import at.tugraz.kmi.medokyservice.fca.rest.wrappers.ConceptWrapper;
import at.tugraz.kmi.medokyservice.fca.rest.wrappers.CourseWrapper;
import at.tugraz.kmi.medokyservice.fca.rest.wrappers.DomainBlueprint;
import at.tugraz.kmi.medokyservice.fca.rest.wrappers.DomainWrapper;
import at.tugraz.kmi.medokyservice.fca.rest.wrappers.LatticeWrapper;
import at.tugraz.kmi.medokyservice.fca.rest.wrappers.LearningObjectWrapper;
import at.tugraz.kmi.medokyservice.fca.rest.wrappers.UpdateWrapper;
import at.tugraz.kmi.medokyservice.fca.rest.wrappers.UserWrapper;
import at.tugraz.kmi.medokyservice.fca.rest.wrappers.ValuationWrapper;
import at.tugraz.kmi.medokyservice.fca.util.NameComparator;

/**
 * FCA REST interface
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
@Path(RestConfig.PATH_FCASERVICE)
public class FCAService {

  /**
   * retrieves all {@link FCAObject}s stored in the database
   * 
   * @return all FCAObjects mapped by their id
   */
  @GET
  @Path(RestConfig.PATH_OBJECTS)
  @Produces(MediaType.APPLICATION_JSON)
  public Map<Long, FCAObject> getObjects() {
    log("getObjects");
    LinkedHashMap<Long, FCAObject> map = new LinkedHashMap<Long, FCAObject>();
    TreeSet<FCAObject> objects = new TreeSet<FCAObject>(new NameComparator());
    objects.addAll(Database.getInstance().getAll(FCAObject.class));
    for (FCAObject o : objects) {
      map.put(o.getId(), o);
    }
    return map;
  }

  /**
   * retrieves all {@link FCAAttribute}s stored in the database
   * 
   * @return all FCAAttributes mapped by their id
   */
  @GET
  @Path(RestConfig.PATH_ATTRIBUTES)
  @Produces(MediaType.APPLICATION_JSON)
  public Map<Long, FCAAttribute> getAttributes() {
    log("getAttributes");
    LinkedHashMap<Long, FCAAttribute> map = new LinkedHashMap<Long, FCAAttribute>();
    TreeSet<FCAAttribute> attributes = new TreeSet<FCAAttribute>(new NameComparator());
    attributes.addAll(Database.getInstance().getAll(FCAAttribute.class));
    for (FCAAttribute a : attributes) {
      map.put(a.getId(), a);
    }
    return map;
  }

  /**
   * retrieves all {@link LearningObject}s stored in the database
   * 
   * @return all LearningObjects mapped by their id
   */
  @GET
  @Path(RestConfig.PATH_LEARNINGOBJECTS)
  @Produces(MediaType.APPLICATION_JSON)
  public Map<Long, LearningObject> getLearningObjects() {
    log("getLearningObjects");
    LinkedHashMap<Long, LearningObject> map = new LinkedHashMap<Long, LearningObject>();
    TreeSet<LearningObject> learningObjects = new TreeSet<LearningObject>(new NameComparator());
    learningObjects.addAll(Database.getInstance().getAll(LearningObject.class));
    for (LearningObject a : learningObjects) {
      map.put(a.getId(), a);
    }
    return map;
  }

  /**
   * retrieves all {@link Domain}s stored in the database
   * 
   * @return all Domains mapped by their id
   */
  @GET
  @Path(RestConfig.PATH_DOMAINS)
  @Produces(MediaType.APPLICATION_JSON)
  public Map<Long, Domain> getDomains() {
    log("getDomains");
    LinkedHashMap<Long, Domain> map = new LinkedHashMap<Long, Domain>();
    for (Domain d : Database.getInstance().getAll(Domain.class)) {
      map.put(d.getId(), d);
    }
    return map;
  }

  @GET
  @Path(RestConfig.PATH_COURSE_DOMAINS)
  @Produces(MediaType.APPLICATION_JSON)
  public Map<String, Set<Long>> getCourseDomains() {
    HashMap<String, Set<Long>> result = new HashMap<String, Set<Long>>();
    Deque<Course> courses = Database.getInstance().getAll(Course.class);
    for (Course c : courses) {
      Set<Long> dIds = new HashSet<Long>();
      for (Domain d : c.getDomains())
        dIds.add(d.getId());
      result.put(c.getExternalCourseID(), dIds);
    }
    return result;
  }

  /**
   * retrieves a Map of Courses containing their Domains as
   * {@link DomainBlueprint}s containing only id, name and description for all
   * {@link Domain}s stored in the Database.
   * 
   * @return all {@link DomainBlueprint}s mapped by their id
   */
  @GET
  @Path(RestConfig.PATH_DOMAINHEADERS)
  @Produces(MediaType.APPLICATION_JSON)
  public Map<Long, CourseWrapper> getDomainHeaders(@QueryParam(RestConfig.KEY_ID) String externalCourseID) {
    log("getDomainHeaders");
    Set<Course> courses = new LinkedHashSet<Course>();
    LinkedHashMap<Long, CourseWrapper> result = new LinkedHashMap<Long, CourseWrapper>();

    // This is a botch, fixme!
    Course course = new Course("", "", 0, "-1");
    for (Domain d : Database.getInstance().getAll(Domain.class)) {
      if (d.isGlobal()) {
        course.addDomain(d);
        break;
      }
    }
    courses.add(course);

    if (externalCourseID.equals("-1")) {
      courses.addAll(Database.getInstance().getAll(Course.class));
    } else {
      Course c = Database.getInstance().getCourseByExternalID(externalCourseID);
      if (c != null)
        courses.add(c);
    }

    for (Course c : courses) {
      CourseWrapper wrapper = new CourseWrapper(c.getId(), c.getName(), c.getDescription(), c.getExternalCourseID());
      LinkedHashMap<Long, DomainBlueprint> map = new LinkedHashMap<Long, DomainBlueprint>();
      TreeSet<Domain> domains = new TreeSet<Domain>(new NameComparator());
      if (!externalCourseID.equals("-1")) {
        for (Domain d : c.getDomains()) {
          if (d.isApproved())
            domains.add(d);
        }
      } else {
        domains.addAll(c.getDomains());
      }
      for (Domain d : c.getDomains()) {
        map.put(d.getId(), new DomainBlueprint(d.getName(), d.getDescription(), d.getOwner(), d.isApproved()));
      }
      wrapper.domains = map;
      result.put(c.getId(), wrapper);
    }
    return result;
  }

  /**
   * retrieves a single {@link Domain} by id
   * 
   * @param id
   *          the id of the domain to retrieve
   * @return the requested Domain (wrapped)
   */
  @GET
  @Path(RestConfig.PATH_DOMAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public DomainWrapper getDomain(@QueryParam(RestConfig.KEY_ID) long id) {
    log("getDomain");

    Domain d = Database.getInstance().get(id);
    d.setMetadata();
    return new DomainWrapper(d);
  }

  /**
   * retrieves a single {@link LearnerDomain} by id
   * 
   * @param id
   *          the id of the domain to retrieve
   * @param uid
   *          the learner id
   * @return the requested LearnerDomain (wrapped)
   * @throws IOException
   * @throws FileNotFoundException
   */
  @GET
  @Path(RestConfig.PATH_LEARNERDOMAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public DomainWrapper getLearnerDomain(@QueryParam(RestConfig.KEY_ID) long id,
      @QueryParam(RestConfig.KEY_EXTERNALUID) String uid) throws FileNotFoundException, IOException {
    log("getLearnerDomain");
    Domain domain = Database.getInstance().<Domain> get(id);
    domain.setMetadata();
    User learner = Database.getInstance().getUserByExternalUID(uid);
    if (domain.getLearnerDomains().containsKey(learner.getId()))
      return new DomainWrapper(domain.getLearnerDomains().get(learner.getId()));
    LearnerDomain dom = new LearnerDomain(Database.getInstance().<User> get(learner.getId()), domain);
    domain.addLearnerDomain(learner.getId(), dom);
    Database.getInstance().put(dom, false);
    Database.getInstance().save();
    return new DomainWrapper(dom);
  }

  /**
   * retrieves a single {@link LearnerDomain} by id
   * 
   * @param id
   *          the id of the domain to retrieve
   * @param uid
   *          the learner id
   * @return the requested LearnerDomain (wrapped)
   * @throws IOException
   * @throws FileNotFoundException
   */
  @GET
  @Path(RestConfig.PATH_LEARNERLATTICE)
  @Produces(MediaType.APPLICATION_JSON)
  public LatticeWrapper getLearnerLattice(@QueryParam(RestConfig.KEY_ID) long id,
      @QueryParam(RestConfig.KEY_EXTERNALUID) long uid) throws FileNotFoundException, IOException {
    log("getLearnerDomain");
    Domain domain = Database.getInstance().<Domain> get(id);
    System.out.println(uid);
    System.out.println(domain.getOwner().getId());
    for (long d : domain.getLearnerDomains().keySet()) {
      System.out.println("learnerID: " + d);
      System.out.println("LearnerDomainId: " + domain.getLearnerDomains().get(d).getId());
    }
    // TODO: clean up
    if (!(domain.getLearnerDomains().containsKey(uid)))
      return new DomainWrapper(domain).formalContext;
    return new DomainWrapper(domain.getLearnerDomains().get(uid)).formalContext;
  }

  /**
   * retrieves a Domain's Learners by domain id
   * 
   * @param id
   *          the id of the domain
   * @return the learnes of the specified domain
   * @throws IOException
   * @throws FileNotFoundException
   */
  @GET
  @Path(RestConfig.PATH_LEARNERS)
  @Produces(MediaType.APPLICATION_JSON)
  public Set<User> getLearnersForDomain(@QueryParam(RestConfig.KEY_ID) long id) throws FileNotFoundException,
      IOException {
    log("getLearnerDomain");
    Domain domain = Database.getInstance().<Domain> get(id);
    Set<User> users = new TreeSet<User>(new Comparator<User>() {

      @Override
      public int compare(User o1, User o2) {
        return o1.getName().compareTo(o2.getName());
      }

    });
    for (long learnerId : domain.getLearnerIDs()) {
      users.add((User) Database.getInstance().get(learnerId));
    }
    return users;
  }

  @GET
  @Path(RestConfig.PATH_DOMAIN_VALUATIONS)
  @Produces(MediaType.APPLICATION_JSON)
  public Map<Long, ValuationWrapper> getValuations(@PathParam(RestConfig.KEY_ID) long learnerDomaindId) {
    log("getValuations");
    Map<Long, ValuationWrapper> valuations = new HashMap<Long, ValuationWrapper>();
    LearnerDomain domain = Database.getInstance().get(learnerDomaindId);
    for (LearnerConcept c : domain.getFormalContext().getConcepts()) {
      valuations.put(c.getId(), new ValuationWrapper(c.getPercentagedValuations(), c.getClickedLearningObjects()));
    }
    return valuations;
  }

  /**
   * Updates an {@link FCAObject} and returns the updated object
   * 
   * @param obj
   *          The FCAObject containing new information not present in the
   *          database
   * @return the updated FCAObject
   */
  // should be PUT, but MSIE screws this up!
  @POST
  @Path(RestConfig.PATH_UPDATEOBJECT)
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
  @Produces(MediaType.APPLICATION_JSON)
  public FCAObject updateObject(@PathParam(RestConfig.KEY_DOMAINID) long domainID, FCAObject obj) {
    log("updateObject");
    Domain d;
    DataObject o = Database.getInstance().get(domainID);
    boolean update = false;
    if (o instanceof Domain)
      d = ((Domain) o);
    else {
      d = Database.getInstance().get(((LearnerDomain) o).getDomainID());
      update = true;
    }
    FCAObject result = updateItem(d, obj, d);

    try {
      if (update) {
        Updater.update((LearnerDomain) Database.getInstance().get(domainID));
      }
      Database.getInstance().save();
    } catch (FileNotFoundException e) {

      e.printStackTrace();
    } catch (IOException e) {

      e.printStackTrace();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }

  @POST
  @Path(RestConfig.PATH_UPDATEOBJECTS)
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
  @Produces(MediaType.APPLICATION_JSON)
  public Set<FCAObject> updateObjects(@PathParam(RestConfig.KEY_DOMAINID) long domainID, Set<FCAObject> obj) {
    log("updateObject");
    Domain d = Database.getInstance().get(domainID);

    Set<FCAObject> result = new HashSet<FCAObject>(obj.size());
    for (FCAObject o : obj)
      result.add(updateItem(d, o, d));
    try {
      Database.getInstance().save();
    } catch (FileNotFoundException e) {

      e.printStackTrace();
    } catch (IOException e) {

      e.printStackTrace();
    }
    return result;
  }

  /**
   * Updates an {@link FCAAttribute} and returns the updated object
   * 
   * @param obj
   *          The FCAAttribute containing new information not present in the
   *          database
   * @return the updated FCAAttribute
   */
  // should be PUT, but MSIE screws this up!
  @POST
  @Path(RestConfig.PATH_UPDATEATTRIBUTE)
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
  @Produces(MediaType.APPLICATION_JSON)
  public FCAAttribute updateAttribute(@PathParam(RestConfig.KEY_DOMAINID) long domainID, FCAAttribute obj) {
    log("updateAttribute");
    Domain d;
    DataObject o = Database.getInstance().get(domainID);
    if (o instanceof Domain)
      d = ((Domain) o);
    else
      d = Database.getInstance().get(((LearnerDomain) o).getDomainID());
    FCAAttribute result = updateItem(d, obj, d);
    try {
      Database.getInstance().save();
    } catch (FileNotFoundException e) {

      e.printStackTrace();
    } catch (IOException e) {

      e.printStackTrace();
    }
    return result;
  }

  @POST
  @Path(RestConfig.PATH_UPDATEATTRIBUTES)
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
  @Produces(MediaType.APPLICATION_JSON)
  public Set<FCAAttribute> updateAttributes(@PathParam(RestConfig.KEY_DOMAINID) long domainID, Set<FCAAttribute> obj) {
    log("updateAttribute");
    Domain d = Database.getInstance().get(domainID);

    Set<FCAAttribute> result = new HashSet<FCAAttribute>(obj.size());
    for (FCAAttribute o : obj)
      result.add(updateItem(d, o, d));
    try {
      Database.getInstance().save();
    } catch (FileNotFoundException e) {

      e.printStackTrace();
    } catch (IOException e) {

      e.printStackTrace();
    }
    return result;
  }

  private <T extends FCAAbstract> T updateItem(Domain d, T obj, Domain domain) {
    T domainObject = Database.getInstance().get(obj.getId());
    if (d.isApproved()
        && (!(obj.getName().equals(domainObject.getName())) && !(obj.getDescription().equals(domainObject
            .getDescription())))) {
      throw new IllegalStateException("Cannot modify parts of an approved domain!");
    }
    domainObject.setName(obj.getName());
    updateLearningObject(obj);
    domainObject.setLearningObjects(obj.getLearningObjects());
    domain.getMapping().storeMetadata(obj);
    domainObject.setDescription(obj.getDescription());
    return domainObject;
  }

  /**
   * Updates a {@link Concept} (for example concept name, description, is this
   * concept part of the taxonomy, ...). Since Changes to a concept can have
   * effects on other concepts part of a {@link Domain} the whole domain needs
   * to be updated and returned to ensure current data.
   * 
   * @param concept
   *          the concept to update -- wrapped in a helper class reducing
   *          overhead
   * @return the updated domain this concept belongs to
   * @throws NullPointerException
   *           if the Concept is not in the database
   */
  // should be PUT, but MSIE screws this up!
  @POST
  @Path(RestConfig.PATH_CONCEPT)
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
  @Produces(MediaType.APPLICATION_JSON)
  public LatticeWrapper updateConcept(ConceptWrapper concept) throws NullPointerException {
    log("updateConcept");
    Domain d = Database.getInstance().get(concept.domainId);
    if (d.isApproved()) {
      throw new IllegalStateException("Cannot modify parts of an approved domain!");
    }
    Concept c = Database.getInstance().get(concept.id);
    if (c == null)
      throw new NullPointerException();
    c.setPartOfTaxonomy(concept.partOfTaxonomy);
    c.setName(concept.name);
    c.setDescription(concept.description);
    d.getFormalContext().updateTaxonomy();
    try {
      Database.getInstance().save();
    } catch (FileNotFoundException e) {

      e.printStackTrace();
    } catch (IOException e) {

      e.printStackTrace();
    }
    return new DomainWrapper(d).formalContext;
  }

  /**
   * Triggers a valuation update based on the indicators provided
   * 
   * @param valuations
   *          the valuation/updates
   * @return an updated version of the affected Domain's Lattice's valuations
   * @throws Exception
   */
  @POST
  @Path(RestConfig.PATH_VALUATIONS)
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
  @Produces(MediaType.APPLICATION_JSON)
  public Map<Long, ValuationWrapper> updateValuations(UpdateWrapper valuations) throws Exception {
    log("updateConcept");

    LearnerDomain domain = Database.getInstance().get(valuations.id);
    Updater.update(domain, valuations.learningObjectId);
    return getValuations(domain.getId());
  }

  /**
   * Identifies an external user. If the user does not exist a new {@link User}
   * is created referencing the external user by UID
   * 
   * @param user
   *          The user to identify wrapped in a helper class
   */
  @POST
  @Path(RestConfig.PATH_IDENTIFY)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.TEXT_PLAIN)
  public String identify(UserWrapper user) {
    log("identify");
    User u = Database.getInstance().getUserByExternalUID(user.externalUID);
    if (u != null)
      return Long.toString(u.getId());
    u = new User(user.externalUID, user.name, user.description);
    log("New User " + u.getId() + ", " + u.getExternalUid() + ", " + u.getName() + ", " + u.getDescription());
    Database.getInstance().put(u, true);
    return Long.toString(u.getId());

  }

  /**
   * Adds the specified {@link FCAObject}s to the database
   * 
   * @param objects
   *          the objects to add containing an invalid (but unique) id. A
   *          mapping of a valid IDs to the objects with invalid IDs is returned
   * @return a mapping of valid IDs to objects with invalid (but unique) IDs
   */
  @POST
  @Path(RestConfig.PATH_OBJECTS)
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
  @Produces(MediaType.APPLICATION_JSON)
  public Map<Long, FCAObject> createObjects(Set<FCAObject> objects) {

    log("createObjects");
    HashMap<Long, FCAObject> result = new HashMap<Long, FCAObject>();
    for (FCAObject object : objects) {

      FCAObject fcaObject = (FCAObject) Database.getInstance().getFCAItemBycreationId(Long.toHexString(object.getId()));
      if (fcaObject == null) {
        fcaObject = new FCAObject(object.getName(), object.getDescription(), Long.toHexString(object.getId()));
      }
      fcaObject.setLearningObjects(object.getLearningObjects());
      result.put(fcaObject.getId(), object);
      Database.getInstance().put(fcaObject, false);
    }
    try {
      Database.getInstance().save();
    } catch (FileNotFoundException e) {

      e.printStackTrace();
    } catch (IOException e) {

      e.printStackTrace();
    }
    return result;

  }

  /**
   * Adds the specified {@link LearningObject}s to the database
   * 
   * @param objects
   *          the objects to add containing an invalid (but unique) id. A
   *          mapping of a valid IDs to the objects with invalid IDs is returned
   * @return a mapping of valid IDs to objects with invalid (but unique) IDs
   */
  @POST
  @Path(RestConfig.PATH_LEARNINGOBJECTS)
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
  @Produces(MediaType.APPLICATION_JSON)
  public Map<Long, LearningObjectWrapper> createLearningObjects(Set<LearningObjectWrapper> objects) {
    log("createLearningObjects");

    HashMap<Long, LearningObjectWrapper> result = new HashMap<Long, LearningObjectWrapper>();
    for (LearningObjectWrapper object : objects) {

      if (Database.getInstance().getLearningObjectsByURL(object.data) == null) {
        LearningObject fcaObject = new LearningObject(object.name, object.description, object.data, Database
            .getInstance().getUserByExternalUID(object.externalUID));
        object.owner = Database.getInstance().getUserByExternalUID(object.externalUID);
        result.put(fcaObject.getId(), object);
        Database.getInstance().put(fcaObject, false);
      }
    }
    try {
      Database.getInstance().save();
    } catch (FileNotFoundException e) {

      e.printStackTrace();
    } catch (IOException e) {

      e.printStackTrace();
    }
    return result;

  }

  /**
   * Adds the specified {@link FCAAttribute}s to the database
   * 
   * @param attributes
   *          the attributes to add containing an invalid (but unique) id. A
   *          mapping of a valid IDs to the attributes with invalid IDs is
   *          returned
   * @return a mapping of valid IDs to attributes with invalid (but unique) IDs
   */
  @POST
  @Path(RestConfig.PATH_ATTRIBUTES)
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
  @Produces(MediaType.APPLICATION_JSON)
  public Map<Long, FCAAttribute> createAttributes(Set<FCAAttribute> attributes) {

    log("createAttributes");
    HashMap<Long, FCAAttribute> result = new HashMap<Long, FCAAttribute>();
    for (FCAAttribute attribute : attributes) {
      FCAAttribute fcaAttribute = (FCAAttribute) Database.getInstance().getFCAItemBycreationId(
          Long.toHexString(attribute.getId()));
      if (fcaAttribute == null) {
        fcaAttribute = new FCAAttribute(attribute.getName(), attribute.getDescription(), Long.toHexString(attribute
            .getId()));
      }
      fcaAttribute.setLearningObjects(attribute.getLearningObjects());
      result.put(fcaAttribute.getId(), attribute);
      Database.getInstance().put(fcaAttribute, false);
    }
    try {
      Database.getInstance().save();
    } catch (FileNotFoundException e) {

      e.printStackTrace();
    } catch (IOException e) {

      e.printStackTrace();
    }
    return result;

  }

  /**
   * Creates a new {@link Domain} and returns it based on the
   * {@link DomainBlueprint} provided
   * 
   * @param relation
   *          the DomainWrapper containing a mapping of {@link FCAObject}s to
   *          {@link FCAAttribute}s
   * @return a new domain based on the domain wrapper provided
   */
  @POST
  @Path(RestConfig.PATH_DOMAIN)
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
  @Produces(MediaType.APPLICATION_JSON)
  public DomainWrapper createDomain(DomainBlueprint relation) {

    log("createDomain");
    IncidenceMatrix matrix = new IncidenceMatrix(relation.name, relation.description);
    Deque<FCAAttribute> attributes = Database.getInstance().get(relation.attributes);
    matrix.initAttributes(attributes);
    Deque<FCAObject> objs = Database.getInstance().get(relation.objects);
    matrix.initObjects(objs);
    for (Long iobjectId : relation.mapping.keySet()) {
      matrix.add(Database.getInstance().<FCAObject> get(iobjectId),
          Database.getInstance().<FCAAttribute> get(relation.mapping.get(iobjectId)));
    }

    Domain domain = new Domain(relation.name, relation.description, matrix, Database.getInstance()
        .getUserByExternalUID(relation.externalUID), false);
    Database.getInstance().put(domain, false);
    Database.getInstance().putAll(domain.getFormalContext().getConcepts(), false);
    Course course = Database.getInstance().getCourseByExternalID(relation.externalCourseID);
    if (course == null) {
      course = new Course(relation.courseName, "", Database.getInstance().getUserByExternalUID(relation.externalUID)
          .getId(), relation.externalCourseID);
    }
    course.addDomain(domain);
    Database.getInstance().put(course, false);
    try {
      Database.getInstance().save();
    } catch (FileNotFoundException e) {

      e.printStackTrace();
    } catch (IOException e) {

      e.printStackTrace();
    }
    return new DomainWrapper(domain);

  }

  @POST
  @Path(RestConfig.PATH_UPDATEDOMAIN)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public DomainWrapper updateDomain(@PathParam(RestConfig.KEY_DOMAINID) long domainId, DomainBlueprint relation) {
    log("updateDomain");
    Domain d = Database.getInstance().get(domainId);
    if (d.isApproved()) {
      throw new IllegalStateException("Cannot modify parts of an approved domain!");
    }
    d.setName(relation.name);
    d.setDescription(relation.description);

    IncidenceMatrix matrix = d.getMapping();
    matrix.setName(relation.name);
    matrix.setDescription(relation.description);
    matrix.clear();
    Deque<FCAAttribute> attributes = Database.getInstance().get(relation.attributes);
    matrix.initAttributes(attributes);
    Deque<FCAObject> objs = Database.getInstance().get(relation.objects);
    matrix.initObjects(objs);

    for (Long iobjectId : relation.mapping.keySet()) {
      matrix.add(Database.getInstance().<FCAObject> get(iobjectId),
          Database.getInstance().<FCAAttribute> get(relation.mapping.get(iobjectId)));
    }

    Database.getInstance().removeAll(d.getFormalContext().getConcepts(), false);
    d.updateLattice();
    Database.getInstance().putAll(d.getFormalContext().getConcepts(), false);
    Database.getInstance().put(d, false);

    try {
      Database.getInstance().save();
    } catch (FileNotFoundException e) {

      e.printStackTrace();
    } catch (IOException e) {

      e.printStackTrace();
    }
    return new DomainWrapper(d);
  }

  @POST
  @Path(RestConfig.PATH_DOMAIN_SHARE)
  @Produces(MediaType.APPLICATION_JSON)
  public Boolean shareDomain(@PathParam(RestConfig.KEY_DOMAINID) long domainID,
      @PathParam(RestConfig.KEY_EXTERNALCOURSEID) String courseID, @PathParam(RestConfig.KEY_NAME) String courseName)
      throws Exception {
    Course c = Database.getInstance().getCourseByExternalID(courseID);
    Domain d = Database.getInstance().get(domainID);
    if (!d.isApproved())
      throw new Exception("Domain is not approved");
    if (c == null) {
      c = new Course(courseName, "",
          Database.getInstance().getUserByExternalUID(d.getOwner().getExternalUid()).getId(), courseID);
      Database.getInstance().put(c, false);
    }
    c.addDomain(d);
    Database.getInstance().save();
    return true;
  }

  @POST
  @Path(RestConfig.PATH_DOMAIN_APPROVE)
  @Produces(MediaType.APPLICATION_JSON)
  public DomainWrapper approveDomain(@PathParam(RestConfig.KEY_DOMAINID) long domainId) {
    Domain d = Database.getInstance().get(domainId);
    d.setApproved(true);
    try {
      Database.getInstance().save();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new DomainWrapper(d);
  }

  /**
   * returns a plain-text representation of this class's REST methods
   * 
   * @return a plain-text representation of this class's REST methods
   */
  @GET
  @Path("rest:syntax")
  @Produces(MediaType.TEXT_PLAIN)
  public String restSyntax() {
    Method[] methods = FCAService.class.getDeclaredMethods();
    Arrays.sort(methods, new Comparator<Method>() {
      @Override
      public int compare(Method o1, Method o2) {
        Path a1 = o1.getAnnotation(Path.class);
        Path a2 = o2.getAnnotation(Path.class);
        if (a1 == null || a2 == null) // null checks
          return 0;
        int cmp = a1.value().compareTo(a2.value());
        if (cmp == 0) {
          cmp = o1.getAnnotation(GET.class) == null ? 1 : -1;
        }
        return cmp;
      }
    });
    StringBuffer hlp = new StringBuffer(String.format("%-20s", "Return Type")).append(String.format("%-8s", "HTTP"))
        .append(String.format("%-30s", "PATH")).append(String.format("%-26s", "Return MediaType"))
        .append(String.format("%-20s", "Parameters")).append("\n\n");

    for (Method m : methods) {
      if (m.isAnnotationPresent(Path.class)) {
        Annotation[] annotations = m.getDeclaredAnnotations();
        int i = 0;
        hlp.append(String.format("%-20s", m.getReturnType().getSimpleName()));
        for (Annotation a : annotations) {
          if (!(a instanceof Consumes)) {
            StringTokenizer stAnnotaion = new StringTokenizer(a.toString(), "@.=()");
            String token = "";
            while (stAnnotaion.hasMoreTokens())
              token = stAnnotaion.nextToken();
            i = i > 0 ? 30 : 8;
            hlp.append(String.format("%-" + i + "s", token));
            i++;
          }
        }
        hlp.append("( ");

        Class<?>[] params = m.getParameterTypes();
        for (Class<?> param : params) {
          Annotation[] a = param.getDeclaredAnnotations();
          if (a.length > 0)
            for (Annotation ann : a) {

              StringTokenizer stAnnotaion = new StringTokenizer(ann.toString(), "@.=()");
              stAnnotaion.nextToken();
              stAnnotaion.nextToken();
              stAnnotaion.nextToken();
              hlp.append(String.format("%-20s", stAnnotaion.nextToken()));
              stAnnotaion.nextToken();
              try {
                hlp.append(String.format("%-20s", "\"" + stAnnotaion.nextToken() + "\""));
              } catch (Exception e) {
              }
            }
          hlp.append(String.format("%-20s", param.getSimpleName()));

        }
        hlp.append(")\n");
      }
    }
    // Database.getInstance().print();
    return hlp.toString();
  }

  private void log(String str) {
    Logger.getLogger("FCAService").log(Level.INFO, str);
  }

  private void updateLearningObject(FCAAbstract obj) {
    for (LearningObject lo : obj.getLearningObjects()) {
      LearningObject dbLo = Database.getInstance().get(lo.getId());
      if (dbLo == null)
        dbLo = new LearningObject(lo.getName(), lo.getDescription(), lo.getData(), lo.getOwner());
      else {
        dbLo.setName(lo.getName());
        dbLo.setDescription(lo.getDescription());
        dbLo.setData(lo.getData());
      }
      Database.getInstance().put(dbLo, false);
    }
  }
}