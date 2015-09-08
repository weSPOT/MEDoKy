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


public class Activities {

	private ArrayList<StudentInfo> comments;
	private ArrayList<StudentInfo> posts;
	private ArrayList<StudentInfo> tweets;
	private ArrayList<StudentInfo> spends;
	private ArrayList<StudentInfo> awards;
		
	public Activities(){
		this.comments = new ArrayList<StudentInfo>();
		this.posts = new ArrayList<StudentInfo>();
		this.tweets = new ArrayList<StudentInfo>();
		this.spends = new ArrayList<StudentInfo>();
		this.awards = new ArrayList<StudentInfo>();
	}
		
	public ArrayList<StudentInfo> getComments() {
		return comments;
	}
	public void setComments(ArrayList<StudentInfo> comments) {
		this.comments = comments;
	}
	public ArrayList<StudentInfo> getPosts() {
		return posts;
	}
	public void setPosts(ArrayList<StudentInfo> posts) {
		this.posts = posts;
	}
	public ArrayList<StudentInfo> getTweets() {
		return tweets;
	}
	public void setTweets(ArrayList<StudentInfo> tweets) {
		this.tweets = tweets;
	}
	public ArrayList<StudentInfo> getSpends() {
		return spends;
	}
	public void setSpends(ArrayList<StudentInfo> spends) {
		this.spends = spends;
	}
		
	public ArrayList<StudentInfo> getAwards() {
		return awards;
	}

	public void setAwards(ArrayList<StudentInfo> awards) {
		this.awards = awards;
	}

	public int getNumberOfEvents(){
		return this.comments.size()+this.posts.size()+this.tweets.size();
	}
	
	public void add(StudentInfo info){
		//System.out.println("INFO "+info.toString());

		if (info == null)
			System.out.println("UIUIUI");
		else if (info.verb.equals("comment") || info.verb.equals("commented"))
			this.comments.add(info);
		else if (info.verb.equals("post") || info.verb.equals("posted"))
			this.posts.add(info);
		else if (info.verb.equals("tweet") || info.verb.equals("tweeted"))
			this.tweets.add(info);
		else if (info.verb.equals("spend") || info.verb.equals("spent"))
			this.spends.add(info);
		else if (info.verb.equals("award") || info.verb.equals("awarded")){
			this.awards.add(info);
			//System.err.println("unknown Activity: "+info.verb);
		}
		else	
			System.err.println("unknown Activity: "+info.verb);		
	}
}
