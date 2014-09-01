package com.five35.dex;

/**
 * A parser for Dex code.
 */
public final class Parser {
	private final String source;

	private Parser(final String source) {
		this.source = source;
	}

	/**
	 * Parse the supplied Dex source into an expression.
	 * 
	 * @param source The Dex source to parse.
	 * @return An expression representing the parsed source.
	 * @throws ParserException When the supplied source cannot be parsed as a
	 *         Dex expression.
	 */
	public static Expression parse(final String source) throws ParserException {
		return new Parser(source).parse();
	}

	private Expression parse() throws ParserException {
		return null;
	}
}
