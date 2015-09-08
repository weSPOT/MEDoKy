import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;


public class BookmarkManager {

	ArrayList<Bookmark> bookmarks;
	HashMap<String, Integer> tags;
	HashMap<String, HashMap<Integer, Long>> usertags;
	
	
	public BookmarkManager(ArrayList<Bookmark> bookmarks) {
		super();
		this.bookmarks = bookmarks;
		this.tags = new LinkedHashMap<String, Integer>();
		
	}
	
	public HashMap<Integer, Long> getUserTags(String userId){
		return usertags.get(userId);
	}
	
	public LinkedList<String> getTagSelection(HashMap<Integer, Double> tagRanking){
		LinkedList<String> tags = new LinkedList();
		LinkedList<Integer> tagIds = new LinkedList();
		for (Map.Entry<Integer, Double> tag : tagRanking.entrySet()){
			// solange es weitere einträge gibt, und die 1. sechs
//			whi
	//			if (tag.getValue()>)
		}
		// return the 6 highest valued tags		
		return tags;
	}
}
