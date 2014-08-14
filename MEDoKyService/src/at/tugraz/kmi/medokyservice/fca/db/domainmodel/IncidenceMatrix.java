package at.tugraz.kmi.medokyservice.fca.db.domainmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.Database;
import at.tugraz.kmi.medokyservice.fca.db.FCAAbstract;
import at.tugraz.kmi.medokyservice.fca.lib.colibri.lib.HashRelation;
import at.tugraz.kmi.medokyservice.fca.lib.colibri.lib.Relation;

/**
 * An IncidenceMAtrix is a Mapping of Objects to attributes and vice versa. This
 * is implemented using two {@link LinkedHashMap}s to provide a bidirectional
 * mapping
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public class IncidenceMatrix extends DataObject {

  private static final long                 serialVersionUID = 316445789327401260L;
  private Map<FCAObject, Set<FCAAttribute>> objects;
  private Map<FCAAttribute, Set<FCAObject>> attributes;
  private Map<Long, FCAItemMetadata>        itemMetadata;

  /**
   * @param name
   * @param description
   */
  public IncidenceMatrix(String name, String description) {
    super(name, description);
    this.objects = Collections.synchronizedMap(new LinkedHashMap<FCAObject, Set<FCAAttribute>>());
    this.attributes = Collections.synchronizedMap(new LinkedHashMap<FCAAttribute, Set<FCAObject>>());
    this.itemMetadata = Collections.synchronizedMap(new HashMap<Long, FCAItemMetadata>());
  }

  /**
   * Maps an {@link FCAObject} to an arbitrary number of {@link FCAAttribute}s
   * 
   * @param obj
   *          the object
   * @param attributes
   *          the attributes to be mapped to the object
   */
  public void add(FCAObject obj, Collection<FCAAttribute> attributes) {
    synchronized (objects) {
      synchronized (attributes) {
        if (obj == null) {
          for (FCAAttribute attr : attributes) {
            storeMetadata(attr);
            Set<FCAObject> objects = this.attributes.get(attr);
            if (objects == null)
              objects = Collections.synchronizedSet(new LinkedHashSet<FCAObject>());
            this.attributes.put(attr, objects);
            for (FCAObject o : objects)
              storeMetadata(o);
          }
          return;
        }
        storeMetadata(obj);
        if (this.objects.get(obj) == null)
          this.objects.put(obj, Collections.synchronizedSet(new LinkedHashSet<FCAAttribute>()));
        Set<FCAAttribute> attribs = this.objects.get(obj);
        if (attributes.size() == 0)
          return;
        Set<FCAAttribute> attrs = Collections.synchronizedSet(new LinkedHashSet<FCAAttribute>(attributes));
        attribs.addAll(attrs);
        this.objects.put(obj, attribs);
        for (FCAAttribute attr : attrs) {
          storeMetadata(attr);
          Set<FCAObject> objects = this.attributes.get(attr);
          if (objects == null)
            objects = Collections.synchronizedSet(new LinkedHashSet<FCAObject>());
          objects.add(obj);
          this.attributes.put(attr, objects);
        }
      }
    }
  }

  public void storeMetadata(FCAAbstract obj) {
    FCAItemMetadata metadata = new FCAItemMetadata(obj);
    itemMetadata.put(obj.getId(), metadata);
    Database.getInstance().put(metadata, false);
  }

  public Set<FCAAttribute> getAttributes(FCAObject obj) {
    Set<FCAAttribute> attrs = this.objects.get(obj) == null ? Collections.synchronizedSet(new HashSet<FCAAttribute>())
        : this.objects.get(obj);
    setMetadata(attrs);
    return attrs;
  }

  public Set<FCAAttribute> getAttributes(Set<FCAObject> objects) {
    if (objects.size() == 0)
      return this.attributes.keySet();

    Set<FCAAttribute> attributes = Collections.synchronizedSet(new HashSet<FCAAttribute>());
    boolean initial = true;
    for (FCAObject o : objects) {
      if (initial) {
        attributes.addAll(this.objects.get(o));
        initial = false;
      } else {
        attributes.retainAll(this.objects.get(o));
      }
    }
    setMetadata(attributes);
    return attributes;
  }

  public Set<FCAObject> getObjects(FCAAttribute attr) {
    Set<FCAObject> objects = this.attributes.get(attr) == null ? Collections.synchronizedSet(new HashSet<FCAObject>())
        : this.attributes.get(attr);
    setMetadata(objects);
    return objects;
  }

  public Set<FCAObject> getObjects(Set<FCAAttribute> attributes) {
    Set<FCAObject> objects;
    if (attributes.size() == 0) {
      objects = this.objects.keySet();
      setMetadata(objects);
      return objects;
    }

    objects = Collections.synchronizedSet(new HashSet<FCAObject>());
    boolean initial = true;
    for (FCAAttribute a : attributes) {
      if (initial) {
        objects.addAll(this.attributes.get(a));
        initial = false;
      } else {
        objects.retainAll(this.attributes.get(a));
      }
    }
    setMetadata(objects);
    return objects;
  }

  public Map<FCAObject, Set<FCAAttribute>> getObjects() {
    setMetadata(this.objects.keySet());
    setMetadata(this.attributes.keySet());
    return this.objects;
  }

  public Map<FCAAttribute, Set<FCAObject>> getAttributes() {
    setMetadata(this.objects.keySet());
    setMetadata(this.attributes.keySet());
    return this.attributes;
  }

  /**
   * Returns a {@link HashRelation} required to compute a valid {@link Lattice}
   * 
   * @return a {@link HashRelation} required to compute a valid {@link Lattice}
   */
  @JsonIgnore
  synchronized Relation getRelation() {
    synchronized (objects) {
      synchronized (attributes) {
        HashRelation relation = new HashRelation();
        for (FCAObject o : getObjects().keySet()) {
          for (FCAAttribute a : getAttributes(o))
            relation.add(o, a);
        }
        return relation;
      }
    }
  }

  /**
   * Return a String representation of this object. The string value returned is
   * subject to change and therefore only suitable for debugging purposes.
   * 
   * @return a String representation of this object
   */
  public String toString() {
    StringBuffer buf = new StringBuffer(super.toString());
    buf.append("\n");
    ArrayList<FCAAttribute> attribs = new ArrayList<FCAAttribute>();
    attribs.addAll(this.attributes.keySet());
    for (FCAAttribute a : attribs) {
      buf.append(a.getName()).append("\t");
    }
    buf.append("\n\n");

    for (FCAObject o : getObjects().keySet()) {

      Set<FCAAttribute> currentAttributes = this.objects.get(o);
      for (FCAAttribute a : attribs) {
        if (currentAttributes.contains(a))
          buf.append("1");
        else
          buf.append("0");
        buf.append("\t");

      }
      buf.append("\n");

    }
    return buf.toString();
  }

  /**
   * Populates the matrix's attributes.
   * 
   * @param set
   *          the set of attributes
   */
  public void initAttributes(Collection<FCAAttribute> set) {
    synchronized (attributes) {
      for (FCAAttribute attr : set) {
        attributes.put(attr, Collections.synchronizedSet(new LinkedHashSet<FCAObject>()));
        storeMetadata(attr);
      }
    }
  }

  /**
   * Populates the matrix's objects
   * 
   * @param set
   *          the set of objects
   */
  public void initObjects(Collection<FCAObject> set) {
    synchronized (objects) {
      for (FCAObject obj : set) {
        objects.put(obj, Collections.synchronizedSet(new LinkedHashSet<FCAAttribute>()));
        storeMetadata(obj);
      }
    }
  }

  public Map<Long, FCAItemMetadata> getItemMetadata() {
    return itemMetadata;
  }

  private <T extends FCAAbstract> void setMetadata(Collection<T> items) {
    for (FCAAbstract item : items) {
      item.setDescription(itemMetadata.get(item.getId()).getDescription());
      item.setLearningObjects(itemMetadata.get(item.getId()).getLearningObjects());
      item.setLearningObjectsByLearners(itemMetadata.get(item.getId()).getLearningObjectByLearner());
    }
  }

  public void clear() {
    synchronized (objects) {
      synchronized (attributes) {
        synchronized (itemMetadata) {
          objects.clear();
          attributes.clear();
          itemMetadata.clear();
        }
      }
    }
  }
}
