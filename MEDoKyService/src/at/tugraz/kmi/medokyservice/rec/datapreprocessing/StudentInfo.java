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

public class StudentInfo {
	
	public String verb; 
	private String object;
	private String starttime;
	private String endtime;
	private String target;
	private String location;
	private String context; 
	private String original;
	private Badge badge;
	
	public StudentInfo(){
	}
	
	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}
	
	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		if (verb.equals("awarded"))
			this.badge = new Badge(original);
		this.original = original;
	}
	
	public Badge getBadge(){
		return this.badge;
	}
	
	public String toString(){
		String infoString; 
		infoString = "verb: "+verb+", object: "+object+"\n"; 
		infoString = infoString+"start: "+starttime+" - end: "+endtime+"\n"; 
		infoString = infoString+"target: "+target+", location: "+location+"\n"; 
		infoString = infoString+"context: "+context+", original: "+original+"\n";
		return infoString;
	}
}
