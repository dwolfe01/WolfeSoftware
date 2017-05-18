package com.wolfesoftware.stats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.wolfesoftware.entity.LogMessage;
import com.wolfesoftware.entity.LogMessageFactory;

import java.util.SortedSet;
import java.util.TreeSet;

public class LogFileStats {

	int numberOfLogMessages = 0;
	private static Map<String, Integer> ipCount = new HashMap<>();
	SortedSet<Entry<String, Integer>> ipCountDescendingOrder;
	LogMessageFactory logMessageFactory;
	
	public LogFileStats(InputStream logFileInputStream) throws ParseException, IOException {
		logMessageFactory = new LogMessageFactory();
		InputStreamReader isr = new InputStreamReader(logFileInputStream);
		String logFileMessage;
		try (BufferedReader br = new BufferedReader(isr)) {
			while ((logFileMessage = br.readLine()) != null) {
				LogMessage logMessage = logMessageFactory.getLogMessage(logFileMessage);
				setNumberOfLogMessages(++numberOfLogMessages);
				addCountToIP(logMessage.getIP());
			}
		}
		ipCountDescendingOrder = this.sortByValue(ipCount);
	}

	public int getNumberOfLogMessages() {
		return numberOfLogMessages;
	}

	public void setNumberOfLogMessages(int numberOfLogMessages) {
		this.numberOfLogMessages = numberOfLogMessages;
	}

	public int getNumberOfUniqueIPAddresses() {
		return ipCount.size();
	}

	public void addCountToIP(String ip) {
		Integer integer = ipCount.get(ip);
		if (null == integer) {
			// System.out.println("adding this IP" + ip);
			ipCount.put(ip, 1);
		} else {
			// System.out.println("adding this IP" + ip);
			ipCount.put(ip, integer + 1);
		}
	}

	public void prettyPrint() {
		System.out.println("Number of unique IP addresses: " + this.getNumberOfUniqueIPAddresses());
	}
	
	protected <K,V extends Comparable<? super V>>
	SortedSet<Entry<K,V>> sortByValue(Map<K,V> map) {
	    SortedSet<Entry<K,V>> sortedEntries = new TreeSet<Entry<K,V>>(
	        new Comparator<Entry<K,V>>() {
	            @Override public int compare(Entry<K,V> e1, Entry<K,V> e2) {
	                int res = e2.getValue().compareTo(e1.getValue());
	                return res != 0 ? res : 1;
	            }
	        }
	    );
	    sortedEntries.addAll(map.entrySet());
	    return sortedEntries;
	}

	public Map<String, Integer> getIPsWithXNumberOfHits(int numberOfHits) {
		Map<String, Integer> mapOfIPsWithXNumberOfHits = new HashMap<>();
		Iterator<Entry<String, Integer>> iterator = this.ipCountDescendingOrder.iterator();
		while (iterator.hasNext()){
			Entry<String, Integer> next = iterator.next();
			if (next.getValue() > numberOfHits){
				mapOfIPsWithXNumberOfHits.put(next.getKey(), next.getValue());
			} else {
				break;
			}
		}
		return mapOfIPsWithXNumberOfHits;
	}

}
