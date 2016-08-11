/**
 * 
 */
package com.kevinomyonga.pesapaldroid.post;

import com.kevinomyonga.pesapaldroid.ApiUrlConstants;
import com.kevinomyonga.pesapaldroid.IRequest;
import com.kevinomyonga.pesapaldroid.Pesapal;
import com.kevinomyonga.pesapaldroid.exception.PostRequestException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.UUID;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;

/**
 * @author Davide Parise mailto:bubini.mara5@gmail.com
 * Sep 10, 2014
 *
 * Make the signed URL with XML post and callback.
 * For create a new instance of this class use the inner class Builder.
 * 
 * !!This class not check the error and NOT validate parameter for now!!
 * Make sure you pass all parameter correct.
 * 
 * After instantiate an object of this class make sure you have called Pesapal.initialize()
 * for initialize the library.
 */
public class PostRequest implements IRequest {	
	
	protected final String callback; // the callback return URL
	protected final String form; // the XML form to send by HTTP GET 
	protected final String baseUrl; // the base URL where send the GET request.
	
	/**
	 * This parameters came from the builder
	 * @param form - the from to send
	 * @param callback - the callback
	 */
	private PostRequest(String baseUrl,String form, String callback) {
		this.form = form;
		this.callback = callback;
		this.baseUrl = baseUrl;
	}
	
	/* (non-Javadoc)
	 * @see com.davide.parise.pesapalandroidlib.lib.IRequest#getString()
	 * 
	 */
	@Override
	public String getURL() throws PostRequestException {
		OAuthConsumer consumer = Pesapal.getConsumer();		
						
		String url = OAuth.addQueryParameters(baseUrl,"pesapal_request_data",form,"oauth_callback",callback);
		
		try {
			url = consumer.sign(url);
		} catch (OAuthMessageSignerException e) {
			throw new PostRequestException("Can't sign message",e);
		} catch (OAuthExpectationFailedException e) {
			throw new PostRequestException("Failed to sign message",e);
		} catch (OAuthCommunicationException e) {
			throw new PostRequestException("Communication failed while sign message",e);
		}
		
		String query;
		try {
			query = url.split("\\?")[1];
		} catch (IndexOutOfBoundsException e) {
			throw new PostRequestException("Invalid query parameters");
		}
		
		// the OAuth library add the other callback param.
		// remove it by calling keyset()
		HttpParameters params = OAuth.decodeForm(query);

		url = baseUrl;
		//params.remove(OAuth.OAUTH_CALLBACK); // remove duplicate of callback parameter
		Set<String> keyset = params.keySet();
		for(String key : keyset){								
			Set<String> values = params.get(key);
			for(String value : values){
				url = OAuth.addQueryParameters(url,key,value);
			}
		}					
		
		return url;
	}

	/* (non-Javadoc)
	 * @see com.davide.parise.pesapalandroidlib.lib.IRequest#get()
	 */
	@Override
	public URL get() throws PostRequestException {
		try {
			return new URL(getURL());
		} catch (MalformedURLException e) {
			throw new PostRequestException("Malformed url. base is "+baseUrl, e);
		}
	}

	

	/**
	 * @return the callback
	 */
	public String getCallback() {
		return callback;
	}

	/**
	 * @return the form
	 */
	public String getForm() {
		return form;
	}

	/**
	 * @return the baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}


	/**
	 * 
	 * @author Davide Parise mailto:bubini.mara5@gmail.com
	 * Sep 10, 2014
	 *
	 * Inner class for build the post request.
	 * The builder not check sanity of parameters passed
	 * 
	 * Make sure you pass all parameters correct
	 * Make sure you set all parameters
	 * 
	 * Automatically set the type MERCHANT and generate an unique reference.
	 * The callback is set by default to pesapal.com API for query status.
	 */
	public static class Builder{
		
		// API URL constants
		protected static final String url_demo = ApiUrlConstants.POST.url_demo;
		protected static final String url_demo_mobile = ApiUrlConstants.POST.url_demo_mobile;
		protected static final String url_live = ApiUrlConstants.POST.url_live;
		protected static final String url_live_mobile = ApiUrlConstants.POST.url_live_mobile;
		
		// default callback URL
		protected static final String callback_live = ApiUrlConstants.CALLBACK.callback_live;
		protected static final String callback_demo = ApiUrlConstants.CALLBACK.callback_demo;
		
		private final String POST_XML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				+ "<PesapalDirectOrderInfo xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
				+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "

				+ "Amount=\"%s\" Description=\"%s\" "
				+ "Type=\"%s\" Reference=\"%s\" "
				+ "FirstName=\"%s\" LastName=\"%s\" "
				+ "Email=\"%s\" PhoneNumber=\"%s\" "

				+ "xmlns=\"http://www.pesapal.com\" />";

		// variables for make the xml post
		private String amount;
		private String description;
		private String type;
		private String reference;
		private String fName;
		private String lName;
		private String mail;
		private String phone;
		
		// 
		private String callback; 
		private boolean isMobile;

		public static class TYPE{
			public static String MERCHANT = "MERCHANT";
			public static String ORDER = "ORDER";
		}
		
		/**
		 * Constructor that initialize some variables with default values
		 */
		public Builder(){
			type = TYPE.MERCHANT;
			callback = Pesapal.getDefaultCallback();
			reference = UUID.randomUUID().toString();
			isMobile = true;
			
		}
		
		/**
		 * Create new PostRequest object. 
		 * @return - new PostRequest object
		 */
		public PostRequest build(){
			String form = String.format(POST_XML, amount,description,type,reference,fName,lName,mail,phone);
			return new PostRequest(getBaseUrl(),form,callback);
		}
		
		/**
		 * Set the amount to pay
		 * @param amount - the amount to pay
		 * @return - this builder
		 */
		public Builder amount(String amount){
			this.amount = amount;
			return this;
		}
		
		/**
		 * Use inner-class TYPE to set it
		 * @param type - the one of MERCHANT or ORDER. The default value is MERCHANT
		 * @return - this
		 */
		public Builder type(String type){
			this.type = type;
			return this;
		}
		
		/**
		 * 
		 * @param description - the description
		 * @return - this
		 */
		public Builder description(String description){
			this.description = description;
			return this;
		}
		
		/**
		 * Set the reference. This must be unique for all transaction.
		 * It is set automatically it is not necessary to set it manually.
		 * The default value is generated by UUID.
		 * @param reference - the reference
		 * @return - this
		 */
		public Builder reference(String reference){
			this.reference = reference;
			return this;
		}
	
		/**
		 * Set the first and last name
		 * @param fName - first name
		 * @param lName - last name
		 * @return - this
		 */
		public Builder name(String fName,String lName){
			this.fName = fName;
			this.lName = lName;
			return this;
		}
		
		/**
		 * Set the one of mail or phone
		 * @param mail - the mail
		 * @return - this
		 */
		public Builder mail(String mail){
			this.mail = mail;
			return this;
		}
		
		/**
		 * Set the phone number. Must be set one of phone or mail
		 * @param phone - the phone number
		 * @return - this
		 */
		public Builder phone(String phone){
			this.phone = phone;
			return this;
		}
		
		/**
		 * Build a request from mobile. Change the Pesapal.com API URL for request
		 * 
		 * @param isMobile - the isMobile. The default value is true
		 * @return - this
		 */
		public Builder isMobile(boolean isMobile){
			this.isMobile = isMobile;
			return this;
		}
		
		/**
		 * Set the callback return URL
		 * 
		 * @param calback - this is set by default, change only if is necessary
		 * @return - this
		 */
		public Builder callback(String calback){
			this.callback = calback;
			return this;
		}

		/**
		 * Util method for get the Pesapal.com API URL
		 * based on demo and mobile variable.
		 * @return - this
		 */
		private String getBaseUrl(){
			if(Pesapal.isDEMO()){			
				return isMobile ? url_demo_mobile : url_demo;
			}else{
				return isMobile ? url_live_mobile : url_live;
			}
		}

	}
}
