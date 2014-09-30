package com.five35.dex;

import com.google.common.base.Optional;
import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method", "unchecked", "unused" })
public class SymbolTest {
	private void assertBinaryOperator(final float expected, final BinarySymbol operator) throws Exception {
		final Result<?> left = new ScalarResult(30);
		final Result<?> right = new ScalarResult(3);

		Assert.assertEquals(expected, operator.binary(left, right).asScalar(), 0.001);
	}

	@Test
	public void operatorAdd_binary_adds() throws Exception {
		this.assertBinaryOperator(33, Symbol.OPERATOR_ADD);
	}

	@Test
	public void operatorAdd_binary_checksForNullArguments(@Mocked final Result<?> left, @Mocked final Result<?> right) throws Exception {
		new NullCheckExpectations(left, right);

		Symbol.OPERATOR_ADD.binary(left, right);
	}

	@Test
	public void operatorAdd_unary_casts(@Mocked final Result<?> operand) throws Exception {
		new Expectations() {
			{
				operand.asScalar();
			}
		};

		Symbol.OPERATOR_ADD.unary(operand);
	}

	@Test
	public void operatorAdd_unary_checksForNullArguments(@Mocked final Result<?> operand) throws Exception {
		new NullCheckExpectations(operand);

		Symbol.OPERATOR_ADD.unary(operand);
	}

	@Test
	public void operatorDivide_checksForNullArguments(@Mocked final Result<?> left, @Mocked final Result<?> right) throws Exception {
		new NullCheckExpectations(left, right);

		Symbol.OPERATOR_DIVIDE.binary(left, right);
	}

	@Test
	public void operatorDivide_divides() throws Exception {
		this.assertBinaryOperator(10, Symbol.OPERATOR_DIVIDE);
	}

	@Test
	public void operatorMultiply_checksForNullArguments(@Mocked final Result<?> left, @Mocked final Result<?> right) throws Exception {
		new NullCheckExpectations(left, right);

		Symbol.OPERATOR_MULTIPLY.binary(left, right);
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
				parser.getExpression(this.anyInt);
				parser.advance((Optional<? extends Symbol>) this.any);
			}
		};

		Symbol.OPERATOR_SUBEXPRESSION.getNullDenotation(parser);
	}

	@Test
	public void operatorSubexpression_getNullDenotation_getsFullExpression(@Mocked final Parser parser) throws Exception {
		new Expectations() {
			{
				parser.getExpression(0);
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
				parser.getExpression(this.anyInt);
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
	public void operatorSubtract_binary_checksForNullArguments(@Mocked final Result<?> left, @Mocked final Result<?> right) throws Exception {
		new NullCheckExpectations(left, right);

		Symbol.OPERATOR_SUBTRACT.binary(left, right);
	}

	@Test
	public void operatorSubtract_binary_subtracts() throws Exception {
		this.assertBinaryOperator(27, Symbol.OPERATOR_SUBTRACT);
	}

	@Test
	public void operatorSubtract_unary_checksForNullArguments(@Mocked final Result<?> operand) throws Exception {
		new NullCheckExpectations(operand);

		Symbol.OPERATOR_SUBTRACT.unary(operand);
	}

	@Test
	public void operatorSubtract_unary_negates() throws Exception {
		Assert.assertEquals(-5, Symbol.OPERATOR_SUBTRACT.unary(new ScalarResult(5)).asScalar(), 0.001);
	}

	// TODO: actually test Symbol itself
}
