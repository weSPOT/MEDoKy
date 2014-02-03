package at.tugraz.kmi.medokyservice.fca.db;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;

import at.tugraz.kmi.medokyservice.fca.db.conf.DBConfig;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Concept;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Course;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Domain;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAItemMetadata;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.LearnerDomain;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;

import com.sun.istack.logging.Logger;

/**
 * Database storing {@link DataObject} derived Objects backed by multiple
 * HashMaps mapping {@link Long} IDs to Objects. Every Object in the Database is
 * referenced twice: by a global Map and by a Map for its specific Class(Type).
 * The class is fully thread-safe at the cost of permitting only purely
 * synchronous(single-threaded) access. Furthermore queries return
 * {@link BlockingDeque}s containing the results to ensure thread-safety. Since
 * only references are stored inside a deque the objects itself are mutable.
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public class Database implements Serializable {

  /**
   * 
   */
  private static boolean testing = false;

  private static final long serialVersionUID = 764673402284713973L;

  private static Database instance = null;

  private Map<String, Course> coursesByExternalID;
  private Map<String, User> usersByExtrnalUID;
  private Map<String, FCAAbstract> fcaItemsBycreationID;
  private Map<String, LearningObject> learningObjectsByURL;
  private Map<Long, DataObject> registry;

  @SuppressWarnings("rawtypes")
  private Map<Class, Map> typeMap;

  @SuppressWarnings("rawtypes")
  private Database() {
    usersByExtrnalUID = Collections.synchronizedMap(new HashMap<String, User>());
    coursesByExternalID = Collections.synchronizedMap(new HashMap<String, Course>());
    fcaItemsBycreationID = Collections.synchronizedMap(new HashMap<String, FCAAbstract>());
    learningObjectsByURL = Collections.synchronizedMap(new HashMap<String, LearningObject>());
    registry = Collections.synchronizedMap(new HashMap<Long, DataObject>());
    typeMap = Collections.synchronizedMap(new HashMap<Class, Map>());
    typeMap.put(FCAObject.class, Collections.synchronizedMap(new HashMap<Long, FCAObject>()));
    typeMap.put(FCAAttribute.class, Collections.synchronizedMap(new HashMap<Long, FCAAttribute>()));
    typeMap.put(LearningObject.class, Collections.synchronizedMap(new HashMap<Long, LearningObject>()));
    typeMap.put(LearnerDomain.class, Collections.synchronizedMap(new HashMap<Long, LearnerDomain>()));
    typeMap.put(Domain.class, Collections.synchronizedMap(new HashMap<Long, Domain>()));
    typeMap.put(Course.class, Collections.synchronizedMap(new HashMap<Long, Course>()));
    typeMap.put(Concept.class, Collections.synchronizedMap(new HashMap<Long, Concept>()));
    typeMap.put(User.class, Collections.synchronizedMap(new HashMap<Long, User>()));
    typeMap.put(FCAItemMetadata.class, Collections.synchronizedMap(new HashMap<Long, FCAItemMetadata>()));

  }

  /**
   * Returns an instance of the database. Deserializes a previously stored
   * database from hard disk if it exists.
   * 
   * @return a database instance
   */
  public static synchronized Database getInstance() {

    if (instance == null) {
      System.out.println("DB INIT");
      File dir = new File(DBConfig.DB_DIR);
      if (!dir.exists()) {
        dir.mkdir();
      }
      File in = new File(DBConfig.DB_DIR + DBConfig.DB_PATH);
      if (in.exists()) {
        ObjectInputStream objIn = null;
        try {
          objIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream(in)));
          Database db = (Database) objIn.readObject();
          instance = db;
          IDGenerator.getInstance().lastId = objIn.readLong();
          Logger.getLogger(Database.class).log(Level.INFO, "Database laded from file " + in.getAbsolutePath());
        } catch (ClassNotFoundException e) {
          Logger.getLogger(Database.class).log(Level.WARNING, "Incomaptible Databse found");
        } catch (InvalidClassException e) {
          Logger.getLogger(Database.class).log(Level.WARNING, "Incomaptible Databse found");
        } catch (FileNotFoundException e) {
          Logger.getLogger(Database.class).log(Level.INFO, "No Database fround");
        } catch (IOException e) {
          Logger.getLogger(Database.class).log(Level.WARNING, "IO ERROR! Can't Read DB");
          e.printStackTrace();
        } finally {
          try {
            objIn.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
          if (instance == null) {
            Logger.getLogger(Database.class).log(Level.INFO, "Creating Empty Database at " + in.getAbsolutePath());
            instance = new Database();
          }
        }
      } else {
        Logger.getLogger(Database.class).log(Level.INFO, "Creating Empty Database at " + in.getAbsolutePath());
        instance = new Database();
      }
    }
    return instance;
  }

  /**
   * Checks whether the specified object exists in the database
   * 
   * @param obj
   *          the object to check
   * @return true if object is in database, false otherwise
   */
  public synchronized boolean contains(DataObject obj) {
    return typeMap.get(obj.getClass()).containsValue(obj);
  }

  /**
   * retrieves an object from the database by ID
   * 
   * @param id
   *          the ID of the object to get
   * @return the object with the provided ID
   */
  @SuppressWarnings("unchecked")
  public synchronized <E extends DataObject> E get(long id) {
    synchronized (registry) {

      return (E) registry.get(id);
    }
  }

  /**
   * retrieves all object of the specified type
   * 
   * @param type
   *          the type of objects to retrieve
   * @return a BlockingDeque containing all objects of the specified type
   */
  @SuppressWarnings("unchecked")
  public synchronized <E extends DataObject> BlockingDeque<E> getAll(Class<E> type) {
    Map<Long, E> map;
    synchronized (typeMap) {
      map = typeMap.get(type);
    }
    synchronized (map) {
      return new LinkedBlockingDeque<E>(map.values());
    }
  }

  /**
   * retrieves a user using an external identifier
   * 
   * @param externalUID
   *          the external UUID of the user to get
   * @return the User with the specified identifier
   */
  public synchronized User getUserByExternalUID(String externalUID) {
    User u = usersByExtrnalUID.get(externalUID);
    return u;
  }

  public synchronized FCAAbstract getFCAItemBycreationId(String creationId) {
    FCAAbstract a = fcaItemsBycreationID.get(creationId);
    return a;
  }

  public synchronized LearningObject getLearningObjectsByURL(String url) {
    System.out.println(url);
    System.out.println(learningObjectsByURL);
    if (!learningObjectsByURL.containsKey(url))
      return null;
    LearningObject lo = learningObjectsByURL.get(url);
    return lo;
  }

  /**
   * retrieves a course using an external identifier
   * 
   * @param externalID
   *          the external ID of the course to get
   * @return the Course with the specified identifier
   */
  public synchronized Course getCourseByExternalID(String externalID) {
    Course c = coursesByExternalID.get(externalID);
    return c;
  }

  /**
   * Retrieves the set of objects matched by the provided IDs
   * 
   * @param ids
   *          the IDs of object to get
   * @return a BlockingDeque containing all objects matched
   */
  @SuppressWarnings("unchecked")
  public synchronized <E extends DataObject> BlockingDeque<E> get(Collection<Long> ids) {
    synchronized (registry) {
      LinkedBlockingDeque<E> result = new LinkedBlockingDeque<E>();
      for (long l : ids)
        result.add((E) registry.get(l));
      return result;
    }
  }

  /**
   * Stores the provided object in the database. If the object already exists it
   * is overwritten
   * 
   * @param obj
   *          the object to store inside the database
   */

  @SuppressWarnings("unchecked")
  public synchronized void put(DataObject obj, boolean save) {
    synchronized (registry) {
      if (!typeMap.containsKey(obj.getClass()))
        typeMap.put(obj.getClass(), createMap());
      synchronized (typeMap.get(obj.getClass())) {
        registry.put(obj.getId(), obj);
        typeMap.get(obj.getClass()).put(obj.getId(), obj);
        if (obj instanceof User)
          usersByExtrnalUID.put(((User) obj).getExternalUid(), (User) obj);
        else if (obj instanceof Course)
          coursesByExternalID.put(((Course) obj).getExternalCourseID(), (Course) obj);
        else if (obj instanceof FCAAbstract)
          fcaItemsBycreationID.put(((FCAAbstract) obj).getCreationId(), (FCAAbstract) obj);
        else if (obj instanceof LearningObject)
          learningObjectsByURL.put(((LearningObject) obj).getData(), (LearningObject) obj);
        if(save)
        try {
          save();
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  
  
  /**
   * Stores the provided objects in the database. If an object already exists it
   * is overwritten
   * 
   * @param objs
   *          the objects to store inside the database
   */
  @SuppressWarnings("unchecked")
  public synchronized <E extends DataObject> void putAll(Collection<E> objs, boolean save) {
    synchronized (registry) {
      for (DataObject obj : objs) {
        if (!typeMap.containsKey(obj.getClass()))
          typeMap.put(obj.getClass(), createMap());
        synchronized (typeMap.get(obj.getClass())) {
          registry.put(obj.getId(), obj);
          typeMap.get(obj.getClass()).put(obj.getId(), obj);
          if (obj instanceof User)
            usersByExtrnalUID.put(((User) obj).getExternalUid(), (User) obj);
          else if (obj instanceof Course)
            coursesByExternalID.put(((Course) obj).getExternalCourseID(), (Course) obj);
          else if (obj instanceof FCAAbstract)
            fcaItemsBycreationID.put(((FCAAbstract) obj).getCreationId(), (FCAAbstract) obj);
          else if (obj instanceof LearningObject)
            learningObjectsByURL.put(((LearningObject) obj).getData(), (LearningObject) obj);
        }
      }
      if(save)
      try {
        save();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Serializes the database to hard disk
   * 
   * @throws FileNotFoundException
   *           see {@link File}
   * @throws IOException
   *           see {@link File}
   */
  public synchronized void save() throws FileNotFoundException, IOException {
    if (!testing) {
      ObjectOutputStream outO = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(DBConfig.DB_DIR
          + DBConfig.DB_PATH)));
      outO.writeObject(this);
      outO.writeLong(IDGenerator.getInstance().lastId);
      outO.close();
      Logger.getLogger(Database.class).log(Level.INFO, "Database " + DBConfig.DB_DIR + DBConfig.DB_PATH + " Saved");
    } else {
      Logger.getLogger(Database.class).log(Level.WARNING, "Test mode, nothing will be written to disk!");
    }
  }

  private synchronized <V extends DataObject> Map<Long, V> createMap() {
    return Collections.synchronizedMap(new HashMap<Long, V>());
  }

  /**
   * Removes all data from the database. This is irreversible!
   */
  @SuppressWarnings("rawtypes")
  public synchronized void clear() {
    usersByExtrnalUID.clear();
    coursesByExternalID.clear();
    fcaItemsBycreationID.clear();
    learningObjectsByURL.clear();
    registry.clear();
    for (Map type : typeMap.values())
      type.clear();
  }

  /**
   * for internal debugging use
   */
  public void print() {
    System.out.println(new File(DBConfig.DB_PATH).getAbsolutePath());
    synchronized (registry) {
      for (long l : registry.keySet()) {
        System.out.println(l + " : " + registry.get(l).toString());
      }
    }
  }

}