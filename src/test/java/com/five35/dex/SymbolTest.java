package com.five35.dex;

import com.google.common.base.Optional;
import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method", "unchecked", "unused" })
public class SymbolTest {
	private void assertBinaryOperator(final float expected, final BinarySymbol operator, final Result left, final Result right) {
		new NonStrictExpectations() {
			{
				left.asScalar();
				this.result = 30;

				right.asScalar();
				this.result = 3;
			}
		};

		Assert.assertEquals(expected, operator.binary(left, right).asScalar(), 0.001);
	}

	@Test
	public void operatorAdd_binary_adds(@Mocked final Result left, @Mocked final Result right) {
		this.assertBinaryOperator(33, Symbol.OPERATOR_ADD, left, right);
	}

	@Test
	public void operatorAdd_binary_checksForNullArguments(@Mocked final Result left, @Mocked final Result right) {
		new NullCheckExpectations(left, right);

		Symbol.OPERATOR_ADD.binary(left, right);
	}

	@Test
	public void operatorAdd_unary_casts(@Mocked final Result operand) {
		new Expectations() {
			{
				operand.asScalar();
			}
		};

		Symbol.OPERATOR_ADD.unary(operand);
	}

	@Test
	public void operatorAdd_unary_checksForNullArguments(@Mocked final Result operand) {
		new NullCheckExpectations(operand);

		Symbol.OPERATOR_ADD.unary(operand);
	}

	@Test
	public void operatorDivide_checksForNullArguments(@Mocked final Result left, @Mocked final Result right) {
		new NullCheckExpectations(left, right);

		Symbol.OPERATOR_DIVIDE.binary(left, right);
	}

	@Test
	public void operatorDivide_divides(@Mocked final Result left, @Mocked final Result right) {
		this.assertBinaryOperator(10, Symbol.OPERATOR_DIVIDE, left, right);
	}

	@Test
	public void operatorMultiply_checksForNullArguments(@Mocked final Result left, @Mocked final Result right) {
		new NullCheckExpectations(left, right);

		Symbol.OPERATOR_MULTIPLY.binary(left, right);
	}

	@Test
	public void operatorMultiply_multiplies(@Mocked final Result left, @Mocked final Result right) {
		this.assertBinaryOperator(90, Symbol.OPERATOR_MULTIPLY, left, right);
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
	public void operatorSubtract_binary_checksForNullArguments(@Mocked final Result left, @Mocked final Result right) {
		new NullCheckExpectations(left, right);

		Symbol.OPERATOR_SUBTRACT.binary(left, right);
	}

	@Test
	public void operatorSubtract_binary_subtracts(@Mocked final Result left, @Mocked final Result right) {
		this.assertBinaryOperator(27, Symbol.OPERATOR_SUBTRACT, left, right);
	}

	@Test
	public void operatorSubtract_unary_checksForNullArguments(@Mocked final Result operand) {
		new NullCheckExpectations(operand);

		Symbol.OPERATOR_SUBTRACT.unary(operand);
	}

	@Test
	public void operatorSubtract_unary_negates(@Mocked final Result operand) {
		new NonStrictExpectations() {
			{
				operand.asScalar();
				this.result = 5;
			}
		};

		Assert.assertEquals(-5, Symbol.OPERATOR_SUBTRACT.unary(operand).asScalar(), 0.001);
	}

	// TODO: actually test Symbol itself
}
