package edu.wpi.cs.fragmenttest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;

public class LoginTask extends AsyncTask<String, Void, String> {

    private Exception exception;

    protected String doInBackground(String... urls) {
        try {
        	/*
        	URL url = new URL("http://www.android.com/");
        	System.out.println("opening connection");
        	HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        	try {
        		System.out.println("getting Input stream");
        		InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        		System.out.println("reading");
        		listener.onRssItemSelected("this is what i got: " + in.read());
        	} finally {
        		urlConnection.disconnect();
        	}
        	*/
        	//listener.onRssItemSelected("oh");
        	
        	//set url http://localhost:8080/WPISuite/API
            //set http method
            //add headers
            
            HttpURLConnection connection = null;
        	boolean requestSendFail = false;
        	boolean responseBodyReadTimeout = false;
        	Exception exceptionRecv = null;
        	
        	String response = "No Response!";
        	
        	System.out.println("Entering Try");
        	
        	try {
        		// setup connection
        		URL url = new URL("http://130.215.15.18:8080/WPISuite/API/login");
        		connection = (HttpURLConnection) url.openConnection();
        		connection.setConnectTimeout(5000);
        		connection.setReadTimeout(5000);
        		connection.setRequestMethod("POST");
        		connection.setDoInput(true);
        		connection.setRequestProperty("Connection", "close");
        		
        		String basicAuth = "Basic ";
        		basicAuth += "YWRtaW46cGFzc3dvcmQ=";
        		// set request headers
        		connection.setRequestProperty("Authorization", basicAuth);
        		
        		System.out.println("Set up connection");
        		
        		connection.connect();
        		
        		System.out.println("Made connection");
        		
        		// get the response headers
        		Map<String, List<String>> responseHeaders = connection.getHeaderFields();
        		
        		// get the response code
        		int responseCode = connection.getResponseCode();
        		
        		// get the response message
        		String responseMessage = connection.getResponseMessage();

        		// get the response body
        		String responseBody = "";
        		InputStream in;
        		
        		if (responseCode < 400) {	// if the request succeeds, get the InputStream
        			in = connection.getInputStream();
        		}
        		else {	// if the request fails, get the ErrorStream
        			in = connection.getErrorStream();
        		}

        		System.out.println("Reading connection");
        		
        		// read response body
        		BufferedReader reader = new BufferedReader(new InputStreamReader(in), 1);
        		String line;
        		try {
        			while((line = reader.readLine()) != null) {
        				responseBody += line + "\n";
        			}
        		} catch (SocketTimeoutException e) {	// if there is a timeout while reading the body
        			exceptionRecv = e;
        			responseBodyReadTimeout = true;
        		} catch (IOException e) {	// if readLine() fails
        			exceptionRecv = e;
        		}
        		finally {	// make sure that the BufferedReader is closed
        			if (reader != null) {
        				reader.close();
        			}
        		}

        		System.out.println("Closed reader");
        		
        		response = responseCode + "\n" + responseMessage + "\n" + responseHeaders.toString() + "\n" + responseBody;
        		// create Response
        		//ResponseModel response = new Response(responseCode, responseMessage, responseHeaders, responseBody);
        		
        		// set the Request's response to the newly created response
        		//request.setResponse(response);
        		System.out.println(response);
        	} catch (IOException e) {
        		exceptionRecv = e;
        		requestSendFail = true;
        	} finally {
        		// close the connection
        		if (connection != null) {
        			connection.disconnect(); 
        		}
        	}
            
            // Send data to Activity
            return response;
        
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(String response) {
        // TODO: check this.exception 
        // TODO: do something with the feed
    	
    	System.out.println("Exception: " + this.exception);

        //System.out.println("onPostExecuteCalled: " +response);
    }
}
