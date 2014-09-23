package com.five35.dex;

import mockit.Mocked;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method", "unused" })
public class TokenTest {
	@Test
	public void ctor_checksForNullArguments(@Mocked final String string) {
		new NullCheckExpectations(string);

		new Token(0, string);
	}
}
