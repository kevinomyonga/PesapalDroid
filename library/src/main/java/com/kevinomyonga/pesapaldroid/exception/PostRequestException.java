/**
 * 
 */
package com.kevinomyonga.pesapaldroid.exception;

/**
 * @author Davide Parise mailto:bubini.mara5@gmail.com
 * Sep 10, 2014
 *
 */
public class PostRequestException extends PesapalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1146022332752691348L;

	/**
	 * 
	 */
	public PostRequestException() {
	}

	/**
	 * @param detailMessage Detail Message
	 */
	public PostRequestException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * @param throwable Throwable
	 */
	public PostRequestException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * @param detailMessage Detail Message
	 * @param throwable Throwable
	 */
	public PostRequestException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
