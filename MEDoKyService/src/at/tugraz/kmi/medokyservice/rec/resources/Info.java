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
package at.tugraz.kmi.medokyservice.rec.resources;

import java.beans.Beans;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Info extends Beans{
	  private String info;
	    	    
	    public Info(){
	    	this.info = "UNSET";
	    }
	    
	    public Info(String text){
	    	this.info  = text;
	    }
	    
	    public String getInfo() {
	            return info;
	    }

	    public void setInfo(String infoText) {
	            this.info = infoText;
	    }
}