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
package at.tugraz.kmi.medokyservice.rec;

import java.beans.Beans;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper; 

@XmlRootElement
public class Recommendations extends Beans{

    private List<Recommendation> recommendations;
    private String status;
        
    public Recommendations(){
    	this.recommendations = new ArrayList<Recommendation>();
    	this.status = "pending";
    }

    
    public void setComplete(){
    	this.status = "complete";
    }
    
    public void setStatus(String status){
    	this.status = status;
    }
    
    @XmlElement 
    public String getStatus(){
    	return this.status;
    }
    
    public void addRecommendation(Recommendation recommendation){
    	this.recommendations.add(recommendation);
    }
    
    /*public void addRecommendation(String text){
    	this.recommendations.add(new Recommendation(text));
    }*/
    
    public void setRecommendations(List<Recommendation> recommendations){
    	this.recommendations = recommendations;
    }
    
   
    @XmlElement 
    public List<Recommendation> getRecommendations(){
    	return this.recommendations;
    }
}
