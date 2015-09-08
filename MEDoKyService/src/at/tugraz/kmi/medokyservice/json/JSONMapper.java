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
package at.tugraz.kmi.medokyservice.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Singleton Object to JSON Mapper
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public abstract class JSONMapper {
  private static Gson instance = null;

  /**
   * 
   * @return Gson instance
   */
  public static Gson getInstance() {
    if (instance == null)
      instance = new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy()).create();
    return instance;
  }

}
