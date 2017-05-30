package com.wolfesoftware.stats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.wolfesoftware.entity.LogMessage;
import com.wolfesoftware.entity.LogMessageFactory;

import java.util.SortedSet;
import java.util.TreeSet;

public class LogFileStats {
	/* these indices are the main output of this class */
	private SortedSet<Entry<String, Integer>> ipCountDescendingOrder;//index
	private SortedSet<Entry<String, Integer>> popularRequestsDescendingOrder;//index

	private int numberOfLogMessages = 0;
	private Map<String, Integer> ipCount = new HashMap<>();
	private Map<String, Integer> popularRequests = new HashMap<>();
	private LogMessageFactory logMessageFactory;
	
	public LogFileStats(InputStream logFileInputStream) throws ParseException, IOException {
		logMessageFactory = new LogMessageFactory();
		InputStreamReader isr = new InputStreamReader(logFileInputStream);
		String logFileMessage;
		try (BufferedReader br = new BufferedReader(isr)) {
			while ((logFileMessage = br.readLine()) != null) {
				LogMessage logMessage = logMessageFactory.getLogMessage(logFileMessage);
				numberOfLogMessages++;
				addCountToIP(logMessage.getIP());
				addCountToRequests(logMessage.getRequest());
			}
		}
		ipCountDescendingOrder = this.sortByValue(ipCount);
		popularRequestsDescendingOrder = this.sortByValue(popularRequests);
	}
	
	public Map<String, Integer> getIPsWithXNumberOfHits(int numberOfHits) {
		Map<String, Integer> mapOfIPsWithXNumberOfHits = new HashMap<>();
		Iterator<Entry<String, Integer>> iterator = this.ipCountDescendingOrder.iterator();
		while (iterator.hasNext()){
			Entry<String, Integer> next = iterator.next();
			if (next.getValue() >= numberOfHits){
				mapOfIPsWithXNumberOfHits.put(next.getKey(), next.getValue());
			} else {
				break;
			}
		}
		return mapOfIPsWithXNumberOfHits;
	}

	public Map<String, Integer> getMostPopularRequests(int howManyPopularRequests) {
		Map<String, Integer> popularRequests = new HashMap<>();
		Iterator<Entry<String, Integer>> iterator = this.popularRequestsDescendingOrder.iterator();
		int x = 0;
		while (iterator.hasNext() && x<howManyPopularRequests){
			Entry<String, Integer> next = iterator.next();
				popularRequests.put(next.getKey(), next.getValue());
				x++;
		}
		return popularRequests;
	}
	
	public int getNumberOfLogMessages() {
		return numberOfLogMessages;
	}

	public int getNumberOfUniqueIPAddresses() {
		return ipCount.size();
	}

	private void addCountToIP(String ip) {
		Integer integer = ipCount.get(ip);
		if (null == integer) {
			// System.out.println("adding this IP" + ip);
			ipCount.put(ip, 1);
		} else {
			// System.out.println("adding this IP" + ip);
			ipCount.put(ip, integer + 1);
		}
	}
	
	private void addCountToRequests(String request) {
		Integer count = popularRequests.get(request);
		if (null == count) {
			// System.out.println("adding this IP" + ip);
			popularRequests.put(request, 1);
		} else {
			// System.out.println("adding this IP" + ip);
			popularRequests.put(request, count + 1);
		}
		
	}

	public void prettyPrint() {
		System.out.println("*****Number of unique IP addresses: " + this.getNumberOfUniqueIPAddresses());
		System.out.println("*****Number of requests processed:" + this.getNumberOfLogMessages());
		System.out.println("*****IP Counts");
		prettyPrintSortedSet(ipCountDescendingOrder);
		System.out.println("*****Popular Requests");
		prettyPrintSortedSet(popularRequestsDescendingOrder);
	}
	
	private <K,V> void prettyPrintSortedSet(SortedSet<Entry<K, V>> sortedSet) {
		Iterator<Entry<K, V>> iterator = sortedSet.iterator();
		while (iterator.hasNext()){
			Entry<K, V> next = iterator.next();
			System.out.println(next.getKey() + " " + next.getValue());
		}
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

		
}
