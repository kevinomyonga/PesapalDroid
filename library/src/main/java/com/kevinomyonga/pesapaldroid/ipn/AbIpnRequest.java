/**
 * 
 */
package com.kevinomyonga.pesapaldroid.ipn;

import com.kevinomyonga.pesapaldroid.IRequest;
import com.kevinomyonga.pesapaldroid.Pesapal;
import com.kevinomyonga.pesapaldroid.exception.IpnRequestException;
import com.kevinomyonga.pesapaldroid.exception.PesapalException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;

/**
 * @author Davide Parise mailto:bubini.mara5@gmail.com
 * Sep 11, 2014
 *
 * Base class for sign IPN request
 */
public abstract class AbIpnRequest implements IRequest{
			
	public static final String PARAM_TARCK_ID = "pesapal_transaction_tracking_id";
	public static final String PARAM_MERCHANT_REFERECE = "pesapal_merchant_reference";

	/* (non-Javadoc)
	 * @see com.davide.parise.pesapalandroidlib.lib.IRequest#getURL()
	 */
	public String getURL(String url) throws PesapalException {
		final OAuthConsumer consumer = Pesapal.getConsumer();
		try {
			url = consumer.sign(url);
			//if(Pesapal.DEBUG)Log.i(Pesapal.TAG, "AbIpnRequest URL signed:"+url);
			return url;
		} catch (OAuthMessageSignerException e) {
			throw new IpnRequestException("AbIpnRequest Can't sign the message",e);
		} catch (OAuthExpectationFailedException e) {
			throw new IpnRequestException("AbIpnRequest Failed to sign message",e);
		} catch (OAuthCommunicationException e) {
			throw new IpnRequestException("AbIpnRequest Communication failed while sign message",e);
		}
	}

	/* (non-Javadoc)
	 * @see com.davide.parise.pesapalandroidlib.lib.IRequest#get()
	 */
	@Override
	public URL get() throws PesapalException {
		try {
			return new URL(getURL());
		} catch (MalformedURLException e) {
			throw new IpnRequestException("AbIpnRequest Malformed URL while get()",e);
		}
	}

	/**
	 * Get a parameter from query string
	 * @param url - the URL that contain the parameter
	 * @param param - the parameter name to find
	 * @return - the parameter value 
	 * @throws IpnRequestException - if the URL is null or empty
	 */
	public String getParamFromUrl(String url,String param) throws IpnRequestException{
		if(url==null || url.isEmpty())
			throw new IpnRequestException("Can't get ID.URL can't be empty.");
		try {
			int pos = url.indexOf('?');
			if(pos == -1)return "";
			
			String query = url.substring(pos+1);
			HttpParameters params = OAuth.decodeForm(query);
			
			return params.getFirst(param);
		} catch (IndexOutOfBoundsException e) {
			return ""; // no id found
		} catch (NoSuchElementException e) {
			return ""; // no id found
					
		} catch (NullPointerException e){
			return "";
		}
		
	}
	/**
	 * @return the id for the URL
	 * @throws IpnRequestException - if parameter is null or empty
	 */
	protected String getId(String url) throws IpnRequestException {
		return getParamFromUrl(url, PARAM_TARCK_ID);
	}

	/**
	 * @return the reference
	 * @throws IpnRequestException - if the URL is null or empty
	 */
	protected String getReference(String url) throws IpnRequestException {
		return getParamFromUrl(url, PARAM_MERCHANT_REFERECE);
	}
	
	public abstract String getId();
	public abstract String getReference();
}
