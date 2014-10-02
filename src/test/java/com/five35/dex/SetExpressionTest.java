package com.five35.dex;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Map;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "unchecked", "unused" })
public class SetExpressionTest {
	@Test
	public void execute_executesAllElements(@Mocked final Expression expression1, @Mocked final Expression expression2) throws Exception {
		final Collection<Expression> elements = ImmutableList.of(expression1, expression2);
		final SetExpression tested = new SetExpression(elements);

		new Expectations() {
			{
				expression1.execute((Optional<Map<String, Expression>>) this.any);
				expression2.execute((Optional<Map<String, Expression>>) this.any);
			}
		};

		tested.execute(Optional.<Map<String, Expression>>absent());
	}
}
