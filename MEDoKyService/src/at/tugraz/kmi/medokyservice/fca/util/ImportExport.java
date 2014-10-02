package at.tugraz.kmi.medokyservice.fca.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BlockingDeque;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.Database;
import at.tugraz.kmi.medokyservice.fca.db.FCAAbstract;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Course;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Domain;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAItemMetadata;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.IncidenceMatrix;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class ImportExport {
  private static final String SECTION_LO   = "learningObjects";
  private static final String SECTION_LO_L = "learningObjectsByLearners";
  private static final String SECTION_O    = "objects";
  private static final String SECTION_A    = "attributes";
  private static final String SECTION_U    = "users";
  private static final String SECTION_D    = "domains";
  private static final String SECTION_M    = "metadata";
  private static final String SECTION_C    = "courses";

  private static final String ID           = "id";
  private static final String O_ID         = "objectId";
  private static final String E_UID        = "externalUid";
  private static final String E_CID        = "externalCourseid";
  private static final String CID          = "creationId";
  private static final String NAME         = "name";
  private static final String DESCRIPTION  = "description";
  private static final String OWNER        = "owner";
  private static final String DATA         = "data";
  private static final String MAPPING      = "mapping";
  private static final String GLOBAL       = "global";
  private static final String APPROVED     = "approved";
  private static final String PARTICIPANTS = "participants";

  private String              file;
  private boolean             export;

  private Gson                gson;

  private Random              rand;

  private static JsonObject prepare(DataObject o) {
    JsonObject jso = new JsonObject();
    jso.addProperty(ID, o.getId());
    jso.addProperty(NAME, o.getName());
    jso.addProperty(DESCRIPTION, o.getDescription());
    return jso;
  }

  public ImportExport(String[] args) throws IllegalArgumentException {
    Params params = new Params();
    JCommander cmd = new JCommander(params, args);
    rand = new Random(Long.parseLong("Bad5eed", 16));
    if (params.file == null) {
      System.out.println("Import/Export FCA DB from/to JSON");
      cmd.setProgramName("java -jar ImportExport.jar");
      cmd.usage();
      throw new IllegalArgumentException("File name unspecified!");
    }
    this.gson = new Gson();
    this.file = params.file;
    this.export = params.export;
  }

  private JsonElement users2JSON() {

    BlockingDeque<User> users = Database.getInstance().getAll(User.class);
    JsonObject block = new JsonObject();
    for (User u : users) {
      JsonObject jso = prepare(u);
      jso.addProperty(E_UID, u.getExternalUid());
      block.add(Long.toString(u.getId()), jso);
    }
    return block;
  }

  private JsonElement learningObjects2JSON() {
    BlockingDeque<LearningObject> lobjects = Database.getInstance().getAll(LearningObject.class);
    JsonObject block = new JsonObject();
    for (LearningObject o : lobjects) {
      JsonObject jso = prepare(o);
      jso.addProperty(DATA, o.getData());
      jso.addProperty(OWNER, o.getOwner().getId());
      block.add(Long.toString(o.getId()), jso);
    }
    return block;
  }

  private <E extends FCAAbstract> JsonElement abstracts2JSon(Class<E> type) {
    BlockingDeque<E> objects = Database.getInstance().getAll(type);
    JsonObject block = new JsonObject();
    for (E o : objects) {
      JsonObject jso = prepare(o);
      jso.addProperty(CID, o.getCreationId());
      LinkedList<Long> loIds = new LinkedList<Long>();
      LinkedList<Long> loByLearnerIds = new LinkedList<Long>();
      for (LearningObject lo : o.getLearningObjects()) {
        loIds.add(lo.getId());
      }
      for (LearningObject lo : o.getLearningObjectsByLearners()) {
        loByLearnerIds.add(lo.getId());
      }
      jso.add(SECTION_LO, gson.toJsonTree(loIds, new TypeToken<List<Long>>() {
      }.getType()));
      jso.add(SECTION_LO_L, gson.toJsonTree(loByLearnerIds, new TypeToken<List<Long>>() {
      }.getType()));
      block.add(Long.toString(o.getId()), jso);
    }
    return block;
  }

  private JsonElement objects2JSON() {
    return abstracts2JSon(FCAObject.class);
  }

  private JsonElement attributes2JSON() {
    return abstracts2JSon(FCAAttribute.class);
  }

  private JsonElement metadata2JSON() {
    BlockingDeque<FCAItemMetadata> lobjects = Database.getInstance().getAll(FCAItemMetadata.class);
    JsonObject block = new JsonObject();
    for (FCAItemMetadata o : lobjects) {
      JsonObject jso = prepare(o);
      jso.addProperty(O_ID, o.getItemID());
      LinkedList<Long> loIds = new LinkedList<Long>();
      for (LearningObject lo : o.getLearningObjects()) {
        loIds.add(lo.getId());
      }
      LinkedList<Long> loByLernerIds = new LinkedList<Long>();
      for (LearningObject lo : o.getLearningObjectByLearner()) {
        loByLernerIds.add(lo.getId());
      }
      jso.add(SECTION_LO, gson.toJsonTree(loIds, new TypeToken<List<Long>>() {
      }.getType()));
      jso.add(SECTION_LO_L, gson.toJsonTree(loByLernerIds, new TypeToken<List<Long>>() {
      }.getType()));
      block.add(Long.toString(o.getId()), jso);
    }
    return block;
  }

  private JsonElement domains2JSON() {
    BlockingDeque<Domain> domains = Database.getInstance().getAll(Domain.class);
    JsonArray jsD = new JsonArray();
    for (Domain d : domains) {
      IncidenceMatrix mat = d.getMapping();
      Map<FCAObject, Set<FCAAttribute>> mapping = mat.getObjects();
      JsonObject jso = prepare(d);
      JsonObject jsM = new JsonObject();
      for (long mId : mat.getItemMetadata().keySet()) {
        jsM.addProperty(Long.toString(mId), mat.getItemMetadata().get(mId).getId());
      }
      jso.add(SECTION_M, jsM);
      jso.addProperty(OWNER, d.getOwner().getId());
      jso.addProperty(GLOBAL, d.isGlobal());
      jso.addProperty(APPROVED, d.isApproved());
      JsonObject jsMapping = new JsonObject();
      for (FCAObject o : mapping.keySet()) {
        LinkedList<Long> aIds = new LinkedList<Long>();
        for (FCAAttribute a : mapping.get(o)) {
          aIds.add(a.getId());
        }
        jsMapping.add(Long.toString(o.getId()), gson.toJsonTree(aIds, new TypeToken<List<Long>>() {
        }.getType()));
      }
      jso.add(MAPPING, jsMapping);
      jsD.add(jso);
    }
    return jsD;
  }

  private JsonElement courses2JSON() {
    BlockingDeque<Course> courses = Database.getInstance().getAll(Course.class);
    JsonArray jsC = new JsonArray();
    for (Course o : courses) {
      JsonObject jso = prepare(o);
      Set<Domain> domains = o.getDomains();
      LinkedList<Long> dIds = new LinkedList<Long>();
      for (Domain d : domains)
        dIds.add(d.getId());

      jso.add(SECTION_D, gson.toJsonTree(dIds, new TypeToken<List<Long>>() {
      }.getType()));

      jso.addProperty(OWNER, o.getOwnerId());
      jso.addProperty(E_CID, o.getExternalCourseID());
      List<Long> participants = new LinkedList<Long>();
      for (User u : o.getParticipants())
        participants.add(u.getId());

      jso.add(PARTICIPANTS, gson.toJsonTree(participants, new TypeToken<List<Long>>() {
      }.getType()));

      jsC.add(jso);
    }
    return jsC;
  }

  private String export() {
    JsonObject output = new JsonObject();

    output.add(SECTION_U, users2JSON());

    output.add(SECTION_LO, learningObjects2JSON());

    output.add(SECTION_O, objects2JSON());

    output.add(SECTION_A, attributes2JSON());

    output.add(SECTION_M, metadata2JSON());

    output.add(SECTION_D, domains2JSON());

    output.add(SECTION_C, courses2JSON());

    return output.toString();
  }

  private Map<Long, User> json2Users(JsonObject json) {
    HashMap<Long, User> users = new HashMap<Long, User>();
    JsonObject block = json.get(SECTION_U).getAsJsonObject();

    Set<Entry<String, JsonElement>> entries = block.entrySet();
    for (Entry<String, JsonElement> u : entries) {
      JsonObject jsUser = u.getValue().getAsJsonObject();
      System.out.println(jsUser.toString());
      String uname;
      if (jsUser.has(NAME))
        uname = jsUser.get(NAME).getAsString();
      else
        uname = "";
      String uDescr;
      if (jsUser.has(DESCRIPTION))
        uDescr = jsUser.get(DESCRIPTION).getAsString();
      else
        uDescr = "";
      String externalUID;
      if (jsUser.has(E_UID))
        externalUID = jsUser.get(E_UID).getAsString();
      else
        externalUID = "";
      User user = new User(externalUID, uname, uDescr);
      users.put(Long.parseLong(u.getKey()), user);
    }
    return users;
  }

  private Map<Long, LearningObject> json2LearningObjects(JsonObject json, Map<Long, User> users) {
    HashMap<Long, LearningObject> learningObjects = new HashMap<Long, LearningObject>();
    JsonObject block = json.get(SECTION_LO).getAsJsonObject();

    Set<Entry<String, JsonElement>> entries = block.entrySet();
    for (Entry<String, JsonElement> lo : entries) {
      JsonObject jsLo = lo.getValue().getAsJsonObject();
      User owner = users.get(Long.parseLong(jsLo.get(OWNER).getAsString()));

      LearningObject learningObject = new LearningObject(jsLo.get(NAME).getAsString(), jsLo.get(DESCRIPTION)
          .getAsString(), jsLo.get(DATA).getAsString(), owner);
      learningObjects.put(Long.parseLong(lo.getKey()), learningObject);
    }
    return learningObjects;
  }

  @SuppressWarnings("unchecked")
  private <E extends FCAAbstract> Map<Long, E> json2Absctract(JsonObject json,
      Map<Long, LearningObject> learningObjects, Class<E> type) {
    HashMap<Long, E> objects = new HashMap<Long, E>();
    JsonObject block = null;
    if (type == FCAObject.class)
      block = json.get(SECTION_O).getAsJsonObject();
    else if (type == FCAAttribute.class)
      block = json.get(SECTION_A).getAsJsonObject();
    Set<Entry<String, JsonElement>> entries = block.entrySet();
    for (Entry<String, JsonElement> o : entries) {
      JsonObject jsO = o.getValue().getAsJsonObject();
      E object;
      String creationId;
      if (!jsO.has(CID)) {
        double num = rand.nextDouble() * 10000;
        int val = (int) num;
        creationId = Integer.toString(val, 16);
      } else
        creationId = jsO.get(CID).getAsString();
      if (type == FCAObject.class)
        object = (E) new FCAObject(jsO.get(NAME).getAsString(), jsO.get(DESCRIPTION).getAsString(), creationId);
      else
        object = (E) new FCAAttribute(jsO.get(NAME).getAsString(), jsO.get(DESCRIPTION).getAsString(), creationId);
      Set<LearningObject> lObjs = new HashSet<LearningObject>();
      Set<LearningObject> lObjsByLearner = new HashSet<LearningObject>();
      Iterator<JsonElement> lOs = jsO.getAsJsonArray(SECTION_LO).iterator();
      while (lOs.hasNext()) {
        LearningObject lo = learningObjects.get((lOs.next().getAsLong()));
        lObjs.add(lo);
      }
      try {
        Iterator<JsonElement> lOsByLearner = jsO.getAsJsonArray(SECTION_LO_L).iterator();
        while (lOsByLearner.hasNext()) {
          LearningObject lo = learningObjects.get((lOsByLearner.next().getAsLong()));
          lObjsByLearner.add(lo);
        }
      } catch (Exception notAnError) {

      }
      object.setLearningObjectsByLearners(lObjsByLearner);
      object.setLearningObjects(lObjs);
      objects.put(Long.parseLong(o.getKey()), object);
    }
    return objects;
  }

  private Map<Long, FCAObject> json2Objects(JsonObject json, Map<Long, LearningObject> learningObjects) {
    return json2Absctract(json, learningObjects, FCAObject.class);
  }

  private Map<Long, FCAAttribute> json2Attributes(JsonObject json, Map<Long, LearningObject> learningObjects) {
    return json2Absctract(json, learningObjects, FCAAttribute.class);
  }

  private Map<Long, FCAItemMetadata> json2Metadata(JsonObject json, Map<Long, LearningObject> learningObjects) {
    Map<Long, FCAItemMetadata> result = new HashMap<Long, FCAItemMetadata>();
    JsonObject jsM = json.get(SECTION_M).getAsJsonObject();
    for (Entry<String, JsonElement> entry : jsM.entrySet()) {
      JsonObject val = entry.getValue().getAsJsonObject();
      JsonArray arr = val.get(SECTION_LO).getAsJsonArray();
      LinkedHashSet<LearningObject> l_objsByLernar = new LinkedHashSet<LearningObject>();
      try {
        JsonArray arrByLearner = val.get(SECTION_LO_L).getAsJsonArray();
        for (JsonElement loID : arrByLearner)
          l_objsByLernar.add(learningObjects.get(loID.getAsLong()));
      } catch (Exception notAnError) {

      }
      LinkedHashSet<LearningObject> l_objs = new LinkedHashSet<LearningObject>();
      for (JsonElement loID : arr)
        l_objs.add(learningObjects.get(loID.getAsLong()));
      result.put(Long.parseLong(entry.getKey()), new FCAItemMetadata(val.get(DESCRIPTION).getAsString(), val.get(O_ID)
          .getAsLong(), l_objs, l_objsByLernar));
    }
    return result;
  }

  private Map<Long, Domain> json2Domains(JsonObject json, Map<Long, FCAObject> objects,
      Map<Long, FCAAttribute> attributes, Map<Long, User> users, Map<Long, FCAItemMetadata> metadata) {
    Iterator<JsonElement> jsD = json.getAsJsonArray(SECTION_D).iterator();
    HashMap<Long, Domain> domains = new HashMap<Long, Domain>();
    while (jsD.hasNext()) {
      JsonObject d = jsD.next().getAsJsonObject();
      IncidenceMatrix mat = new IncidenceMatrix(d.get(NAME).getAsString(), d.get(DESCRIPTION).getAsString());
      Set<Entry<String, JsonElement>> mapping = d.get(MAPPING).getAsJsonObject().entrySet();
      JsonObject objMeta = d.get(SECTION_M).getAsJsonObject();
      for (Entry<String, JsonElement> map : mapping) {
        FCAObject object = objects.get(Long.parseLong(map.getKey()));
        FCAItemMetadata meta = metadata.get(objMeta.get(map.getKey()).getAsLong());
        System.out.println(object.getName());
        object.setLearningObjects(meta.getLearningObjects());
        object.setLearningObjectsByLearners(meta.getLearningObjectByLearner());
        String descr = object.getDescription();
        object.setDescription(meta.getDescription());
        mat.storeMetadata(object);
        object.setDescription(descr);
        Iterator<JsonElement> atts = map.getValue().getAsJsonArray().iterator();
        HashSet<FCAAttribute> attribs = new HashSet<FCAAttribute>();
        while (atts.hasNext()) {
          long id = atts.next().getAsLong();
          FCAAttribute attr = attributes.get(id);
          attribs.add(attr);
          meta = metadata.get(objMeta.get(Long.toString(id)).getAsLong());
          attr.setLearningObjects(meta.getLearningObjects());
          descr = attr.getDescription();
          attr.setDescription(meta.getDescription());
          mat.storeMetadata(attr);
          attr.setDescription(descr);
        }
        mat.add(object, attribs);
      }
      boolean global = (d.get(GLOBAL) == null) ? false : d.get(GLOBAL).getAsBoolean();
      Domain domain = new Domain(mat.getName(), mat.getDescription(), mat, users.get(d.get(OWNER).getAsLong()), global);
      if (d.get(APPROVED) == null) {
        domain.setApproved(true);
      } else {
        domain.setApproved(d.get(APPROVED).getAsBoolean());
      }
      domains.put(d.get(ID).getAsLong(), domain);
    }
    return domains;
  }

  private Set<Course> json2Courses(JsonObject json, Map<Long, Domain> domains, Map<Long, User> users) {
    Set<Course> courses = new HashSet<Course>();
    Iterator<JsonElement> jsC = json.getAsJsonArray(SECTION_C).iterator();
    while (jsC.hasNext()) {
      JsonObject c = jsC.next().getAsJsonObject();
      Iterator<JsonElement> domainIds = c.getAsJsonArray(SECTION_D).iterator();
      Set<Domain> ddomains = new HashSet<Domain>();
      while (domainIds.hasNext()) {
        Domain domain = domains.get(domainIds.next().getAsLong());
        ddomains.add(domain);
      }

      Iterator<JsonElement> userIds = c.getAsJsonArray(PARTICIPANTS).iterator();
      Set<User> uusers = new HashSet<User>();
      while (userIds.hasNext()) {
        User u = users.get(userIds.next().getAsLong());
        uusers.add(u);
      }
      Course course = new Course(c.get(NAME).getAsString(), c.get(DESCRIPTION).getAsString(), c.get(OWNER).getAsLong(),
          c.get(E_CID).getAsString());
      for (Domain d : ddomains)
        course.addDomain(d);
      for (User u : uusers)
        course.addParticipant(u);
      courses.add(course);
    }
    return courses;
  }

  private void imPort(JsonObject json) throws Exception {
    Database.getInstance().clear();

    Map<Long, User> users = json2Users(json);
    Database.getInstance().putAll(users.values(), false);

    Map<Long, LearningObject> learningObjects = json2LearningObjects(json, users);
    Database.getInstance().putAll(learningObjects.values(), false);

    Map<Long, FCAObject> objects = json2Objects(json, learningObjects);
    Database.getInstance().putAll(objects.values(), false);

    Map<Long, FCAAttribute> attributes = json2Attributes(json, learningObjects);
    Database.getInstance().putAll(attributes.values(), false);

    Map<Long, FCAItemMetadata> metadata = json2Metadata(json, learningObjects);
    Database.getInstance().putAll(metadata.values(), false);

    Map<Long, Domain> domains = json2Domains(json, objects, attributes, users, metadata);
    for (Domain domain : domains.values()) {
      Database.getInstance().putAll(domain.getFormalContext().getConcepts(), false);
    }
    Database.getInstance().putAll(domains.values(), false);

    Database.getInstance().putAll(json2Courses(json, domains, users), false);
    Database.getInstance().save();

  }

  private class Params {
    @Parameter
    private List<String> parameters = new ArrayList<String>();

    @Parameter(names = { "-f", "--file" }, description = "Source/Desitnation File")
    private String       file;

    @Parameter(names = { "-e", "--export" }, description = "Export mode, the FCA DB file needs to be located at webapps/at.tugraz.kmi.medoky.fca.db"
        + " -- If not set data will be imported to webapps/at.tugraz.kmi.medoky.fca.db")
    private boolean      export     = false;

  }

  public static void main(String[] args) {
    try {
      ImportExport ie = new ImportExport(args);
      File io = new File(ie.file);
      if (ie.export) {
        BufferedWriter out = new BufferedWriter(new FileWriter(io, false));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(ie.export());
        out.write(gson.toJson(je));
        out.close();
      } else {
        BufferedReader in = new BufferedReader(new FileReader(io));
        ie.imPort(new JsonParser().parse(in).getAsJsonObject());
        in.close();
      }
    } catch (Exception e) {
      System.err.println("Error: " + e.getLocalizedMessage());
      e.printStackTrace();
    }
  }
}
