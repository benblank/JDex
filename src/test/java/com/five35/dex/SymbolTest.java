package com.five35.dex;

import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method", "unused" })
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
	public void operatorAdd_adds(@Mocked final Result left, @Mocked final Result right) {
		this.assertBinaryOperator(33, Symbol.OPERATOR_ADD, left, right);
	}

	@Test
	public void operatorAdd_casts(@Mocked final Result operand) {
		new Expectations() {
			{
				operand.asScalar();
			}
		};

		Symbol.OPERATOR_ADD.unary(operand);
	}

	@Test
	public void operatorDivide_divides(@Mocked final Result left, @Mocked final Result right) {
		this.assertBinaryOperator(10, Symbol.OPERATOR_DIVIDE, left, right);
	}

	@Test
	public void operatorMultiply_multiplies(@Mocked final Result left, @Mocked final Result right) {
		this.assertBinaryOperator(90, Symbol.OPERATOR_MULTIPLY, left, right);
	}

	@Test
	public void operatorSubtract_negates(@Mocked final Result operand) {
		new NonStrictExpectations() {
			{
				operand.asScalar();
				this.result = 5;
			}
		};

		Assert.assertEquals(-5, Symbol.OPERATOR_SUBTRACT.unary(operand).asScalar(), 0.001);
	}

	@Test
	public void operatorSubtract_subtracts(@Mocked final Result left, @Mocked final Result right) {
		this.assertBinaryOperator(27, Symbol.OPERATOR_SUBTRACT, left, right);
	}

	// TODO: actually test Symbol itself
}
