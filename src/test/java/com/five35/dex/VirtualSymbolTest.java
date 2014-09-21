package com.five35.dex;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method", "unused" })
public class VirtualSymbolTest {
	@Test
	public void ctor_hasZeroBindingPower() {
		new MockUp<Symbol>() {
			@Mock(invocations = 1)
			void $init(final String name, final int bindingPower) {
				Assert.assertEquals(0, bindingPower);
			}
		};

		new VirtualSymbol("FOO", "(bar)");
	}

	@Test(expected = SyntaxException.class)
	public void getLeftDenotation_throwsSyntaxException(@Mocked final Parser parser, @Mocked final Expression left) throws Exception {
		new VirtualSymbol("FOO", "(bar)").getLeftDenotation(parser, left);
	}

	@Test(expected = SyntaxException.class)
	public void getNullDenotation_throwsSyntaxException(@Mocked final Parser parser) throws Exception {
		new VirtualSymbol("FOO", "(bar)").getNullDenotation(parser);
	}
}
