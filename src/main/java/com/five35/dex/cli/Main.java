package com.five35.dex.cli;

import com.five35.dex.ExecutionException;
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

		try {
			System.out.println(Parser.parse(source).execute(Optional.<Map<String, Expression>>absent()).asScalar());
		} catch (final ExecutionException | ParserException ex) {
			System.err.println(ex.getMessage());

			return;
		}
	}
}
