/**
 * 
 */
package com.kevinomyonga.pesapaldroid.exception;

/**
 * @author Davide Parise mailto:bubini.mara5@gmail.com
 * Sep 11, 2014
 *
 */
public class IpnRequestException extends PesapalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7159535116461873714L;

	/**
	 * 
	 */
	public IpnRequestException() {
	}

	/**
	 * @param detailMessage Detail Message
	 */
	public IpnRequestException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * @param throwable Throwable
	 */
	public IpnRequestException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * @param detailMessage Detail Message
	 * @param throwable Throwable
	 */
	public IpnRequestException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
