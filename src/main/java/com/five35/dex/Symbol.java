package com.five35.dex;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * A valid symbol in Dex.
 */
@Immutable
public abstract class Symbol {
	private static final Map<String, Symbol> SYMBOL_TABLE = new HashMap<>();
	private static final Pattern SCALAR_PATTERN = Pattern.compile("\\p{Digit}+", Pattern.UNICODE_CHARACTER_CLASS);

	static final Symbol LITERAL_SCALAR = new ScalarSymbol("LITERAL_SCALAR", 0);

	// TODO: needs to work as a unary operator, too
	static final InfixSymbol OPERATOR_ADD = new InfixSymbol("OPERATOR_ADD", "+", 10) {
		@Override
		Result binary(final Result left, final Result right) {
			return new ScalarResult(left.asScalar() + right.asScalar());
		}
	};

	// TODO: needs to work as a unary operator, too
	static final InfixSymbol OPERATOR_SUBTRACT = new InfixSymbol("OPERATOR_SUBTRACT", "-", 10) {
		@Override
		Result binary(final Result left, final Result right) {
			return new ScalarResult(left.asScalar() - right.asScalar());
		}
	};

	static final InfixSymbol OPERATOR_MULTIPLY = new InfixSymbol("OPERATOR_MULTIPLY", "*", 20) {
		@Override
		Result binary(final Result left, final Result right) {
			return new ScalarResult(left.asScalar() * right.asScalar());
		}
	};

	static final InfixSymbol OPERATOR_DIVIDE = new InfixSymbol("OPERATOR_DIVIDE", "/", 20) {
		@Override
		Result binary(final Result left, final Result right) {
			return new ScalarResult(left.asScalar() / right.asScalar());
		}
	};

	static final Symbol VIRTUAL_TERMINATOR = new VirtualSymbol("VIRTUAL_TERMINATOR", "(end)", 0);

	private final int bindingPower;
	private final String name;

	Symbol(final String name, final int leftBindingPower) {
		this.name = Preconditions.checkNotNull(name);
		this.bindingPower = leftBindingPower;
	}

	Symbol(final String name, final String characters, final int bindingPower) {
		this(name, bindingPower);

		Symbol.SYMBOL_TABLE.put(characters, this);
	}

	@Nonnull
	static Symbol getSymbol(final Token token) throws InvalidSymbolException {
		final String characters = Preconditions.checkNotNull(token).toString();

		if (Symbol.SYMBOL_TABLE.containsKey(characters)) {
			return Symbol.SYMBOL_TABLE.get(characters);
		}

		if (Symbol.SCALAR_PATTERN.matcher(characters).matches()) {
			return Symbol.LITERAL_SCALAR;
		}

		// TODO: recognize variable names and numbers

		throw new InvalidSymbolException(characters);
	}

	int getBindingPower() {
		return this.bindingPower;
	}

	@Nonnull
	abstract Expression getLeftDenotation(final Parser parser, final Expression left) throws ParserException;

	@Nonnull
	abstract Expression getNullDenotation(final Parser parser) throws ParserException;

	@Override
	@Nonnull
	public String toString() {
		return this.name;
	}
}
