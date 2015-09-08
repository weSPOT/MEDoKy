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
package at.tugraz.kmi.medokyservice.rec.resources;

import java.util.ArrayList;

import at.tugraz.kmi.medokyservice.rec.Intervention;


//TODO: adapt comments to recommendations

/**
 * The class manages the registration and instantiation of classes derived from {@link Intervention}. 
 * Recommendations are broadly classified into three groups: 
 * learning resources, learning activities, learning peers. 
 * 
 * @author Simone Kopeinik <simone.kopeinik@tugraz.at>
 * 
 */
public class RecommendationManager {

	
	private ArrayList<String> resourceRecommendation = null;
	private ArrayList<String> learningActivityRecommendation = null;
	private ArrayList<String> peerRecommendation = null;
	

	
	/**
	 * Instantiates a new recommendation manager.
	 */
	public RecommendationManager() {
		this.resourceRecommendation = new ArrayList<String>();
		this.learningActivityRecommendation = new ArrayList<String>();
		this.peerRecommendation = new ArrayList<String>();
		this.resourceRecommendation();
		this.learningActivityRecommendation();
		this.peerRecommendation();
	}

	
	/**
	 * Register clarification interventions.
	 */
	private void peerRecommendation() {
		this.peerRecommendation.add("at.tugraz.kmi.am.interventions.MusicIntervention");
		
	}



	/**
	 * Register clarification interventions.
	 */
	private void learningActivityRecommendation() {
		this.peerRecommendation.add("at.tugraz.kmi.am.interventions.MusicIntervention");
	}



	/**
	 * Register clarification interventions.
	 */
	private void resourceRecommendation() {
		this.peerRecommendation.add("at.tugraz.kmi.am.interventions.MusicIntervention");
	}

	
	/**
	 * Returns a list of all available recommendations.
	 * 
	 * @return available recommendations
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public synchronized ArrayList<Intervention> getRecommendations() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ArrayList<Intervention> recommendations = new ArrayList<Intervention>();
		recommendations.addAll(getResourceRecommendations());
		recommendations.addAll(getLearningActivityRecommendations());
		recommendations.addAll(getPeerRecommendations());
		return recommendations;
	}
	
	/**competenceRelatedInterventions
	 *  
	 * @return a list of all registered ResourceRecommendations
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public synchronized ArrayList<Intervention> getResourceRecommendations() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		ArrayList<Intervention> interventions = new ArrayList<Intervention>();
		
		for (String classname : this.resourceRecommendation)
			interventions.add((Intervention)Class.forName(classname).newInstance());
		
		return interventions;
	}
	
	/**
	 * 
	 * @return a list of all registered LearningActivityRecommendations
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public synchronized ArrayList<Intervention> getLearningActivityRecommendations() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		ArrayList<Intervention> interventions = new ArrayList<Intervention>();
		
		for (String classname : this.learningActivityRecommendation)
			interventions.add((Intervention)Class.forName(classname).newInstance());
		
		return interventions;
	}
	
	/**
	 * @return a list of all registered PeerRecommendations
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public synchronized ArrayList<Intervention> getPeerRecommendations() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		ArrayList<Intervention> interventions = new ArrayList<Intervention>();
		
		for (String classname : this.peerRecommendation)
			interventions.add((Intervention)Class.forName(classname).newInstance());
		
		return interventions;
	}

}
