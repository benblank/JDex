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
public class Symbol {
	private static final Map<String, Symbol> SYMBOL_TABLE = new HashMap<>();
	private static final Pattern SCALAR_PATTERN = Pattern.compile("\\p{Digit}+", Pattern.UNICODE_CHARACTER_CLASS);
	private static final Pattern VARIABLE_PATTERN = Pattern.compile("[\\p{Alpha}_]+", Pattern.UNICODE_CHARACTER_CLASS);

	static final Symbol LITERAL_SCALAR = new ScalarSymbol("LITERAL_SCALAR");
	static final Symbol VARIABLE_REFERENCE = new ReferenceSymbol("VARIABLE_REFERENCE");

	static final InfixOrPrefixSymbol OPERATOR_ADD = new InfixOrPrefixSymbol("OPERATOR_ADD", "+", 10, 30) {
		@Override
		public Result binary(final Result left, final Result right) {
			Preconditions.checkNotNull(left);
			Preconditions.checkNotNull(right);

			return new ScalarResult(left.asScalar() + right.asScalar());
		}

		@Override
		public Result unary(final Result operand) {
			Preconditions.checkNotNull(operand);

			return new ScalarResult(operand.asScalar());
		}
	};

	static final InfixOrPrefixSymbol OPERATOR_SUBTRACT = new InfixOrPrefixSymbol("OPERATOR_SUBTRACT", "-", 10, 30) {
		@Override
		public Result binary(final Result left, final Result right) {
			Preconditions.checkNotNull(left);
			Preconditions.checkNotNull(right);

			return new ScalarResult(left.asScalar() - right.asScalar());
		}

		@Override
		public Result unary(final Result operand) {
			Preconditions.checkNotNull(operand);

			return new ScalarResult(-operand.asScalar());
		}
	};

	static final InfixSymbol OPERATOR_MULTIPLY = new InfixSymbol("OPERATOR_MULTIPLY", "*", 20) {
		@Override
		public Result binary(final Result left, final Result right) {
			Preconditions.checkNotNull(left);
			Preconditions.checkNotNull(right);

			return new ScalarResult(left.asScalar() * right.asScalar());
		}
	};

	static final InfixSymbol OPERATOR_DIVIDE = new InfixSymbol("OPERATOR_DIVIDE", "/", 20) {
		@Override
		public Result binary(final Result left, final Result right) {
			Preconditions.checkNotNull(left);
			Preconditions.checkNotNull(right);

			return new ScalarResult(left.asScalar() / right.asScalar());
		}
	};

	static final Symbol VIRTUAL_TERMINATOR = new Symbol("VIRTUAL_TERMINATOR", "(end)", 0);

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

		if (Symbol.VARIABLE_PATTERN.matcher(characters).matches()) {
			return Symbol.VARIABLE_REFERENCE;
		}

		throw new InvalidSymbolException(characters);
	}

	int getBindingPower() {
		return this.bindingPower;
	}

	@Override
	@Nonnull
	public String toString() {
		return this.name;
	}

	@Nonnull
	@SuppressWarnings("static-method")
	Expression getLeftDenotation(final Parser parser, @SuppressWarnings("unused") final Expression left) throws ParserException {
		throw new SyntaxException(parser.getCurrentToken());
	}

	@Nonnull
	@SuppressWarnings("static-method")
	Expression getNullDenotation(final Parser parser) throws ParserException {
		throw new SyntaxException(parser.getCurrentToken());
	}
}
