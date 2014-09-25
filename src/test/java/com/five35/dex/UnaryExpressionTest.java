package com.five35.dex;

import com.google.common.base.Optional;
import java.util.Map;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method", "unused" })
public class UnaryExpressionTest {
	@Test
	public void ctor_checksForNullArguments(@Injectable final InfixOrPrefixSymbolTest.DummyInfixOrPrefixSymbol symbol, @Mocked final Expression operand) {
		new NullCheckExpectations(symbol, operand);

		new UnaryExpression(symbol, operand);
	}

	@Test
	public void execute_callsUnary(@Injectable final InfixOrPrefixSymbolTest.DummyInfixOrPrefixSymbol symbol, @Mocked final Expression expression) throws Exception {
		new Expectations() {
			{
				symbol.unary((Result) this.any);
			}
		};

		new UnaryExpression(symbol, expression).execute(Optional.<Map<String, Expression>>absent());
	}

	@Test
	public void execute_checksForNullArguments(@Injectable final InfixOrPrefixSymbolTest.DummyInfixOrPrefixSymbol symbol, @Mocked final Expression expression, @Mocked final Optional<Map<String, Expression>> variables) throws Exception {
		new NullCheckExpectations(variables);

		new UnaryExpression(symbol, expression).execute(variables);
	}
}
