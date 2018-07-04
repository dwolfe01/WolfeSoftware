package com.wolfesoftware.search.entity;

import java.io.UnsupportedEncodingException;

import org.hibernate.search.bridge.StringBridge;

public class ByteToStringBridge implements StringBridge {

	@Override
	public String objectToString(Object arg0) {
		byte[] myBytes = (byte[])arg0;
		try {
			return new String(myBytes,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

}
