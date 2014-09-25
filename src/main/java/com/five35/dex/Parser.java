package com.five35.dex;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.Nonnull;

/**
 * A parser for Dex code.
 */
public final class Parser {
	private Symbol currentSymbol;

	// Initialized with a dummy token to ensure empty source is handled
	// correctly.
	private Token currentToken = new Token(1, "");

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
		final Parser parser = new Parser(Preconditions.checkNotNull(source));
		final Expression expression = parser.getExpression(0);

		parser.advance(Optional.of(Symbol.VIRTUAL_TERMINATOR));

		return expression;
	}

	@Nonnull
	Symbol advance(final Optional<? extends Symbol> expected) throws ParserException {
		Preconditions.checkNotNull(expected);

		final Token nextToken;

		try {
			nextToken = this.tokens.next();
		} catch (final NoSuchElementException ex) {
			final int index = this.currentToken.getIndex() + this.currentToken.toString().length();

			throw new MissingSymbolException(index, expected, Optional.<Token>absent());
		}

		final Symbol symbol = Symbol.getSymbol(nextToken);

		// There is only one instance of each symbol, so object equality really
		// is what we want.
		if (!expected.isPresent() || expected.get() == symbol) {
			this.currentToken = nextToken;

			return symbol;
		}

		throw new MissingSymbolException(nextToken.getIndex(), expected, Optional.of(nextToken));
	}

	@Nonnull
	Token getCurrentToken() {
		return this.currentToken;
	}

	@Nonnull
	Expression getExpression(final int bindingPower) throws ParserException {
		this.currentSymbol = this.advance(Optional.<Symbol>absent());

		Expression expression = this.currentSymbol.getNullDenotation(this);
		Symbol nextSymbol = Symbol.getSymbol(this.previewNextToken());

		while (bindingPower < nextSymbol.getBindingPower()) {
			this.currentSymbol = this.advance(Optional.<Symbol>absent());

			expression = this.currentSymbol.getLeftDenotation(this, expression);
			nextSymbol = Symbol.getSymbol(this.previewNextToken());
		}

		return expression;
	}

	@Nonnull
	Token previewNextToken() throws MissingSymbolException {
		try {
			return this.tokens.peek();
		} catch (final NoSuchElementException ex) {
			throw new MissingSymbolException(this.currentToken.getIndex(), Optional.<Symbol>absent(), Optional.<Token>absent());
		}
	}
}
