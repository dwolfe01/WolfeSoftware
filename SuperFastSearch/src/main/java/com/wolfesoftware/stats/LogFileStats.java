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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedSet;
import java.util.TreeSet;

import com.wolfesoftware.entity.LogMessage;
import com.wolfesoftware.entity.LogMessageFactory;
import com.wolfesoftware.exception.LogMessageFactoryException;

public class LogFileStats {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogFileStats.class);
	
	/* these indices are the main output of this class */
	private SortedSet<Entry<String, Integer>> ipCountDescendingOrder;//index
	private SortedSet<Entry<String, Integer>> popularRequestsDescendingOrder;//index

	private int numberOfLogMessages = 0;
	private Map<String, Integer> ipCount = new HashMap<>();
	private Map<String, Integer> popularRequests = new HashMap<>();
	private LogMessageFactory logMessageFactory;
	
	public LogFileStats(InputStream logFileInputStream, String logFilePattern, String dateFormat,String logFileOrdering) throws IOException, ParseException {
		logMessageFactory = new LogMessageFactory(logFilePattern, dateFormat, logFileOrdering);
		String logFileMessage;
		try (InputStreamReader isr = new InputStreamReader(logFileInputStream);BufferedReader br = new BufferedReader(isr)) {
			while ((logFileMessage = br.readLine()) != null) {
				LogMessage logMessage;
				try {
					logMessage = logMessageFactory.getLogMessage(logFileMessage);
					numberOfLogMessages++;
					addCountToIP(logMessage.getIP());
					addCountToRequests(logMessage.getRequest());
				} catch (LogMessageFactoryException e) {
					LOGGER.info(e.getMessage());
				}
			}
		}
		//because I have used try with resources block with two resources then they both will be closed at this point i.e. after the try block
		// if either of those objects throw an exception, e.g. the IOException then they will be closed here before that Exception is passed on to the calling method
		//if the close method itself throws an exception as well as the resource itself then the system will only throw the exception as thrown by the resource
		// it would be possible to retrieve that suppressed exception.
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

	public String prettyPrint() {
		String prettyString = "";
		prettyString += "Number of unique IP addresses: " + this.getNumberOfUniqueIPAddresses() + "\n";
		prettyString += "Number of requests processed:" + this.getNumberOfLogMessages() + "\n";
		prettyString += "Popular Requests \n";
		prettyString += prettyPrintSortedSet(popularRequestsDescendingOrder) + "\n";
		prettyString += "IP Counts + \n";
		prettyString += prettyPrintSortedSet(ipCountDescendingOrder) + "\n";
		return prettyString;
	}
	
	private <K,V> String prettyPrintSortedSet(SortedSet<Entry<K, V>> sortedSet) {
		String prettyString = "";
		Iterator<Entry<K, V>> iterator = sortedSet.iterator();
		while (iterator.hasNext()){
			Entry<K, V> next = iterator.next();
			prettyString += next.getKey() + " " + next.getValue() + "\n";
		}
		return prettyString;
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
