package com.five35.dex;

import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Tested;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "unused" })
public class InfixSymbolTest {
	static class DummySymbol extends InfixSymbol {
		DummySymbol() {
			super("DUMMY", "(dummy)", -535);
		}

		@Override
		Result binary(final Result left, final Result right) {
			return new ScalarResult(0);
		}
	}

	@Tested
	InfixSymbolTest.DummySymbol infixSymbol;

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
	public void getNullDenotation_throws(@Mocked final Parser parser) throws Exception {
		new NonStrictExpectations() {
			{
				parser.getCurrentToken();
				this.result = new Token(1, "(dummy)");
			}
		};

		this.infixSymbol.getNullDenotation(parser);
	}
}
