package com.five35.dex;

import com.google.common.base.Optional;
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
	static final Symbol LITERAL_SET = new SetSymbol("LITERAL_SET", "[", 0);
	static final Symbol VARIABLE_REFERENCE = new ReferenceSymbol("VARIABLE_REFERENCE");

	static final InfixOrPrefixSymbol OPERATOR_ADD = new InfixOrPrefixSymbol("OPERATOR_ADD", "+", 10, 30) {
		@Override
		public Result<?> binary(final Result<?> left, final Result<?> right) throws ExecutionException {
			final float augend = Preconditions.checkNotNull(left).cast(ScalarResult.class).getValue();
			final float addend = Preconditions.checkNotNull(right).cast(ScalarResult.class).getValue();

			return new ScalarResult(augend + addend);
		}

		@Override
		public Result<?> unary(final Result<?> operand) throws ExecutionException {
			final float identity = Preconditions.checkNotNull(operand).cast(ScalarResult.class).getValue();

			return new ScalarResult(identity);
		}
	};

	static final InfixOrPrefixSymbol OPERATOR_SUBTRACT = new InfixOrPrefixSymbol("OPERATOR_SUBTRACT", "-", 10, 30) {
		@Override
		public Result<?> binary(final Result<?> left, final Result<?> right) throws ExecutionException {
			final float minuend = Preconditions.checkNotNull(left).cast(ScalarResult.class).getValue();
			final float subtrahend = Preconditions.checkNotNull(right).cast(ScalarResult.class).getValue();

			return new ScalarResult(minuend - subtrahend);
		}

		@Override
		public Result<?> unary(final Result<?> operand) throws ExecutionException {
			final float negative = Preconditions.checkNotNull(operand).cast(ScalarResult.class).getValue();

			return new ScalarResult(-negative);
		}
	};

	static final InfixSymbol OPERATOR_MULTIPLY = new InfixSymbol("OPERATOR_MULTIPLY", "*", 20) {
		@Override
		public Result<?> binary(final Result<?> left, final Result<?> right) throws ExecutionException {
			final float multiplicand = Preconditions.checkNotNull(left).cast(ScalarResult.class).getValue();
			final float multiplier = Preconditions.checkNotNull(right).cast(ScalarResult.class).getValue();

			return new ScalarResult(multiplicand * multiplier);
		}
	};

	static final InfixSymbol OPERATOR_DIVIDE = new InfixSymbol("OPERATOR_DIVIDE", "/", 20) {
		@Override
		public Result<?> binary(final Result<?> left, final Result<?> right) throws ExecutionException {
			final float dividend = Preconditions.checkNotNull(left).cast(ScalarResult.class).getValue();
			final float divisor = Preconditions.checkNotNull(right).cast(ScalarResult.class).getValue();

			return new ScalarResult(dividend / divisor);
		}
	};

	static final Symbol OPERATOR_SUBEXPRESSION = new Symbol("OPERATOR_SUBEXPRESSION", "(", 30) {
		@Override
		Expression getNullDenotation(final Parser parser) throws ParserException {
			final Expression expression = Preconditions.checkNotNull(parser).getExpression(0);

			parser.advance(Optional.of(Symbol.VIRTUAL_CLOSE_PAREN));

			return expression;
		}
	};

	static final Symbol VIRTUAL_CLOSE_PAREN = new Symbol("VIRTUAL_CLOSE_PAREN", ")", 0);
	static final Symbol VIRTUAL_CLOSE_SET = new Symbol("VIRTUAL_CLOSE_SET", "]", 0);
	static final Symbol VIRTUAL_COMMA = new Symbol("VIRTUAL_COMMA", ",", 0);
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

	@Override
	@Nonnull
	public String toString() {
		return this.name;
	}
}
