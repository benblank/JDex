package com.five35.dex;

import com.google.common.base.Optional;
import java.util.Map;
import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "unchecked", "unused" })
public class RepeatExpressionTest {
	@Test
	public void execute_executesRightSideMultipleTimes(@Mocked final Expression left, @Mocked final Expression right) throws Exception {
		new NonStrictExpectations() {
			{
				left.execute((Optional<Map<String, Expression>>) this.any);
				this.result = new ScalarResult(5);
			}
		};

		new Expectations() {
			{
				right.execute((Optional<Map<String, Expression>>) this.any);
				this.times = 5;
			}
		};

		new RepeatExpression(left, right).execute(Optional.<Map<String, Expression>>absent());
	}
}
