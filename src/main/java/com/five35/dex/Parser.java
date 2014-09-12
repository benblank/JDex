package com.five35.dex;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * A parser for Dex code.
 */
public final class Parser {
	private final PeekingIterator<Token> tokens;

	private Parser(final String source) {
		final List<Token> tokenList = Tokenizer.tokenize(Preconditions.checkNotNull(source));

		this.tokens = Iterators.peekingIterator(tokenList.iterator());
	}

	/**
	 * Parse the supplied Dex source into an expression.
	 * 
	 * @param source The Dex source to parse.
	 * @return An expression representing the parsed source.
	 * @throws ParserException When the supplied source cannot be parsed as a
	 *         Dex expression.
	 */
	@Nonnull
	public static Expression parse(final String source) throws ParserException {
		return new Parser(Preconditions.checkNotNull(source)).parse();
	}

	@Nonnull
	private Expression parse() throws ParserException {
		return null;
	}
}
