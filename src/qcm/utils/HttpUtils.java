package qcm.utils;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

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
}
