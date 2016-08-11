/**
 * 
 */
package com.kevinomyonga.pesapaldroid;

import com.kevinomyonga.pesapaldroid.exception.PesapalRuntimeException;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

/**
 * @author Davide Parise mailto:bubini.mara5@gmail.com
 * Sep 10, 2014
 *
 * Initialize the library
 */
public class Pesapal {
	// there isn't log ...
	public static boolean DEBUG = true; // use for debug purpose
	public static final String TAG = "Pesapal"; // use for debug
		
	private OAuthConsumer consumer;
	private boolean DEMO = false;
	
	private static Pesapal instance; // instance for store the consumer
	
	/**
	 * @param consumer_key Consumer Key
	 * @param consumer_secret Consumer Secret
	 */
	private Pesapal(String consumer_key, String consumer_secret) {
		consumer = new DefaultOAuthConsumer(consumer_key, consumer_secret);
	}
	
	/**
	 * Initialize the Pesapal library
	 * 
	 * @param consumer_key - the consumer key
	 * @param consumer_secret - the consumer secret
	 */
	public static void initialize(String consumer_key, String consumer_secret){
		instance = new Pesapal(consumer_key, consumer_secret);
	}

	/**
	 * Create the consumer from secret and key for sign request
	 * @return - OAuthConsumer for sign request
	 */
	public static OAuthConsumer getConsumer() {
		if(instance==null)
			throw new PesapalRuntimeException("Pesapal library not initialized, Call firs initialize()");
		return instance.consumer;
	}

	/**
	 * @return the demo
	 */
	public static boolean isDEMO() {
		if(instance==null)
			throw new PesapalRuntimeException("Pesapal library not initialized, Call firs initialize()");
		return instance.DEMO;
	}

	/**
	 * @param demo the demo to set
	 */
	public static void setDEMO(boolean demo) {
		if(instance==null)
			throw new PesapalRuntimeException("Pesapal library not initialized, Call firs initialize()");
		instance.DEMO = demo;
	}
	
	/**
	 * @return - default callback
	 */
	public static String getDefaultCallback() {
		return isDEMO() ? ApiUrlConstants.CALLBACK.callback_demo : ApiUrlConstants.CALLBACK.callback_live;
	}
}
