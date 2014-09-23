package com.five35.dex;

import com.google.common.base.Optional;
import java.util.Map;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method", "unchecked", "unused" })
public class ReferenceSymbolTest {
	@Test
	public void ctor_checksForNullArguments() {
		new NullCheckExpectations("FOO");

		new ReferenceSymbol("FOO");
	}

	@Test
	public void ctor_hasZeroBindingPower() {
		new MockUp<Symbol>() {
			@Mock(invocations = 1)
			void $init(final String name, final int bindingPower) {
				Assert.assertEquals(0, bindingPower);
			}
		};

		new ReferenceSymbol("FOO");
	}

	@Test(expected = SyntaxException.class)
	public void getLeftDenotation_throwsSyntaxException(@Mocked final Parser parser, @Mocked final Expression left) throws Exception {
		new ReferenceSymbol("FOO").getLeftDenotation(parser, left);
	}

	@Test
	public void getNullDenotation_checksForNullArguments(@Mocked final Parser parser, @Mocked final Token token) throws Exception {
		new NullCheckExpectations(parser);

		new NonStrictExpectations() {
			{
				parser.getCurrentToken();
				this.result = token;

				token.toString();
				this.result = "abc";
			}
		};

		new ReferenceSymbol("FOO").getNullDenotation(parser);
	}

	@Test
	public void getNullDenotation_ExecutesVariable(@Mocked final Parser parser, @Mocked final Map<String, Expression> map, @Mocked final Expression expression) throws Exception {
		final Optional<Map<String, Expression>> optional = Optional.of(map);

		new Expectations() {
			{
				expression.execute(optional);
			}
		};

		new NonStrictExpectations() {
			{
				parser.getCurrentToken();
				this.result = new Token(1, "abc");

				map.containsKey("abc");
				this.result = true;

				map.get("abc");
				this.result = expression;
			}
		};

		new ReferenceSymbol("FOO").getNullDenotation(parser).execute(optional);
	}

	@Test
	public void getNullDenotation_returnsExpressionExecutingCorrectVariable(@Mocked final Parser parser, @Mocked final Map<String, Expression> map, @Mocked final Expression expression) throws Exception {
		new Expectations() {
			{
				map.get("abc");
				this.result = expression;
			}
		};

		new NonStrictExpectations() {
			{
				parser.getCurrentToken();
				this.result = new Token(1, "abc");

				map.containsKey("abc");
				this.result = true;

				expression.execute((Optional<Map<String, Expression>>) this.any);
			}
		};

		new ReferenceSymbol("FOO").getNullDenotation(parser).execute(Optional.of(map));
	}

	@Test(expected = UndefinedVariableException.class)
	public void getNullDenotation_returnsExpressionThrowingOnNoVariables(@Mocked final Parser parser, @Mocked final Optional<Map<String, Expression>> variables) throws Exception {
		new NonStrictExpectations() {
			{
				parser.getCurrentToken();
				this.result = new Token(1, "abc");

				variables.isPresent();
				this.result = false;
			}
		};

		new ReferenceSymbol("FOO").getNullDenotation(parser).execute(variables);
	}

	@Test(expected = UndefinedVariableException.class)
	public void getNullDenotation_returnsExpressionThrowingOnUndefinedVariable(@Mocked final Parser parser, @Mocked final Map<String, Expression> map) throws Exception {
		new NonStrictExpectations() {
			{
				parser.getCurrentToken();
				this.result = new Token(1, "abc");

				map.containsKey(this.anyString);
				this.result = false;
			}
		};

		new ReferenceSymbol("FOO").getNullDenotation(parser).execute(Optional.of(map));
	}
}
