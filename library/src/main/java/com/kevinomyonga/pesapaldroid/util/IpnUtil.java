/**
 * 
 */
package com.kevinomyonga.pesapaldroid.util;

import com.kevinomyonga.pesapaldroid.exception.IpnRequestException;
import com.kevinomyonga.pesapaldroid.ipn.AbIpnRequest;
import com.kevinomyonga.pesapaldroid.ipn.DefaultIpnRequest;

import java.util.HashMap;
import java.util.Map;

import oauth.signpost.OAuth;
import oauth.signpost.http.HttpParameters;

/**
 * @author Davide Parise mailto:bubini.mara5@gmail.com
 * Sep 15, 2014
 *
 */
public class IpnUtil {

	/**
	 * Create new instance of DefaultIpnRequest from callback receive to the transaction request.
	 * This occur when no follow the redirect.
	 * 
	 * @param callback - the callback received from pesapal
	 * @return - new instance of DefaultIpnRequest
	 * @throws IpnRequestException - if callback is null or empty
	 */
	public static DefaultIpnRequest createFromCallback(String callback) throws IpnRequestException{
		if(callback==null || callback.isEmpty())
			throw new IpnRequestException("Callback is Empty");
				
		int pos = callback.indexOf("=");
		if(pos==-1) 
			throw new IpnRequestException("Invalid Callback or Parameter absents");
				
		callback = OAuth.percentDecode(callback);		
		String paramUrl;
		try {
			paramUrl = callback.substring(pos+1);
		} catch (IndexOutOfBoundsException e) {
			throw new IpnRequestException("Invalid Callback or Parameter absents",e);
		}
		return new DefaultIpnRequest(paramUrl);
	}
	
	/**
	 * 
	 * @param url - The URL, no callback
	 * @return - the map of the parameter, can be empty or not contain key
	 * @throws IpnRequestException - if the URL is empty or not contain parameters
	 */
	public static Map<String,String> getIpnParamsFromUrl(String url) throws IpnRequestException{
		Map<String,String> map = new HashMap<String,String>();

		if(url==null || url.isEmpty())
			throw new IpnRequestException("Callback is Empty");
				
		int pos = url.indexOf('?');
		if(pos == -1 || pos+1>url.length())
			throw new IpnRequestException("Invalid Callback or Parameter absents");
		url = url.substring(pos+1);

		HttpParameters params = OAuth.decodeForm(url);
		if(params==null || params.isEmpty())
			throw new IpnRequestException("Invalid Callback or Parameter absents");

		if(params.containsKey(AbIpnRequest.PARAM_TARCK_ID))
			map.put(AbIpnRequest.PARAM_TARCK_ID, params.getFirst(AbIpnRequest.PARAM_TARCK_ID));
		
		if(params.containsKey(AbIpnRequest.PARAM_MERCHANT_REFERECE))
			map.put(AbIpnRequest.PARAM_MERCHANT_REFERECE, params.getFirst(AbIpnRequest.PARAM_MERCHANT_REFERECE));
		
		return map;
	}
}
