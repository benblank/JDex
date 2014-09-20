package com.five35.dex;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method" })
public class ScalarResultTest {
	@Test
	public void asScalar_returnsValue() {
		Assert.assertEquals(5, new ScalarResult(5).asScalar(), 0);
	}

	@Test
	public void asSet_containsOnlyOneElement() {
		Assert.assertEquals(1, new ScalarResult(5).asSet().size());
	}

	@Test
	public void asSet_containsSelf() {
		final ScalarResult result = new ScalarResult(5);

		Assert.assertTrue(result.asSet().contains(result));
	}
}
