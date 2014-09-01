package com.five35.dex;

/**
 * An exception which occurs while parsing a Dex expression.
 */
public class ParserException extends Exception {
	private static final long serialVersionUID = 7740257106541709808L;

	@SuppressWarnings("javadoc")
	public ParserException(String message) {
		super(message);
	}
}
