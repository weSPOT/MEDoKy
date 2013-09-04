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
package at.tugraz.kmi.medokyservice.bl;

import java.util.ArrayList;

import at.tugraz.kmi.medokyservice.recommendations.Intervention;


/**
 * The class manages the registration and instantiation of classes derived from {@link Intervention}. 
 * Interventions are broadly classified according to there expected impact into Emotion-, 
 * Clarification-, Motivation- and Competence Related - Interventions. 
 * 
 * @author Simone Kopeinik <simone.kopeinik@tugraz.at>
 * 
 */
public class InterventionManager {

	
	private ArrayList<String> emotionInterventions = null;
	private ArrayList<String> clarificationInterventions = null;
	private ArrayList<String> motivationInterventions = null;
	private ArrayList<String> competenceRelatedInterventions = null;

	
	/**
	 * Instantiates a new intervention manager.
	 */
	public InterventionManager() {
		this.emotionInterventions = new ArrayList<String>();
		this.clarificationInterventions = new ArrayList<String>();
		this.motivationInterventions = new ArrayList<String>();
		this.competenceRelatedInterventions = new ArrayList<String>();
		this.registerEmotionInterventions();
		this.registerClarificationInterventions();
		this.registerMotivationInterventions();
		this.registerCompetenceRelatedInterventions();
	}

	
	
	
	/**
	 * Register emotion interventions.
	 */
	private void registerEmotionInterventions(){
		emotionInterventions.add("at.tugraz.kmi.am.interventions.MusicIntervention");
		emotionInterventions.add("at.tugraz.kmi.am.interventions.FlowerIntervention");
		emotionInterventions.add("at.tugraz.kmi.am.interventions.SendBirdsIntervention");
		emotionInterventions.add("at.tugraz.kmi.am.interventions.OutsideFieldRecordIntervention");
	}
	

	/**
	 * Register clarification interventions.
	 */
	private void registerClarificationInterventions(){
		// TODO: does it make sense to introduce two new INTERVENTIONS telling the user to open the CPA to check
		// performance or look at other videos
		clarificationInterventions.add("at.tugraz.kmi.am.interventions.InformationPauseIntervention");
	}
	


	/**
	 * Register motivation interventions.
	 */
	private void registerMotivationInterventions(){
		motivationInterventions.add("at.tugraz.kmi.medokyService.interventions.TwitterActivityIntervention");
	}
	


	
	/**
	 * Register competence related interventions.
	 */
	private void registerCompetenceRelatedInterventions(){
		competenceRelatedInterventions.add("at.tugraz.kmi.am.interventions.ExperiencedNPCIntervention");
	}
	
	/**
	 * Returns a list of all available interventions.
	 * 
	 * @return the interventions
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public synchronized ArrayList<Intervention> getInterventions() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ArrayList<Intervention> interventions = new ArrayList<Intervention>();
		interventions.addAll(getEmotionInterventions());
		interventions.addAll(getClarificationInterventions());
		interventions.addAll(getMotivationInterventions());
		interventions.addAll(getCompetenceRelatedInterventions());
		return interventions;
	}
	
	/**competenceRelatedInterventions
	 * Returns a list containing all registered emotion interventions.
	 * 
	 * @return the emotion intervention
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public synchronized ArrayList<Intervention> getEmotionInterventions() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		ArrayList<Intervention> interventions = new ArrayList<Intervention>();
		
		for (String classname : emotionInterventions)
			interventions.add((Intervention)Class.forName(classname).newInstance());
		
		return interventions;
	}
	
	/**
	 * Returns a list containing all registered clarification interventions.
	 * 
	 * @return the clarification intervention
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public synchronized ArrayList<Intervention> getClarificationInterventions() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		ArrayList<Intervention> interventions = new ArrayList<Intervention>();
		
		for (String classname : clarificationInterventions)
			interventions.add((Intervention)Class.forName(classname).newInstance());
		
		return interventions;
	}
	
	/**
	 * Returns a list containing all registered motivation interventions.
	 * 
	 * @return the motivation intervention
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public synchronized ArrayList<Intervention> getMotivationInterventions() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		ArrayList<Intervention> interventions = new ArrayList<Intervention>();
		
		for (String classname : motivationInterventions)
			interventions.add((Intervention)Class.forName(classname).newInstance());
		
		return interventions;
	}

	/**
	 * Returns a list containing all registered competence related interventions.
	 * 
	 * @return the competence related intervention
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public synchronized ArrayList<Intervention> getCompetenceRelatedInterventions() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		ArrayList<Intervention> interventions = new ArrayList<Intervention>();
		
		for (String classname : competenceRelatedInterventions)
			interventions.add((Intervention)Class.forName(classname).newInstance());
		
		return interventions;
	}

}
