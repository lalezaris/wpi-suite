package edu.wpi.cs.coreconnectfragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.Response;
import android.os.AsyncTask;

public class ServerRequest extends AsyncTask<Request, Integer, Response> {

    private Exception exception;

    protected Response doInBackground(Request... request) {
        try {
        	publishProgress(1);
        	request[0].send();
        	publishProgress(90);
        	
        	//set url http://localhost:8080/WPISuite/API
            //set http method
            //add headers
            
            HttpURLConnection connection = null;
        	boolean requestSendFail = false;
        	boolean responseBodyReadTimeout = false;
        	Exception exceptionRecv = null;
        	
        	Response response = new Response(0, null, null, null);
        	
        	System.out.println("Entering Try");
        	
        	try {
        		// setup connection
        		URL url = new URL("http://130.215.10.227:8080/WPISuite/API/login");
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
        		
        		//response = responseCode + "\n" + responseMessage + "\n" + responseHeaders.toString() + "\n" + responseBody;
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
