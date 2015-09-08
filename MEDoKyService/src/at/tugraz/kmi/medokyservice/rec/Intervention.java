/*
 * Copyright (C) 2010-2011 Graz University of Technology, Austria. All Rights reserved. 
 *
 * Contact: Simone Kopeinik <simone.kopeinik@tugraz.at>
 * 	   Graz University of Technology, Knowledge Management Institute, Brückenkopfgasse 1/6, 8020 Graz 	
 * 	   <http://www.kmi.tugraz.at/>
 * 
 * This software is part of the TARGET platform developed by the
 * TARGET EC-funded project www.reachyourtarget.org
 *
 * This software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * The GNU General Public License is available at <http://www.gnu.org/licenses/>.
 *
 */

package at.tugraz.kmi.medokyservice.rec;




/**
 * The Class Intervention is a super class to all various types of interventions. Interventions always refer to micro 
 * adaptations within a user's experience in a particular learning environment.
 * 
 * @author Simone Kopeinik <simone.kopeinik@tugraz.at>
 * 
 */
public abstract class Intervention {

	// TODO: HOW can duration and level be defined properly

	protected static final int STANDARD_DURATION = 160;
	protected static final int STANDARD_LEVEL = 0;
	protected int durationInSec; 
	protected int intesityLevel;
	protected RecommendationClassification type;

	

	/**
	 * Instantiates a new intervention.
	 * 
	 * @param durationInSec
	 *            the duration in sec
	 * @param intensityLevel
	 *            the intensity level
	 * @param type
	 *            the type
	 */
	public Intervention(int durationInSec, int intensityLevel, RecommendationClassification type) {
		this.durationInSec = durationInSec;
		this.intesityLevel = intensityLevel;
		this.type = type;
	
	}
	
	
	/**
	 * Instantiates a new intervention.
	 *
	 * @param durationInSec the duration in sec
	 * @param type the type
	 */
	public Intervention(int durationInSec, RecommendationClassification type) {
		this.durationInSec = durationInSec;
		this.intesityLevel = STANDARD_LEVEL;
		this.type = type;
	}

	/**
	 * Instantiates a new intervention.
	 *
	 * @param type the type
	 */
	public Intervention(RecommendationClassification type) {
		this.durationInSec = STANDARD_DURATION;
		this.intesityLevel = STANDARD_LEVEL;
		this.type = type;
	}
	


	/**
	 * Gets the duration in sec.
	 * 
	 * @return the duration in sec
	 */
	public int getDurationInSec() {
		return durationInSec;
	}

	/**
	 * Gets the intensity level.
	 * 
	 * @return the intensity level
	 */
	public long getIntensityLevel() {
		return intesityLevel;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public RecommendationClassification getType() {
		return type;
	}
	
	/**
	 * Trigger.
	 *
	 * @return true, if successful
	 */
	public abstract boolean trigger();

	
	/**
	 * Returns a String .
	 *
	 * @return a text description of the Intervention
	 */
	public abstract String toString();
}
