/**
 * 
 */
package com.kevinomyonga.pesapaldroid.ipn;

import com.kevinomyonga.pesapaldroid.Pesapal;
import com.kevinomyonga.pesapaldroid.exception.IpnRequestException;
import com.kevinomyonga.pesapaldroid.exception.PesapalException;


/**
 * @author Davide Parise mailto:bubini.mara5@gmail.com
 * Sep 11, 2014
 *
 * Util implementation for default callback.
 * The default callback is set to API for query status
 */
public class DefaultIpnRequest extends AbIpnRequest {

	private final String url;
	private final String id;
	private final String reference;
	

	/**
	 * 
	 * @param url - the URL with query parameter
	 * @throws IpnRequestException - if url is empty or have not parameters set
	 */
	public DefaultIpnRequest(String url) throws IpnRequestException {		
		this.url = url;
		id = getId(url);
		reference = getReference(url);
	}


	/* (non-Javadoc)
	 * @see com.davide.parise.pesapalandroidlib.lib.IRequest#getURL()
	 */
	@Override
	public String getURL() throws PesapalException {
		if(url == null || url.isEmpty())
			throw new IpnRequestException("DefaultIpnRequest URL can't be empty");
		
		String baseUrl = Pesapal.getDefaultCallback();
		if(!url.startsWith(baseUrl)){
			throw new IpnRequestException("callback not match.Leave default callback.");
		}
		
		if(id==null || id.isEmpty() || reference == null || reference.isEmpty()){
			throw new IpnRequestException("ID or Refence not set");
		}
		return getURL(url);
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
