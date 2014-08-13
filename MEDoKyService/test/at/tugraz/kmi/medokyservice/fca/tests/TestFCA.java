package at.tugraz.kmi.medokyservice.fca.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.tugraz.kmi.medokyservice.fca.db.Database;
import at.tugraz.kmi.medokyservice.fca.db.conf.DBConfig;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.Domain;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.IncidenceMatrix;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;
import at.tugraz.kmi.medokyservice.fca.db.usermodel.User;

public class TestFCA {

  @Before
  public void clean() {
    new File(DBConfig.DB_PATH).delete();
    reset(true);
  }

  @After
  public void restore() {
    reset(false);
  }

  public void reset(boolean test) {
    if (test) {

      System.gc();
      Database.getInstance().clear();
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
    Field testing;
    try {
      testing = Database.class.getDeclaredField("testing");
      testing.setAccessible(true);
      testing.setBoolean(null, test);
      if (!test)
        Database.getInstance().save();
    } catch (NoSuchFieldException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void testCreateDomain() {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    final int part = 20;

    final LearningObject[] lobjs = new LearningObject[part * part];
    for (int i = 0; i < (part * part); ++i) {
      lobjs[i] = new LearningObject("LearningObject " + i, "test" + i * i + "http:// test", "", new User("FCA Tester",
          "", ""),false);
      Database.getInstance().put(lobjs[i],false);

    }

    final FCAObject[] objs = new FCAObject[(part * part)];
    for (int i = 0; i < part * part; ++i) {
      objs[i] = (new FCAObject("Object " + i, i * i + " test", Double.toHexString(1000 * Math.random())));
      for (int n = 0; n < Math.random() * (part / 4); ++n) {
        objs[i].getLearningObjects().add(lobjs[((int) (Math.random() * (part * part)))]);
        Database.getInstance().put(objs[i], false);
      }
    }

    final FCAAttribute[] attrs = new FCAAttribute[(part * part)];
    for (int i = 0; i < part * part; ++i) {
      attrs[i] = (new FCAAttribute("Attr " + i, i * i + " test", Double.toHexString(1000 * Math.random())));
      for (int n = 0; n < Math.random() * (part / 4); ++n) {
        attrs[i].getLearningObjects().add(lobjs[((int) (Math.random() * (part * part)))]);
        Database.getInstance().put(attrs[i], false);
      }
    }

    final Domain[] domains = new Domain[part * part];

    ConcurrentLinkedQueue<Thread> threads = new ConcurrentLinkedQueue<Thread>();
    for (int n = 0; n < part; ++n) {
      for (int j = n * part; j < part; ++j) {
        final Integer val = j;
        Thread t = new Thread() {
          public void run() {
            IncidenceMatrix mat = new IncidenceMatrix("mat", "mat");
            for (int i = 0; i < (Math.random() * (part) + 1); ++i) {
              LinkedList<FCAAttribute> a = new LinkedList<FCAAttribute>();
              for (int h = 0; h < (Math.random() * (part) + 1); ++h) {
                a.add(attrs[((int) (Math.random() * (part)))]);
              }
              mat.add(objs[((int) (Math.random() * (part)))], a);
            }
            domains[val] = (new Domain("dom" + val, "", mat, new User(" ", " ", " "), false));
            Database.getInstance().put(domains[val], false);
            Database.getInstance().putAll(domains[val].getFormalContext().getConcepts(), false);
          }
        };
        threads.add(t);
        t.start();
      }
    }
    for (Thread t : threads) {
      try {
        t.join();
      } catch (InterruptedException e) {
      }
    }

    System.out.println(new File(DBConfig.DB_PATH).getAbsolutePath());
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
