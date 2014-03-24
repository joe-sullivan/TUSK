package com.seniordesign.ultimatescorecard.networkdatabase;

import java.util.List;

import org.apache.http.NameValuePair;

public class HttpParameter {
	private String _url;
	private String _method;
	List<NameValuePair> _params;
	
	public HttpParameter(String url, String method, List<NameValuePair> params){
		_url = url;
		_method = method;
		_params = params;
	}

	public String get_url() {
		return _url;
	}

	public void set_url(String _url) {
		this._url = _url;
	}

	public String get_method() {
		return _method;
	}

	public void set_method(String _method) {
		this._method = _method;
	}

	public List<NameValuePair> get_params() {
		return _params;
	}

	public void set_params(List<NameValuePair> _params) {
		this._params = _params;
	}

	
	
	
}
