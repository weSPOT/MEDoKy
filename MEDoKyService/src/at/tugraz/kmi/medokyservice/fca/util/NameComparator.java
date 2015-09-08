/*
 * 
 * MEDoKyService:
 * A web service component for learner modelling and learning recommendations.
 * Copyright (C) 2015 KTI, TUGraz, Contact: simone.kopeinik@tugraz.at
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Affero General Public License for more details.  
 * For more information about the GNU Affero General Public License see <http://www.gnu.org/licenses/>.
 * 
 */
package at.tugraz.kmi.medokyservice.fca.util;

import java.util.Comparator;

import at.tugraz.kmi.medokyservice.fca.db.DataObject;

/**
 * Compares DataObjects by name. If names are equal, their IDs are compared.
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
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
