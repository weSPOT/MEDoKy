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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.BlockingDeque;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.Database;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Domain;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
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
  private static final String SECTION_LO = "learningObjects";
  private static final String SECTION_O = "objects";
  private static final String SECTION_A = "attributes";
  private static final String SECTION_U = "users";
  private static final String SECTION_D = "domains";

  private static final String ID = "id";
  private static final String E_UID = "externalUid";
  private static final String NAME = "name";
  private static final String DESCRIPTION = "description";
  private static final String OWNER = "owner";
  private static final String DATA = "data";
  private static final String MAPPING = "mapping";

  private String file;
  private boolean export;

  private Gson gson;

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

  private String export() {
    JsonObject output = new JsonObject();

    BlockingDeque<User> users = Database.getInstance().getAll(User.class);
    JsonObject block = new JsonObject();
    for (User u : users) {
      JsonObject jso = prepare(u);
      jso.addProperty(E_UID, u.getExternalUid());
      block.add(Long.toString(u.getId()), jso);
    }
    output.add(SECTION_U, block);

    BlockingDeque<LearningObject> lobjects = Database.getInstance().getAll(
        LearningObject.class);
    block = new JsonObject();
    for (LearningObject o : lobjects) {
      JsonObject jso = prepare(o);
      jso.addProperty(DATA, o.getData());
      jso.addProperty(OWNER, o.getOwner().getId());
      block.add(Long.toString(o.getId()), jso);
    }
    output.add(SECTION_LO, block);

    BlockingDeque<FCAObject> objects = Database.getInstance().getAll(
        FCAObject.class);
    block = new JsonObject();
    for (FCAObject o : objects) {
      JsonObject jso = prepare(o);
      LinkedList<Long> loIds = new LinkedList<Long>();
      for (LearningObject lo : o.getLearningObjects()) {
        loIds.add(lo.getId());
      }
      jso.add(SECTION_LO, gson.toJsonTree(loIds, new TypeToken<List<Long>>() {
      }.getType()));
      block.add(Long.toString(o.getId()), jso);
    }
    output.add(SECTION_O, block);

    BlockingDeque<FCAAttribute> attributes = Database.getInstance().getAll(
        FCAAttribute.class);
    block = new JsonObject();
    for (FCAAttribute o : attributes) {
      JsonObject jso = prepare(o);
      LinkedList<Long> loIds = new LinkedList<Long>();
      for (LearningObject lo : o.getLearningObjects()) {
        loIds.add(lo.getId());
      }
      jso.add(SECTION_LO, gson.toJsonTree(loIds, new TypeToken<List<Long>>() {
      }.getType()));
      block.add(Long.toString(o.getId()), jso);
    }
    output.add(SECTION_A, block);

    BlockingDeque<Domain> domains = Database.getInstance().getAll(Domain.class);
    JsonArray jsD = new JsonArray();
    for (Domain d : domains) {
      IncidenceMatrix mat = d.getMapping();
      Map<FCAObject, Set<FCAAttribute>> mapping = mat.getObjects();
      JsonObject jso = prepare(d);
      jso.addProperty(OWNER, d.getOwner().getId());
      JsonObject jsMapping = new JsonObject();
      for (FCAObject o : mapping.keySet()) {
        LinkedList<Long> aIds = new LinkedList<Long>();
        for (FCAAttribute a : mapping.get(o)) {
          aIds.add(a.getId());
        }
        jsMapping.add(Long.toString(o.getId()),
            gson.toJsonTree(aIds, new TypeToken<List<Long>>() {
            }.getType()));
      }
      jso.add(MAPPING, jsMapping);
      jsD.add(jso);
    }
    output.add(SECTION_D, jsD);

    return output.toString();
  }

  private void imPort(JsonObject json) throws Exception {
    Database.getInstance().clear();

    HashMap<Long, User> users = new HashMap<Long, User>();
    JsonObject block = json.get(SECTION_U).getAsJsonObject();

    Set<Entry<String, JsonElement>> entries = block.entrySet();
    for (Entry<String, JsonElement> u : entries) {
      JsonObject jsUser = u.getValue().getAsJsonObject();
      User user = new User(jsUser.get(E_UID).getAsString(), jsUser.get(NAME)
          .getAsString(), jsUser.get(DESCRIPTION).getAsString());
      users.put(Long.parseLong(u.getKey()), user);
    }
    Database.getInstance().putAll(users.values());

    HashMap<Long, LearningObject> learningObjects = new HashMap<Long, LearningObject>();
    block = json.get(SECTION_LO).getAsJsonObject();

    entries = block.entrySet();
    for (Entry<String, JsonElement> lo : entries) {
      JsonObject jsLo = lo.getValue().getAsJsonObject();
      User owner = users.get(Long.parseLong(jsLo.get(OWNER).getAsString()));
      LearningObject learningObject = new LearningObject(jsLo.get(NAME)
          .getAsString(), jsLo.get(DESCRIPTION).getAsString(), jsLo.get(DATA)
          .getAsString(), owner);
      learningObjects.put(Long.parseLong(lo.getKey()), learningObject);
    }
    Database.getInstance().putAll(learningObjects.values());

    HashMap<Long, FCAObject> objects = new HashMap<Long, FCAObject>();
    block = json.get(SECTION_O).getAsJsonObject();

    entries = block.entrySet();
    for (Entry<String, JsonElement> o : entries) {
      JsonObject jsO = o.getValue().getAsJsonObject();
      FCAObject object = new FCAObject(jsO.get(NAME).getAsString(), jsO.get(
          DESCRIPTION).getAsString());
      Iterator<JsonElement> lOs = jsO.getAsJsonArray(SECTION_LO).iterator();
      Set<LearningObject> lObjs = new HashSet<LearningObject>();
      while (lOs.hasNext()) {
        LearningObject lo = learningObjects.get((lOs.next().getAsLong()));
        lObjs.add(lo);
      }
      object.setLearningObjects(lObjs);
      objects.put(Long.parseLong(o.getKey()), object);
    }
    Database.getInstance().putAll(objects.values());

    HashMap<Long, FCAAttribute> attributes = new HashMap<Long, FCAAttribute>();
    block = json.get(SECTION_A).getAsJsonObject();

    entries = block.entrySet();
    for (Entry<String, JsonElement> a : entries) {
      JsonObject jsA = a.getValue().getAsJsonObject();
      FCAAttribute attribute = new FCAAttribute(jsA.get(NAME).getAsString(),
          jsA.get(DESCRIPTION).getAsString());
      Iterator<JsonElement> lOs = jsA.getAsJsonArray(SECTION_LO).iterator();
      Set<LearningObject> lObjs = new HashSet<LearningObject>();
      while (lOs.hasNext()) {
        LearningObject lo = learningObjects.get((lOs.next().getAsLong()));
        lObjs.add(lo);
      }
      attribute.setLearningObjects(lObjs);
      attributes.put(Long.parseLong(a.getKey()), attribute);
    }
    Database.getInstance().putAll(attributes.values());

    Iterator<JsonElement> jsD = json.getAsJsonArray(SECTION_D).iterator();
    HashSet<Domain> domains = new HashSet<Domain>();
    while (jsD.hasNext()) {
      JsonObject d = jsD.next().getAsJsonObject();
      IncidenceMatrix mat = new IncidenceMatrix(d.get(NAME).getAsString(), d
          .get(DESCRIPTION).getAsString());
      Set<Entry<String, JsonElement>> mapping = d.get(MAPPING)
          .getAsJsonObject().entrySet();
      for (Entry<String, JsonElement> map : mapping) {
        FCAObject object = objects.get(Long.parseLong(map.getKey()));
        Iterator<JsonElement> atts = map.getValue().getAsJsonArray().iterator();
        HashSet<FCAAttribute> attribs = new HashSet<FCAAttribute>();
        while (atts.hasNext()) {
          attribs.add(attributes.get(atts.next().getAsLong()));
        }
        mat.add(object, attribs);
      }
      domains.add(new Domain(mat.getName(), mat.getDescription(), mat, users
          .get(d.get(OWNER).getAsLong())));

    }

    Database.getInstance().putAll(domains);
    Database.getInstance().save();

  }

  private class Params {
    @Parameter
    private List<String> parameters = new ArrayList<String>();

    @Parameter(names = { "-f", "--file" }, description = "Source/Desitnation File")
    private String file;

    @Parameter(names = { "-e", "--export" }, description = "Export mode, the FCA DB file needs to be located at webapps/at.tugraz.kmi.medoky.fca.db"
        + " -- If not set data will be imported to webapps/at.tugraz.kmi.medoky.fca.db")
    private boolean export = false;

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
    }
  }
}
