package com.wolfesoftware.sailfish.uptime;

import java.util.HashMap;
import java.util.Map;

public class UptimeHistory {
	
	Map<String,Long> history = new HashMap<String,Long>();
	
	public void update(String responseCode, Long milliseconds) {
		Long count = history.get(responseCode);
		if (count==null){
			history.put(responseCode, new Long(1));
		} else {
			history.put(responseCode, new Long(count + 1));
		}
		String output = "";
		for (Map.Entry<String,Long> entry : history.entrySet()) {
			output += entry.getKey() + ", " + entry.getValue();
		}
		System.out.println(output);
	}

}
