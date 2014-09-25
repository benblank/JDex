package com.five35.dex;

import com.google.common.base.Optional;
import java.util.Map;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method", "unused" })
public class BinaryExpressionTest {
	@Test
	public void ctor_checksForNullArguments(@Injectable final InfixSymbolTest.DummyInfixSymbol symbol, @Mocked final Expression left, @Mocked final Expression right) {
		new NullCheckExpectations(symbol, left, right);

		new BinaryExpression(symbol, left, right);
	}

	@Test
	public void execute_callsBinary(@Injectable final InfixSymbolTest.DummyInfixSymbol symbol, @Mocked final Expression expression) throws Exception {
		new Expectations() {
			{
				symbol.binary((Result) this.any, (Result) this.any);
			}
		};

		new BinaryExpression(symbol, expression, expression).execute(Optional.<Map<String, Expression>>absent());
	}

	@Test
	public void execute_checksForNullArguments(@Injectable final InfixSymbolTest.DummyInfixSymbol symbol, @Mocked final Expression expression, @Mocked final Optional<Map<String, Expression>> variables) throws Exception {
		new NullCheckExpectations(variables);

		new BinaryExpression(symbol, expression, expression).execute(variables);
	}
}
