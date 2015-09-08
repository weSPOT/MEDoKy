package at.tugraz.kmi.medokyservice.fca.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;
import at.tugraz.kmi.medokyservice.fca.db.Database;
import at.tugraz.kmi.medokyservice.fca.db.conf.DBConfig;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAAttribute;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.FCAObject;

public class TestDatabaseAdvanced {

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
    System.gc();
    Database.getInstance().clear();
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
  public void testCreateObjects() {

    int part = 40;
    final FCAObject[] objs = new FCAObject[part * part];
    for (int i = 0; i < part * part; ++i) {
      objs[i] = (new FCAObject("Object " + i, i * i + " test",
          Double.toHexString(1000 * Math.random())));
    }
    testCreate(objs, part);
    assertTrue(Database.getInstance().getAll(FCAObject.class)
        .containsAll(Arrays.asList(objs)));
  }

  @Test
  public void testCreateAttributes() {
    testCreateObjects();
    int part = 80;
    final FCAAttribute[] attrs = new FCAAttribute[part * part];
    for (int i = 0; i < part * part; ++i) {
      attrs[i] = new FCAAttribute("Attribute " + i, i * i + " test",
          Double.toHexString(1000 * Math.random()));
    }
    testCreate(attrs, part);
    assertTrue(Database.getInstance().getAll(FCAAttribute.class)
        .containsAll(Arrays.asList(attrs)));
  }

  static <E extends DataObject> void testCreate(final E[] objs,
      final int partition) {
    final Failure f = new Failure();
    final Database instance = Database.getInstance();
    LinkedList<Thread> threads = new LinkedList<Thread>();
    for (int i = 0; i < partition; ++i) {
      final Integer val = i;
      Thread t = new Thread() {
        public void run() {
          for (int n = val * partition; n < (val * partition) + partition; ++n)
            try {

              instance.put(objs[n],false);
            } catch (Exception e) {
              e.printStackTrace();
              System.out.println("n: " + n + "; size: " + objs.length);
              System.out.println(objs[n]);
              // f.eceptions.add(e);
              f.failed = true;
            }
        }
      };
      threads.add(t);
      t.start();
    }
    System.out.println("waiting");
    for (Thread t : threads) {
      try {
        t.join();
      } catch (InterruptedException e) {
      }
    }

    assertTrue("Failed adding objects to database", !f.failed);
  }

}