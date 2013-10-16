package at.tugraz.kmi.medokyservice.fca.rest.wrappers;

import java.util.HashMap;

public class CourseWrapper extends AbstractWrapper {

  public HashMap<Long, DomainBlueprint> domains;
  public String externalCourseID;

  public CourseWrapper(long id, String name, String description,
      String externalID) {
    super.id = id;
    super.name = name;
    super.description = description;
    domains = new HashMap<Long, DomainBlueprint>();
    this.externalCourseID = externalID;

  }

}
