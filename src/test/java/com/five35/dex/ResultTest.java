package com.five35.dex;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method" })
public class ResultTest {
	@Test
	public void cast_returnsOriginalOnClassMatch() throws Exception {
		final Result result = new ScalarResult(5);

		Assert.assertSame(result, result.cast(ScalarResult.class));
	}

	@Test(expected = ResultCastException.class)
	public void cast_throwsOnClassMismatch() throws Exception {
		(new Result() {
			@Override
			public float asScalar() {
				return 0;
			}
		}).cast(ScalarResult.class);
	}
}
