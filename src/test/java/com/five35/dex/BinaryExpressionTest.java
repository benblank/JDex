package com.five35.dex;

import com.google.common.base.Optional;
import java.util.Map;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import org.junit.Test;


@SuppressWarnings({ "javadoc", "unused" })
public class BinaryExpressionTest {
	@Test
	public void execute_callsInfix(@Injectable final InfixSymbolTest.DummySymbol symbol, @Mocked final Expression expression) {
		new Expectations() {
			{
				symbol.binary((Result) this.any, (Result) this.any);
			}
		};

		(new BinaryExpression(symbol, expression, expression)).execute(Optional.<Map<String, Expression>>absent());
	}
}
