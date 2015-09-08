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
package at.tugraz.kmi.medokyservice.rec.datapreprocessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;

public class ContentTags {

	public long timestamp = 0;
	public List<String> tags = null;
	public HashMap<String, Double> features= null;
	
	public ContentTags(Date timestamp){
		this.features = new HashMap<String, Double>();
		this.timestamp = timestamp.getTime();
	}
	
	public void setFeatures(ArrayList<String> features){
		for(String feature : features){
			this.features.put(feature, 1.0);
		}
	}
}
