package com.wolfesoftware.sailfish.http.uptime;

import java.util.HashMap;
import java.util.Map;

public class UptimeHistory {
	
	Map<String,UptimeRecord> history = new HashMap<String,UptimeRecord>();
	
	public synchronized void update(String url, Long milliseconds) {
		UptimeRecord current = history.get(url);
		if (current==null){
			history.put(url, new UptimeRecord(1,milliseconds));
		} else {
			current.updateCount();
			current.updateMilliseconds(milliseconds);
		}
	}

	public Long getMilliseconds(String key) {
		return history.get(key).getMilliseconds();
	}
	
	public Double getAverageResponseTime(String key) {
		return history.get(key).getAverageTimeInMilliseconds();
	}
	
	public String prettyPrint() {
		String pretty = "***Uptime***\n";
		for (Map.Entry<String,UptimeRecord> entry : history.entrySet()) {
			UptimeRecord record = entry.getValue();
			pretty += "url:" + entry.getKey() + " count:" + record.getCount() + " average:" + record.getAverageTimeInMilliseconds() + " milliseconds";
			pretty += "\n";
		}
		return pretty;
	}


	
	private class UptimeRecord{
		private Long count;
		
		protected Long getCount() {
			return count;
		}

		protected Long getMilliseconds() {
			return milliseconds;
		}
		private Long milliseconds;
		
		UptimeRecord(long count, long millis){
			this.count = count;
			milliseconds = millis;
		}
		
		protected void updateCount(){
			count ++;
		}
		protected void updateMilliseconds(Long millis){
			milliseconds+=millis;
		}
		
		protected double getAverageTimeInMilliseconds(){
			return milliseconds/count;
		}
		
	}

}


