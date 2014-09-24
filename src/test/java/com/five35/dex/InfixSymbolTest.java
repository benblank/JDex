package com.five35.dex;

import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Tested;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method", "unused" })
public class InfixSymbolTest {
	static class DummySymbol extends InfixSymbol {
		DummySymbol() {
			super("DUMMY", "(dummy)", -535);
		}

		@Override
		public Result binary(final Result left, final Result right) {
			return new ScalarResult(0);
		}
	}

	@Tested
	InfixSymbolTest.DummySymbol infixSymbol;

	@Test
	public void ctor_checksForNullArguments() {
		new NullCheckExpectations("DUMMY", "(dummy)");

		new InfixSymbolTest.DummySymbol();
	}

	@Test
	public void getLeftDenotation_checksForNullArguments(@Mocked final Parser parser, @Mocked final Expression left) throws Exception {
		new NullCheckExpectations(parser, left);

		this.infixSymbol.getLeftDenotation(parser, left);
	}

	@Test
	public void getLeftDenotation_getsExpression(@Mocked final Parser parser, @Mocked final Expression left, @Mocked final Expression right) throws Exception {
		new Expectations() {
			{
				parser.getExpression(-535);
				this.result = right;
			}
		};

		this.infixSymbol.getLeftDenotation(parser, left);
	}

	@Test
	public void getLeftDenotation_returnsBinaryExpression(@Mocked final Parser parser, @Mocked final Expression left, @Mocked final Expression right) throws Exception {
		new NonStrictExpectations() {
			{
				parser.getExpression(this.anyInt);
				this.result = right;
			}
		};

		Assert.assertTrue(this.infixSymbol.getLeftDenotation(parser, left) instanceof BinaryExpression);
	}

	@Test(expected = SyntaxException.class)
	public void getNullDenotation_throwsSyntaxException(@Mocked final Parser parser) throws Exception {
		this.infixSymbol.getNullDenotation(parser);
	}
}
