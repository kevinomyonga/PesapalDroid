/**
 * 
 */
package com.kevinomyonga.pesapaldroid.ipn;

import com.kevinomyonga.pesapaldroid.ApiUrlConstants;
import com.kevinomyonga.pesapaldroid.Pesapal;
import com.kevinomyonga.pesapaldroid.exception.IpnRequestException;
import com.kevinomyonga.pesapaldroid.exception.PesapalException;

import oauth.signpost.OAuth;

/**
 * @author Davide Parise mailto:bubini.mara5@gmail.com
 * Sep 10, 2014
 *
 *	Implement Pesapal.com API querypaymentstatus
 */
public class IpnRequestStatus extends AbIpnRequest {
	
	private final String id;
	private final String reference;
	
	
	/**
	 * @param id Id
	 * @param reference Reference
	 * @throws IpnRequestException 
	 */
	public IpnRequestStatus(String id, String reference) {
		this.id = id;
		this.reference = reference;	
	}
	
	/**
	 * Build the request URL for send IPN.
	 * Note: Is NOT the one returned by transaction requests
	 * 
	 * @param callback - A generic URL with parameter set.
	 * @return - new instance
	 * @throws IpnRequestException - if callback is null or empty or not contain all parameter
	 */
	public IpnRequestStatus(String callback) throws IpnRequestException{
			this.id = getId(callback);
			this.reference = getReference(callback);
	}
	/* (non-Javadoc)
	 * @see com.davide.parise.pesapalandroidlib.lib.IRequest#getURL()
	 */
	@Override
	public String getURL() throws PesapalException {
		if(id==null || id.isEmpty() || reference == null || reference.isEmpty()){
			throw new IpnRequestException("ID or Refence not set");
		}
		
		final String baseUrl = getBaseUrl();
		String url = OAuth.addQueryParameters(baseUrl, PARAM_TARCK_ID,id,PARAM_MERCHANT_REFERECE,reference);
		return getURL(url);
	}

	/**
	 * 
	 * @return - the current base URL based on demo variable
	 */
	private String getBaseUrl(){
		return Pesapal.isDEMO() ? ApiUrlConstants.IPN_STATUS.url_demo : ApiUrlConstants.IPN_STATUS.url_live;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	
	
}
