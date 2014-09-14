package com.five35.dex;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * A valid symbol in Dex.
 */
@Immutable
public abstract class Symbol {
	private static final Map<String, Symbol> SYMBOL_TABLE = new HashMap<>();

	private final int leftBindingPower;
	private final String name;

	Symbol(final String name, final int leftBindingPower) {
		this.name = Preconditions.checkNotNull(name);
		this.leftBindingPower = leftBindingPower;
	}

	Symbol(final String name, final String characters, final int leftBindingPower) {
		this(name, leftBindingPower);

		Symbol.SYMBOL_TABLE.put(characters, this);
	}

	@Nonnull
	static Symbol getSymbol(final Token token) throws InvalidSymbolException {
		final String characters = Preconditions.checkNotNull(token).toString();

		if (Symbol.SYMBOL_TABLE.containsKey(characters)) {
			return Symbol.SYMBOL_TABLE.get(characters);
		}

		// TODO: recognize variable names and numbers

		throw new InvalidSymbolException(characters);
	}

	@Nonnull
	abstract Expression denoteLeft(final Parser parser, final Expression left) throws ParserException;

	@Nonnull
	abstract Expression denoteNull(final Parser parser) throws ParserException;

	int getLeftBindingPower() {
		return this.leftBindingPower;
	}

	@Override
	@Nonnull
	public String toString() {
		return this.name;
	}
}
