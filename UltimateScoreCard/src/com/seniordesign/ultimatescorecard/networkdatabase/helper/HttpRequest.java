package com.seniordesign.ultimatescorecard.networkdatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

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

import android.os.AsyncTask;
import android.util.Log;

public class HttpRequest extends AsyncTask<HttpParameter, Void, JSONObject> {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	
	private JSONObject _result;
	
	@Override
	protected JSONObject doInBackground(HttpParameter... input) {
		HttpParameter p = input[0];
		String url = p.get_url();
		String method = p.get_method();
		List<NameValuePair> params = p.get_params();
		
		
		// Making HTTP request
		try {
			
			// check for request method
			if(method == "POST"){
				Log.i("NH", "NH: Beginning of Post section");
				// request method is POST
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				Log.i("NH", "NH: Before post call");
				HttpPost httpPost = new HttpPost(url);
				HttpPost httpPost2 = new HttpPost();
				Log.i("NH", "NH: Before set entity");
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				Log.i("NH", "NH: Before response");
				HttpResponse httpResponse = httpClient.execute(httpPost);
				Log.i("NH", "NH: Before get entity");
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				Log.i("NH", "NH: End of Post section");
			}else if(method == "GET"){
				// request method is GET
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);

				HttpResponse httpResponse = httpClient.execute(httpGet);
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
					is, "iso-8859-1"), 8);
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
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		_result = jObj;
		return jObj;

	}

public JSONObject returnValue(){
	return _result;
}

}
