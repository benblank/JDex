package com.five35.dex;

import com.google.common.base.Optional;
import javax.annotation.concurrent.Immutable;

/**
 * An exception which occurs when a symbol is expected but not available.
 */
@Immutable
public class MissingSymbolException extends ParserException {
	private static final long serialVersionUID = 466382546876378790L;

	private final Optional<Symbol> expected;
	private final Optional<Token> found;
	private final int index;

	MissingSymbolException(final int index, final Optional<Symbol> expected, final Optional<Token> found) {
		// In general, statements should be extracted rather than wrapped, but
		// that isn't possible for constructors' super() calls, so just disable
		// the formatter for this statement.

		//@formatter:off
		super(String.format("Expected symbol%s at %d, but found %s.",
			expected.isPresent() ? " " + expected.get().toString() : "",
			index,
			found.isPresent() ? found.get().toString() : "end of string"));
		//@formatter:on

		this.index = index;
		this.expected = expected;
		this.found = found;
	}

	/**
	 * @return The specific symbol expected, if any.
	 */
	public final Optional<Symbol> getExpected() {
		return this.expected;
	}

	/**
	 * @return The unexpected token found, if any.
	 */
	public final Optional<Token> getFound() {
		return this.found;
	}

	/**
	 * @return The index within the source at which this exception occurred.
	 */
	public final int getIndex() {
		return this.index;
	}
}
