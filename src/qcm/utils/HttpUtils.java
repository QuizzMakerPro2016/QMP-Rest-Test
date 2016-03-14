package qcm.utils;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
	
	//Not Finished
	public static String postHTML(String urlToRead) throws ClientProtocolException, IOException {
	    String result="";
	    CloseableHttpClient httpClient = HttpClients.createDefault();
	    try {
	        HttpPost postRequest = new HttpPost(urlToRead);
	        
	        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair("param1", "param1_value"));
	        postParameters.add(new BasicNameValuePair("param2", "param2_value"));

	        postRequest.setEntity(new UrlEncodedFormEntity(postParameters));	        
	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
	        result = httpClient.execute(postRequest, responseHandler);
	    }finally {
	        httpClient.close();
	    }
	    return result;
	}
}