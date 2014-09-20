package com.five35.dex;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "unused" })
public class SymbolTest {
	private void assertOperator(final float expected, final InfixSymbol operator, final Result left, final Result right) {
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
		this.assertOperator(33, Symbol.OPERATOR_ADD, left, right);
	}

	@Test
	public void operatorDivide_divides(@Mocked final Result left, @Mocked final Result right) {
		this.assertOperator(10, Symbol.OPERATOR_DIVIDE, left, right);
	}

	@Test
	public void operatorMultiply_multiplies(@Mocked final Result left, @Mocked final Result right) {
		this.assertOperator(90, Symbol.OPERATOR_MULTIPLY, left, right);
	}

	@Test
	public void operatorSubtract_subtracts(@Mocked final Result left, @Mocked final Result right) {
		this.assertOperator(27, Symbol.OPERATOR_SUBTRACT, left, right);
	}

	// TODO: actually test Symbol itself
}
