package com.five35.dex;

import javax.annotation.concurrent.Immutable;

/**
 * An exception thrown when a token does not represent a known symbol.
 */
@Immutable
public class InvalidSymbolException extends ParserException {
	private static final long serialVersionUID = -7900230809459505220L;

	@SuppressWarnings("javadoc")
	public InvalidSymbolException(final String message) {
		super("Token does not correspond to a known symbol: " + message);
	}
}
