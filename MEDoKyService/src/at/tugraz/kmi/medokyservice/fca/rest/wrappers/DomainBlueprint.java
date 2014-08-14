package at.tugraz.kmi.medokyservice.fca.rest.wrappers;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;

/**
 * Wrapper used for serving domain metadata (name, description id) and for
 * creating domains based on a mapping of {@link FCAObject}s to
 * {@link FCAAttribute}s.
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public class DomainBlueprint extends AbstractWrapper {
  public DomainBlueprint() {
  }

  public DomainBlueprint(String name, String description, User owner, boolean approved) {
    super.name = name;
    super.description = description;
    this.owner = owner;
    this.approved = approved;
  }

  /**
   * external UID of the teacher that created this domain
   */
  public String                                   externalUID;

  public String                                   externalCourseID;

  public String                                   courseName;


  /**
   * the set of {@link FCAAttribute} IDs part of this domain
   */
  public LinkedHashSet<Long>                      attributes;

  /**
   * the set of {@link FCAObject} IDs part of this domain
   */
  public LinkedHashSet<Long>                      objects;

  /**
   * Mapping of {@link FCAObject} IDs to FCAAttribute IDs
   */
  public LinkedHashMap<Long, LinkedHashSet<Long>> mapping;

  /**
   * The Domain's owner/creator
   */
  public User                                     owner;

  /**
   * Flag indicating whethet this domain is approved
   */
  public boolean                                  approved;

}
