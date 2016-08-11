/**
 * 
 */
package com.kevinomyonga.pesapaldroid.exception;

/**
 * @author Davide Parise mailto:bubini.mara5@gmail.com
 * Sep 10, 2014
 *
 */
public class PesapalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5689637209671849107L;

	/**
	 * 
	 */
	public PesapalException() {
	}

	/**
	 * @param detailMessage Detail Message
	 */
	public PesapalException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * @param throwable Throwable
	 */
	public PesapalException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * @param detailMessage Detail message
	 * @param throwable Throwable
	 */
	public PesapalException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
