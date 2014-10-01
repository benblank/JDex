package com.five35.dex;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import java.util.Map;

class ScalarSymbol extends Symbol {
	ScalarSymbol(final String name) {
		super(name, 0);
	}

	@Override
	Expression getNullDenotation(final Parser parser) throws ParserException {
		final String characters = Preconditions.checkNotNull(parser).getCurrentToken().toString();
		final float value;

		try {
			value = Float.parseFloat(characters);
		} catch (final NumberFormatException ex) {
			// It shouldn't be possible to reach this point; the tokenizer
			// should never produce a token containing an unparseable
			// number.

			throw new ParserException(String.format("Could not parse '%s' as a number.", characters));
		}

		return new Expression() {
			final Result<?> result = new ScalarResult(value);

			@Override
			public Result<?> execute(final Optional<Map<String, Expression>> variables) {
				return this.result;
			}
		};
	}
}
