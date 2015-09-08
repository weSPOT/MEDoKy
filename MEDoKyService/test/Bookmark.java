import java.util.HashMap;
import java.util.LinkedHashMap;


public class Bookmark {

	String user;
	HashMap<Integer, String>tags;
	long timestamp;
	
	
	
	public Bookmark(String user, HashMap<Integer, String> tags, long timestamp) {
		super();
		this.user = user;
		this.tags = tags;
		this.timestamp = timestamp;
	}

	public Bookmark() {
		super();
		this.user = "not defined";
		this.tags = new LinkedHashMap<Integer, String>();
		this.timestamp = 0;
	}

	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public HashMap<Integer, String> getTags() {
		return tags;
	}
	public void setTags(HashMap<Integer, String> tags) {
		this.tags = tags;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}


