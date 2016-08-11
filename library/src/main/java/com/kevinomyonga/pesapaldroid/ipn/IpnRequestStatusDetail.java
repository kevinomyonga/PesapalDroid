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
 * Sep 19, 2014
 *
 */
public class IpnRequestStatusDetail extends AbIpnRequest {

	private final String id;
	private final String reference;
	
	/**
	 * @param id Id
	 * @param reference Reference
	 */
	public IpnRequestStatusDetail(String id, String reference) {
		this.id = id;
		this.reference = reference;
	}

	/* (non-Javadoc)
	 * @see com.davide.parise.pesapal.IRequest#getURL()
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
		return Pesapal.isDEMO() ? ApiUrlConstants.IPN_STATUS_DETAIL.url_demo : ApiUrlConstants.IPN_STATUS_DETAIL.url_live;
	}
	/* (non-Javadoc)
	 * @see com.davide.parise.pesapal.ipn.AbIpnRequest#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.davide.parise.pesapal.ipn.AbIpnRequest#getReference()
	 */
	@Override
	public String getReference() {
		return reference;
	}

}
