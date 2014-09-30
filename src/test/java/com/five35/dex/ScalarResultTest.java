package com.five35.dex;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method" })
public class ScalarResultTest {
	@Test
	public void asScalar_returnsValue() {
		Assert.assertEquals(5, new ScalarResult(5).asScalar(), 0);
	}
}
