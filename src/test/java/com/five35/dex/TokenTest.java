package com.five35.dex;

import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method" })
public class TokenTest {
	@SuppressWarnings("unused")
	@Test(expected = Exception.class)
	public void ctor_throwsOnNullValue() {
		new Token(0, null);
	}
}
