package com.five35.dex;

import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Tested;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method", "unused" })
public class InfixOrPrefixSymbolTest {
	static class DummyInfixOrPrefixSymbol extends InfixOrPrefixSymbol {
		DummyInfixOrPrefixSymbol() {
			super("DUMMY_PREFIX_OR_INFIX", "(dummy prefix or infix)", -5, -107);
		}

		@Override
		public Result<?> binary(final Result<?> left, final Result<?> right) {
			return new ScalarResult(0);
		}

		@Override
		public Result<?> unary(final Result<?> operand) {
			return new ScalarResult(0);
		}
	}

	@Tested
	InfixOrPrefixSymbolTest.DummyInfixOrPrefixSymbol infixOrPrefixSymbol;

	@Test
	public void ctor_checksForNullArguments() {
		new NullCheckExpectations("DUMMY_PREFIX_OR_INFIX", "(dummy prefix or infix)");

		new InfixOrPrefixSymbolTest.DummyInfixOrPrefixSymbol();
	}

	@Test
	public void getLeftDenotation_checksForNullArguments(@Mocked final Parser parser, @Mocked final Expression left) throws Exception {
		new NullCheckExpectations(parser, left);

		this.infixOrPrefixSymbol.getLeftDenotation(parser, left);
	}

	@Test
	public void getLeftDenotation_getsExpression(@Mocked final Parser parser, @Mocked final Expression left, @Mocked final Expression right) throws Exception {
		new Expectations() {
			{
				parser.getExpression(-5);
				this.result = right;
			}
		};

		this.infixOrPrefixSymbol.getLeftDenotation(parser, left);
	}

	@Test
	public void getLeftDenotation_returnsBinaryExpression(@Mocked final Parser parser, @Mocked final Expression left, @Mocked final Expression right) throws Exception {
		new NonStrictExpectations() {
			{
				parser.getExpression(this.anyInt);
				this.result = right;
			}
		};

		Assert.assertTrue(this.infixOrPrefixSymbol.getLeftDenotation(parser, left) instanceof BinaryExpression);
	}

	@Test
	public void getNullDenotation_checksForNullArguments(@Mocked final Parser parser) throws Exception {
		new NullCheckExpectations(parser);

		this.infixOrPrefixSymbol.getNullDenotation(parser);
	}

	@Test
	public void getNullDenotation_getsExpression(@Mocked final Parser parser, @Mocked final Expression operand) throws Exception {
		new Expectations() {
			{
				parser.getExpression(-107);
				this.result = operand;
			}
		};

		this.infixOrPrefixSymbol.getNullDenotation(parser);
	}

	@Test
	public void getNullDenotation_returnsUnaryExpression(@Mocked final Parser parser, @Mocked final Expression operand) throws Exception {
		new NonStrictExpectations() {
			{
				parser.getExpression(this.anyInt);
				this.result = operand;
			}
		};

		Assert.assertTrue(this.infixOrPrefixSymbol.getNullDenotation(parser) instanceof UnaryExpression);
	}
}
