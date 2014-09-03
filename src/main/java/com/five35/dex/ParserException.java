package com.five35.dex;

import javax.annotation.concurrent.Immutable;

/**
 * An exception which occurs while parsing a Dex expression.
 */
@Immutable
public class ParserException extends Exception {
	private static final long serialVersionUID = 7740257106541709808L;

	@SuppressWarnings("javadoc")
	public ParserException(final String message) {
		super(message);
	}
}
