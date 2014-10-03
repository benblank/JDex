package com.five35.dex;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

@SuppressWarnings("javadoc")
public class EndToEndTest {
	@Rule
	@SuppressWarnings("checkstyle:visibilitymodifier")
	public final ErrorCollector collector = new ErrorCollector();

	private void assertResult(final String expected, final String expression) throws Exception {
		this.assertResult(expected, expression, Optional.<Map<String, Expression>>absent());
	}

	@SuppressWarnings("checkstyle:illegalcatch")
	private void assertResult(final String expected, final String expression, final Optional<Map<String, Expression>> variables) throws Exception {
		try {
			final Result<?> result = Parser.parse(expression).execute(variables);

			this.collector.checkThat(result.toString(), CoreMatchers.equalTo(expected));
		} catch (final Exception ex) {
			this.collector.addError(ex);
		}
	}

	@Test
	public void endToEnd() throws Exception {
		this.assertResult("2.0", "1 + 1");
		this.assertResult("2.0", "1+1");
		this.assertResult("2.0", "one + one", Optional.of((Map<String, Expression>) ImmutableMap.of("one", Parser.parse("1"))));

		this.assertResult("3.0", "4 - 1");
		this.assertResult("6.0", "2 * 3");
		this.assertResult("3.5", "7 / 2");

		this.assertResult("14.0", "2 + 3 * 4");
		this.assertResult("4.0", "6 - 1 - 1");

		this.assertResult("-5.0", "-5");
		this.assertResult("2.0", "+2");

		this.assertResult("-3.0", "-1 - 2");
		this.assertResult("8.0", "5 - -3");
		this.assertResult("2.0", "-+2+--++-+-4");

		this.assertResult("9.0", "(1 + 2) * 3");
		this.assertResult("-5.0", "-(2 + 3)");
		this.assertResult("-4.0", "4 * (2 - 3)");

		this.assertResult("[5.0, 3.0, 5.0]", "[5,3,5]");
		this.assertResult("[1.0, [2.0, [3.0]]]", "[1,[2,[3]]]");
		this.assertResult("13.0", "5 + [3, 5]");
		this.assertResult("10.0", "[1,[2,[3]]] + 4");
		this.assertResult("5.0", "5 + []");

		this.assertResult("[1.0, 1.0, 1.0]", "3 x 1");
		this.assertResult("[2.0, 2.0, 2.0]", "3 x 1 + 1");
		this.assertResult("[[1.0, 2.0, 3.0], [1.0, 2.0, 3.0]]", "2x [1,2,3]");
	}
}
