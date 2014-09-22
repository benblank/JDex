package com.five35.dex;

import com.google.common.base.Preconditions;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
	public void ctor_checksForNullArguments() {
		@SuppressFBWarnings(value = "DMI_DOH", justification = "It's sensical when declaring expectations.")
		final class ArgumentExpectations extends Expectations {
			ArgumentExpectations() {
				super(Preconditions.class);

				Preconditions.checkNotNull("DUMMY");
				this.result = "DUMMY";

				Preconditions.checkNotNull("(dummy)");
				this.result = "(dummy)";
			}
		}

		new ArgumentExpectations();

		new InfixSymbolTest.DummySymbol();
	}

	@Test
	public void getLeftDenotation_checksForNullArguments(@Mocked final Parser parser, @Mocked final Expression left) throws Exception {
		new Expectations(Preconditions.class) {
			{
				Preconditions.checkNotNull(parser);
				Preconditions.checkNotNull(left);
			}
		};

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
