package com.example.hossein.quiztest;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	public HttpGet httpResponse;
//	HttpResponse httpResponse = null;

	// constructor
	public JSONParser() {

	}

	// function get json from url
	// by making HTTP POST or GET mehtod
	public JSONObject makeHttpRequest(String url, String method,

			
			List<NameValuePair> params) {
		Log.d("test2", "test2success");

		// Making HTTP request
		try {

			// check for request method
			if (method.equalsIgnoreCase("POST")) {
				// request method is POST
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				Log.d("test3", "test3success");

				HttpPost httpPost = new HttpPost(url);
				Log.d("test4", "test4success");
				httpPost.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
		    	 Log.d("test5", url);
		    	 

				 httpResponse=httpClient.execute(httpPost);
				
		    	 Log.d("test6", "test6success");

				HttpEntity httpEntity = httpResponse.getEntity();
		    	 Log.d("test7", "test7success");

				is = httpEntity.getContent();
				Log.d("test2", "test2success");

			} else if (method.equalsIgnoreCase("GET")) {
				// request method is GET
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);

				// HttpResponse
				 httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();

			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is,  "utf-8"), 8 );
			Log.d("test1", "test1success");

			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			Log.i("tagconvertstr", "[" + json + "]");
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}


}