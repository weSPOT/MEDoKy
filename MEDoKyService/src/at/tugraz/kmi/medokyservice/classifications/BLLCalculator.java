package at.tugraz.kmi.medokyservice.classifications;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class BLLCalculator {
	
/*
	public static List<Map<Integer, Double>> getArtifactMaps(List<Bookmark> userLines, List<Bookmark> testLines, 
			List<Long> timestampList, List<Double> denomList, double dVal, boolean normalize) {
		
		List<Map<Integer, Double>> maps = new ArrayList<Map<Integer, Double>>();
		for (Bookmark data : userLines) {
			int userID = 0;
			//System.out.println(data);
			userID = data.getUserID();
			
			long baselineTimestamp = -1;
			if (userID >= maps.size()) {
				baselineTimestamp = Utilities.getBaselineTimestamp(testLines, userID, false);
				
				timestampList.add(baselineTimestamp);
				if (baselineTimestamp != -1) {
					maps.add(addActValue(data, new LinkedHashMap<Integer, Double>(), baselineTimestamp,  dVal));
				} else {
					maps.add(null);
				}
			} else {
				baselineTimestamp = timestampList.get(userID);
				if (baselineTimestamp != -1) {
					addActValue(data, maps.get(userID), baselineTimestamp, dVal);
				}
			}
		}
		// normalize values
		for (Map<Integer, Double> map : maps) {
			double denom = 0.0;
			if (map != null) {
				for (Map.Entry<Integer, Double> entry : map.entrySet()) {
					if (entry != null) {
						double actVal = Math.log(entry.getValue());
						denom += Math.exp(actVal);
						entry.setValue(actVal);
					}
				}
				denomList.add(denom);
				if (normalize) {
					for (Map.Entry<Integer, Double> entry : map.entrySet()) {
						if (entry != null) {
							double actVal = Math.exp(entry.getValue());
							entry.setValue(actVal / denom);
						}
					}
				}
			}
		}
		
		return maps;
	}
	*/
	
	public Map<Integer, String> getRankedTagList(int userID) {
		Map<Integer, String> resultMap = new LinkedHashMap<Integer, String>();
		Map<Integer, Double> actValues = new LinkedHashMap<Integer, Double>();
		//TODO: some code here 
		// call method to get TagsByGroup()
		// call method to get TagsByUser()
		// pre-process data 
		
		//addActValue(actValues, time)
		// normalize values
		// add MPValue to act values
		// determine 6 highest values
		// write 6 values to result Map or set TagRecommendations
		return resultMap;
	}
	
	
	// Bookmark needs tag id, timestamp
	/*private static Map<Integer, Double> addActValue(Bookmark data, Map<Integer, Double> actValues, long baselineTimestamp, double dVal) {
		if (!data.getTimestamp().isEmpty()) {
			//calculates recency
			Double recency = (double)(baselineTimestamp - Long.parseLong(data.getTimestamp()) + 1.0);
			//double recency = Math.ceil((baselineTimestamp - Long.parseLong(data.getTimestamp()) + 1.0) / 60.0 / 60.0 / 24.0 / 365.0 / 10);
			//System.out.println(recency);			
			Double newAct = Math.pow(recency, dVal * -1.0);
			
			for (Integer value : data.getTags()) {
				Double oldAct = actValues.get(value);
				if (!newAct.isInfinite() && !newAct.isNaN()) {
					actValues.put(value, (oldAct != null ? oldAct + newAct : newAct));
				} else {
					System.out.println(data.getUserID() + "_" + baselineTimestamp + " " + data.getTimestamp());
				}
			}
		}
		return actValues;
	}*/
}
