package org.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

public class JSONObjectFromUrl {
	
	/**
	 * Class to read and convert JSON files from an URL into a JSONObject
	 * 
	 * Written by Tim Visée - timvisee.com
	 * Copyright (c) Tim Visée 2013, All rights reserved.
	 */
	
	public JSONObjectFromUrl() { }
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    
	    // Current index
	    int cp;
	    while ((cp = rd.read()) != -1)
	    	sb.append((char) cp);
	    
	    // Return the string that was build
	    return sb.toString();
	}
	
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		try {
			// Get the input stream
		    URL urlObj = new URL(url);
		    URLConnection con = urlObj.openConnection();
		    con.setConnectTimeout(3 * 1000);
		    con.setReadTimeout(3 * 1000);
		    InputStream is = null;
		    
		    try {
		    	is = con.getInputStream();
		    } catch(UnknownHostException ex) {
		    	System.out.println("[SafeCreeper] [ERROR] Can't connect to Safe Creeper server!");
		    	return new JSONObject("{\"error\":\"Can't connect to Safe Creeper server!\"}");
		    }
		    
		    // Parse and return the JSON as JSONObject
		    try {
			    BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			    String jsonText = readAll(rd);
			    JSONObject json = new JSONObject(jsonText);
			    return json;
		    } finally {
		    	is.close();
		    }
		} catch(SocketTimeoutException ex) {
			return null;
		}
	}
}
