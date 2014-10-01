package com.five35.dex;

import com.google.common.base.Optional;
import java.util.Map;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method", "unused" })
public class ScalarSymbolTest {
	@Test
	public void ctor_checksForNullArguments() {
		new NullCheckExpectations("FOO");

		new ScalarSymbol("FOO");
	}

	@Test
	public void ctor_hasZeroBindingPower() {
		new MockUp<Symbol>() {
			@Mock(invocations = 1)
			void $init(final String name, final int bindingPower) {
				Assert.assertEquals(0, bindingPower);
			}
		};

		new ScalarSymbol("FOO");
	}

	@Test(expected = SyntaxException.class)
	public void getLeftDenotation_throwsSyntaxException(@Mocked final Parser parser, @Mocked final Expression left) throws Exception {
		new ScalarSymbol("FOO").getLeftDenotation(parser, left);
	}

	@Test
	public void getNullDenotation_checksForNullArguments(@Mocked final Parser parser, @Mocked final Token token) throws Exception {
		new NullCheckExpectations(parser);

		new NonStrictExpectations() {
			{
				parser.getCurrentToken();
				this.result = token;

				token.toString();
				this.result = "0";
			}
		};

		new ScalarSymbol("FOO").getNullDenotation(parser);
	}

	@Test
	public void getNullDenotation_returnsExpressionReturningCorrectValue(@Mocked final Parser parser) throws Exception {
		new NonStrictExpectations() {
			{
				parser.getCurrentToken();
				this.result = new Token(1, "5.35");
			}
		};

		final Result<?> actual = new ScalarSymbol("FOO").getNullDenotation(parser).execute(Optional.<Map<String, Expression>>absent());

		Assert.assertEquals(5.35, actual.cast(ScalarResult.class).getValue(), 0.001);
	}

	@Test(expected = ParserException.class)
	public void getNullDenotation_throwsOnUnparsableFloat(@Mocked final Parser parser) throws Exception {
		new NonStrictExpectations() {
			{
				parser.getCurrentToken();
				this.result = new Token(1, "abc");
			}
		};

		new ScalarSymbol("FOO").getNullDenotation(parser);
	}
}
