package qcm.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtils {
	public static String getHTML(String urlToRead) throws ClientProtocolException, IOException {
	    String result="";
	    CloseableHttpClient httpClient = HttpClients.createDefault();
	    try {
	        HttpGet getRequest = new HttpGet(urlToRead);
	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
	        result = httpClient.execute(getRequest, responseHandler);
	    }finally {
	        httpClient.close();
	    }
	    return result;
	}
	
	public static String postHTML(String urlToRead, HashMap<String, Object> params) throws ClientProtocolException, IOException {
	    String result="";
	    CloseableHttpClient httpClient = HttpClients.createDefault();
	    try {
	        HttpPost postRequest = new HttpPost(urlToRead);
	        
	        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        
	        for (Entry<String, Object> entry : params.entrySet())
	        {
	        	postParameters.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue().toString()));
	        }

	        postRequest.setEntity(new UrlEncodedFormEntity(postParameters));	        
	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
	        result = httpClient.execute(postRequest, responseHandler);
	    }finally {
	        httpClient.close();
	    }
	    return result;
	}
	
	public static String putHTML(String urlToRead, HashMap<String, Object> params) throws ClientProtocolException, IOException {
	    String result="";
	    CloseableHttpClient httpClient = HttpClients.createDefault();
	    try {
	        HttpPut putRequest = new HttpPut(urlToRead);
	        
	        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        
	        for (Entry<String, Object> entry : params.entrySet())
	        {
	        	postParameters.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue().toString()));
	        }

	        putRequest.setEntity(new UrlEncodedFormEntity(postParameters));	        
	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
	        result = httpClient.execute(putRequest, responseHandler);
	    }finally {
	        httpClient.close();
	    }
	    return result;
	}
	
	public static String deleteHTML(String urlToRead) throws ClientProtocolException, IOException {
	    String result="";
	    CloseableHttpClient httpClient = HttpClients.createDefault();
	    try {
	        HttpDelete deleteRequest = new HttpDelete(urlToRead);
	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
	        result = httpClient.execute(deleteRequest, responseHandler);
	    }finally {
	        httpClient.close();
	    }
	    return result;
	}
}
