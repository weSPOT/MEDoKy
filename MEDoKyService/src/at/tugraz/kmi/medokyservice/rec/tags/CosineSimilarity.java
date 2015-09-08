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

import java.util.*;
import java.lang.Math;

/**
 * This class calculates the cosine similarity between two clusters, or entities 
 * in our cases. Each cluster consists of one mention at the initialization phase, 
 * because we initialize each mention as a cluster.
 * <P>
 * In the search phase, we need to merge two clusters, each of which contains at 
 * least one mention. So in this way, we need to extract features from the cluster 
 * pairs. We use List to represent the features of each cluster and then compute 
 * the cosine similarity between the feature pairs.
 * <P>
 * The formula to calculate the cosine similarity is borrowed from link:
 * http://en.wikipedia.org/wiki/Cosine_similarity
 * 
 * @author xie
 *
 */
public class CosineSimilarity {

	/**
	 * calculate the cosine similarity between feature vectors of two clusters
	 * 
	 * The feature vector is represented as HashMap<String, Double>. 
	 * 
	 * @param firstFeatures The feature vector of the first cluster
	 * @param secondFeatures The feature vector of the second cluster 
	 * @return the similarity measure
	 */
	public static Double calculateCosineSimilarity(HashMap<String, Double> firstFeatures, HashMap<String, Double> secondFeatures) {
		Double similarity = 0.0;
		Double sum = 0.0;	// the numerator of the cosine similarity
		Double fnorm = 0.0;	// the first part of the denominator of the cosine similarity
		Double snorm = 0.0;	// the second part of the denominator of the cosine similarity
		Set<String> fkeys = firstFeatures.keySet();
		Iterator<String> fit = fkeys.iterator();
		while (fit.hasNext()) {
			String featurename = fit.next();
			boolean containKey = secondFeatures.containsKey(featurename);
			if (containKey) {
				sum = sum + firstFeatures.get(featurename) * secondFeatures.get(featurename);
			}
		}
		fnorm = calculateNorm(firstFeatures);
		snorm = calculateNorm(secondFeatures);
		similarity = sum / (fnorm * snorm);
		return similarity;
	}
	
	/**
	 * calculate the norm of one feature vector
	 * 
	 * @param feature of one cluster
	 * @return
	 */
	public static Double calculateNorm(HashMap<String, Double> feature) {
		Double norm = 0.0;
		Set<String> keys = feature.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String featurename = it.next();
			norm = norm + Math.pow(feature.get(featurename), 2);
		}
		return Math.sqrt(norm);
	}
	
}