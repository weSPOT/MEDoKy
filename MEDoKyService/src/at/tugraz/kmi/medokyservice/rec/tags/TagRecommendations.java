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
package at.tugraz.kmi.medokyservice.rec.tags;

import java.beans.Beans;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class TagRecommendations extends Beans{
	
	private List<String> recommendations; 
	private String algorithm; 
	private boolean hasFeatures = false;
	
	public TagRecommendations(){
		this.recommendations = new ArrayList<String>();
	}
	
	public TagRecommendations(String userId){
		this.recommendations = new ArrayList<String>();
		this.setRecommendations();
		this.algorithm = "showcase";
	}
	
	@XmlElement 
	public String getAlgorithm() {
		return algorithm;
	}

	
	public void setHasFeatures(boolean hasFeatures) {
		this.hasFeatures = hasFeatures;
	}
	
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public TagRecommendations(List<String>recommendations, String algorithm, boolean hasFeatures){
		this.recommendations = recommendations;
		this.algorithm = algorithm;
		this.hasFeatures = hasFeatures;
	}
	 	
	public void setRecommendations(){
		for (int i=0; i<6; i++)
			recommendations.add("tag "+i);
	}
	
    @XmlElement 
    public List<String> getRecommendations(){
    	return this.recommendations;
    }

    @XmlElement 
    public boolean hasFeatures(){
    	return this.hasFeatures;
    }
    
    public String toString(){
    	return "Object TagRecommendations of "+this.algorithm+" with "+this.recommendations.toString();
    }	
}
