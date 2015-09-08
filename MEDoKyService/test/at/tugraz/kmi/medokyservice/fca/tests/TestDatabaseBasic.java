package at.tugraz.kmi.medokyservice.fca.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.tugraz.kmi.medokyservice.fca.db.Database;
import at.tugraz.kmi.medokyservice.fca.db.IDGenerator;
import at.tugraz.kmi.medokyservice.fca.db.conf.DBConfig;

public class TestDatabaseBasic {

  @Before
  @After
  public void clean() {
    new File(DBConfig.DB_PATH).delete();
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
  }

  public void testGetInstance() {
    final ConcurrentHashMap<Database, Integer> instances = new ConcurrentHashMap<Database, Integer>();
    LinkedList<Thread> threads = new LinkedList<Thread>();
    for (int i = 0; i < 100; ++i) {
      Thread t = new Thread() {
        public void run() {
          for (int n = 0; n < 100; ++n)
            instances.put(Database.getInstance(), n);

        }
      };
      threads.add(t);
      t.start();
    }
    for (Thread t : threads) {
      try {
        t.join();
      } catch (InterruptedException e) {
      }
    }
    assertTrue(instances.size() == 1);

  }

  @Test
  public void testSave() {
    testGetInstance();
    LinkedList<Thread> threads = new LinkedList<Thread>();
    for (int i = 0; i < 100; ++i) {
      Thread t = new Thread() {
        public void run() {
          try {
            Database.getInstance().save();
          } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Save error");
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Save error");
          }
        }
      };
      threads.add(t);
      t.start();
    }
    for (Thread t : threads) {
      try {
        t.join();
      } catch (InterruptedException e) {
      }
    }
  }

  @Test
  public void testIDGenerator() {
    final IDGenerator instance = IDGenerator.getInstance();
    final ConcurrentSkipListSet<Long> ids = new ConcurrentSkipListSet<Long>();
    LinkedList<Thread> threads = new LinkedList<Thread>();
    for (int i = 0; i < 10000; ++i) {
      Thread t = new Thread() {
        public void run() {
          ids.add(instance.getID());
        }
      };
      t.start();

      assertTrue(threads.add(t));
    }
    for (Thread t : threads) {
      try {
        t.join();
      } catch (InterruptedException e) {
      }
    }
    assertTrue(ids.size() == 10000);
  }

}
