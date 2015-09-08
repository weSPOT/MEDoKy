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
package at.tugraz.kmi.medokyservice.fca.db;

/**
 * Singelton generating sequential unique {@link Long} ids starting at -(2^50)
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public class IDGenerator {
  private static IDGenerator instance = null;
  /**
   * The last generated id
   */
  long lastId;

  private IDGenerator() {
    lastId = (long) -Math.pow(2, 50);
  }

  /**
   * @return IDGenerator instance
   */
  public static synchronized IDGenerator getInstance() {
    if (instance == null)
      instance = new IDGenerator();
    return instance;
  }

  /**
   * Returns the next valid id
   * 
   * @return the next valid id
   */
  public synchronized long getID() {
    return ++lastId;
  }

  void reset() {
    lastId = (long) -Math.pow(2, 50);
  }
}
