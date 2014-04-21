package com.seniordesign.ultimatescorecard.networkdatabase.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class AdminNetworkHelper {
	
	
	protected static final String TAG_NAME = "name";
	protected static final String TAG_PASS = "pass";
	
	protected static final String TAG_USER = "user";
	protected static final String TAG_PASSWORD = "password";
	protected static final String TAG_SUCCESS = "success";
	
	protected static final String dburl = "http://tusk.zapto.org/php/";
	protected static final String url_create_user = dburl + "create_user.php";
	protected static final String url_authenticate_user = dburl + "authenticate.php";
	
	public AdminNetworkHelper () {  
	};
	
	public void createUser(String name, String password){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(TAG_NAME, name));
		params.add(new BasicNameValuePair(TAG_PASS, password));
		HttpParameter parameter = new HttpParameter(url_create_user,	"POST", params);
		AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	}
	
public boolean authenticateUser(String user, String password){
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	params.add(new BasicNameValuePair(TAG_USER, user));
	params.add(new BasicNameValuePair(TAG_PASSWORD, password));
	HttpParameter parameter = new HttpParameter(url_authenticate_user,"POST", params);
	AsyncTask<HttpParameter, Void, JSONObject> result = new HttpRequest().execute(parameter);
	try {
		JSONObject t = result.get();
		int v = Integer.parseInt(t.getString(TAG_SUCCESS));
		Log.i("INTEG", "Authenticate returns: " + v);
		if (v==1){
			return true;
		}
		else{
			return false;
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	} 
	
	
}
	

}
