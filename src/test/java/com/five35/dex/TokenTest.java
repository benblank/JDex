package com.five35.dex;

import com.google.common.base.Preconditions;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method", "unused" })
public class TokenTest {
	@Test
	public void ctor_checksForNullArguments(@Mocked final String string) {
		new Expectations(Preconditions.class) {
			{
				Preconditions.checkNotNull(string);
			}
		};

		new Token(0, string);
	}
}
