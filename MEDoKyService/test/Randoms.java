import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


public class Randoms {

    public static void main(String[] args) {
    	Randoms r = new Randoms();
    	//r.testSet();
    	r.testSortedMap();
    }
    
    private void testSet(){
    	Set<String> baseSet = new HashSet<String>();
    	Set<String> linkedSet = new HashSet<String>();
    	Set<String> returnSet = new HashSet<String>();
    	
    	baseSet.add("ONe");
    	baseSet.add("Two");
    	baseSet.add("Three");
    	baseSet.add("Four");
    	baseSet.add("Five");
    	
    	linkedSet.add("Two");
    	linkedSet.add("Three");
    	linkedSet.add("Four");
    
    	returnSet = baseSet; 
		returnSet.removeAll(linkedSet);
		if (returnSet.size()>0)
			System.out.println(returnSet);
    }
    
    private void testSortedMap(){
        HashMap<String,Double> map = new HashMap<String,Double>();
        map.put("M",99.5);
        map.put("B",3.3);
        map.put("C",67.4);
        map.put("D",67.3);
        map.put("a",20.3);
        map.put("b",20.3);
        map.put("X",20.3);

        ValueComparator bvc =  new ValueComparator(map);
        TreeMap<String,Double> sorted_map = new TreeMap<String,Double>(bvc);

        sorted_map.putAll(map);             

        System.out.println("unsorted map: "+map);



        System.out.println("results: "+sorted_map);
        System.out.println("results: "+sorted_map.firstKey());
        sorted_map.values().removeAll(Collections.singleton(20.3));
        for (Entry<String, Double> entry : sorted_map.entrySet())
        	System.out.println("iterated results: "+entry.getValue()+entry.getKey());
        
    
    }
    
    
   

}



class ValueComparator implements Comparator<String> {

    Map<String, Double> base;
    public ValueComparator(Map<String, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }

}







