package com.five35.dex.cli;

import com.five35.dex.Expression;
import com.five35.dex.Parser;
import com.five35.dex.ParserException;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import java.util.Map;

@SuppressWarnings("javadoc")
public class Main {
	private Main() {
	}

	public static void main(final String[] args) {
		final String source = Joiner.on(" ").join(args);
		final Expression expression;

		try {
			expression = Parser.parse(source);
		} catch (final ParserException ex) {
			System.err.println(ex.getMessage());

			return;
		}

		System.out.println(expression.execute(Optional.<Map<String, Expression>>absent()).asScalar());
	}
}
