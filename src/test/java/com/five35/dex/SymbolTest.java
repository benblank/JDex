package com.five35.dex;

import com.google.common.base.Optional;
import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method", "unchecked", "unused" })
public class SymbolTest {
	private void assertBinaryNullCheck(final BinarySymbol operator) throws Exception {
		final Result<?> left = new ScalarResult(4);
		final Result<?> right = new ScalarResult(2);

		new NullCheckExpectations(left, right);

		operator.binary(left, right);
	}

	private void assertBinaryOperator(final float expected, final BinarySymbol operator) throws Exception {
		final Result<?> left = new ScalarResult(30);
		final Result<?> right = new ScalarResult(3);

		Assert.assertEquals(expected, operator.binary(left, right).cast(ScalarResult.class).getValue(), 0.001);
	}

	private void assertUnaryNullCheck(final UnarySymbol operator) throws Exception {
		final Result<?> operand = new ScalarResult(4);

		new NullCheckExpectations(operand);

		operator.unary(operand);
	}

	@Test
	public void getSymbol_recognizesKnownSymbols() throws Exception {
		final Symbol dummySymbol = new Symbol("", "{getSymbol_recognizesKnownSymbols}", BindingPower.NONE);
		final Token token = new Token(0, "{getSymbol_recognizesKnownSymbols}");

		Assert.assertSame(dummySymbol, Symbol.getSymbol(token));
	}

	@Test
	public void getSymbol_recognizesScalars() throws Exception {
		final Token token = new Token(0, "535");

		Assert.assertSame(Symbol.LITERAL_SCALAR, Symbol.getSymbol(token));
	}

	@Test
	public void getSymbol_recognizesVariables() throws Exception {
		final Token token = new Token(0, "do_wah_ditty_ditty_dum_ditty_do");

		Assert.assertSame(Symbol.VARIABLE_REFERENCE, Symbol.getSymbol(token));
	}

	@Test(expected = InvalidSymbolException.class)
	public void getSymbol_throwsOnUnrecognizedSymbol() throws Exception {
		final Token token = new Token(0, "five35");

		Symbol.getSymbol(token);
	}

	@Test
	public void operatorAdd_binary_adds() throws Exception {
		this.assertBinaryOperator(33, Symbol.OPERATOR_ADD);
	}

	@Test
	public void operatorAdd_binary_checksForNullArguments() throws Exception {
		this.assertBinaryNullCheck(Symbol.OPERATOR_ADD);
	}

	@Test
	public void operatorAdd_unary_casts() throws Exception {
		final Result<?> operand = new ScalarResult(5);

		new Expectations() {
			{
				operand.cast(ScalarResult.class).getValue();
			}
		};

		Symbol.OPERATOR_ADD.unary(operand);
	}

	@Test
	public void operatorAdd_unary_checksForNullArguments() throws Exception {
		this.assertUnaryNullCheck(Symbol.OPERATOR_ADD);
	}

	@Test
	public void operatorDivide_checksForNullArguments() throws Exception {
		this.assertBinaryNullCheck(Symbol.OPERATOR_DIVIDE);
	}

	@Test
	public void operatorDivide_divides() throws Exception {
		this.assertBinaryOperator(10, Symbol.OPERATOR_DIVIDE);
	}

	@Test
	public void operatorMultiply_checksForNullArguments() throws Exception {
		this.assertBinaryNullCheck(Symbol.OPERATOR_MULTIPLY);
	}

	@Test
	public void operatorMultiply_multiplies() throws Exception {
		this.assertBinaryOperator(90, Symbol.OPERATOR_MULTIPLY);
	}

	@Test
	public void operatorSubexpression_getNullDenotation_checksForNullArguments(@Mocked final Parser parser) throws Exception {
		new NullCheckExpectations(parser);

		new NonStrictExpectations() {
			{
				parser.getExpression((BindingPower) this.any);
				parser.advance((Optional<? extends Symbol>) this.any);
			}
		};

		Symbol.OPERATOR_SUBEXPRESSION.getNullDenotation(parser);
	}

	@Test
	public void operatorSubexpression_getNullDenotation_getsFullExpression(@Mocked final Parser parser) throws Exception {
		new Expectations() {
			{
				parser.getExpression(BindingPower.NONE);
			}
		};

		new NonStrictExpectations() {
			{
				parser.advance((Optional<? extends Symbol>) this.any);
			}
		};

		Symbol.OPERATOR_SUBEXPRESSION.getNullDenotation(parser);
	}

	@Test
	public void operatorSubexpression_getNullDenotation_requiresClosingParen(@Mocked final Parser parser) throws Exception {
		new NonStrictExpectations() {
			{
				parser.getExpression((BindingPower) this.any);
			}
		};

		new Expectations() {
			{
				parser.advance(Optional.of(Symbol.VIRTUAL_CLOSE_PAREN));
			}
		};

		Symbol.OPERATOR_SUBEXPRESSION.getNullDenotation(parser);
	}

	@Test
	public void operatorSubtract_binary_checksForNullArguments() throws Exception {
		this.assertBinaryNullCheck(Symbol.OPERATOR_SUBTRACT);
	}

	@Test
	public void operatorSubtract_binary_subtracts() throws Exception {
		this.assertBinaryOperator(27, Symbol.OPERATOR_SUBTRACT);
	}

	@Test
	public void operatorSubtract_unary_checksForNullArguments() throws Exception {
		this.assertUnaryNullCheck(Symbol.OPERATOR_SUBTRACT);
	}

	@Test
	public void operatorSubtract_unary_negates() throws Exception {
		Assert.assertEquals(-5, Symbol.OPERATOR_SUBTRACT.unary(new ScalarResult(5)).cast(ScalarResult.class).getValue(), 0.001);
	}
}
