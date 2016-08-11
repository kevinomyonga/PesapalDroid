/**
 * 
 */
package com.kevinomyonga.pesapaldroid.exception;

/**
 * @author Davide Parise mailto:bubini.mara5@gmail.com
 * Sep 10, 2014
 *
 */
public class PesapalRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2898423848610915504L;

	/**
	 * 
	 */
	public PesapalRuntimeException() {
		super();
	}

	/**
	 * @param detailMessage Detail Message
	 * @param throwable Throwable
	 */
	public PesapalRuntimeException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	/**
	 * @param detailMessage Detail Message
	 */
	public PesapalRuntimeException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * @param throwable Throwable
	 */
	public PesapalRuntimeException(Throwable throwable) {
		super(throwable);
	}

	
}
