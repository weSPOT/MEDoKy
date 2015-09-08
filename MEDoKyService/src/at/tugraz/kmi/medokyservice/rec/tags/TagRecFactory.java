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

import java.util.Random;

public class TagRecFactory {
	
	public static TagRecommender getRandomTagRecommender(Long groupId){
			if (groupId%2==0)
				return getIndividualTagRecommender();
		
			return getCollaborativeTagRecommender();
	}

	private static TagRecommender getIndividualTagRecommender(){
		Random rand = new Random(System.currentTimeMillis());
		switch (rand.nextInt(3)) {
		 case 0: return new BLLCalculatorIndividual(); 
		 case 1: return new MostPopularTagsIndividual();
         case 2: return new MinervaCalculatorIndividual();
         default:return new MinervaCalculatorIndividual();
		}
	}
	
	private static TagRecommender getCollaborativeTagRecommender(){
		Random rand = new Random(System.currentTimeMillis());
		switch (rand.nextInt(4)) {
		 case 0: return new BLLMPCalculator(); 
         case 1: return new MostPopularTagsGroup();
         case 2: return new MinervaCalculatorGroup();
         case 3: return new BLLCalculatorGroup();
         default:return new MinervaCalculatorGroup();
		}
	}
	
	public static TagRecommender getTagRecommender(String algorithm) {
		switch (algorithm) {
        case "MinervaGroup": return new MinervaCalculatorGroup();
        case "MinervaIndividual": return new MinervaCalculatorIndividual();
        case "BLLIndividual": return new BLLCalculatorIndividual(); 
		case "MPIndividual": return new MostPopularTagsIndividual();
		case "BLLMP": return new BLLMPCalculator(); 
        case "MPGroup": return new MostPopularTagsGroup();
        case "BLLGroup": return new BLLCalculatorGroup();
        default:return new RandomTags();
	 }
	}
}
