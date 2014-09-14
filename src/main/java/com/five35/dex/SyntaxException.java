package com.five35.dex;

import javax.annotation.concurrent.Immutable;

/**
 * Thrown when a valid symbol is present at an invalid location.
 */
@Immutable
public class SyntaxException extends ParserException {
	private static final long serialVersionUID = 2615665712325470797L;

	@SuppressWarnings("javadoc")
	public SyntaxException(final Token token) {
		super(String.format("Binary operator %s is not valid at %d.", token, token.getIndex()));
	}
}
