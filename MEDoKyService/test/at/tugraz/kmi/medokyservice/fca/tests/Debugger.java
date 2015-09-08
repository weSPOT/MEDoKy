package at.tugraz.kmi.medokyservice.fca.tests;

import java.io.FileNotFoundException;
import java.io.IOException;

import at.tugraz.kmi.medokyservice.fca.db.Database;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;

public class Debugger {
  public static void main(String[] args) {
    for (LearningObject o : Database.getInstance().getAll(LearningObject.class)) {
      System.out.println("NAME: " + o.getName() + " DESCRIPTION: "
          + o.getDescription());
      if (o.getDescription().startsWith("http://https://"))
        o.setDescription(o.getDescription().replace("http://https://",
            "https://"));
    }
    try {
      Database.getInstance().save();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
