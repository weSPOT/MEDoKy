package at.tugraz.kmi.medokyservice.fca.util;

import java.util.Comparator;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;

/**
 * Compares DataObjects by name. If names are equal, their IDs are compared.
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 * @param <DataObject>
 */
public class NameComparator implements Comparator<DataObject> {

  /**
   * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
   */
  @Override
  public int compare(DataObject o1, DataObject o2) {
    if (o1.getName().equals(""))
      return -1;
    else if (o1.getName().equals(""))
      return 1;
    int val = o1.getName().compareTo(o2.getName());
    if (val == 0)
      return o1.compareTo(o2);
    return val;
  }

}
