package com.five35.dex;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method" })
public class ResultTest {
	private static class DummyResult extends Result<String> {
		protected DummyResult() {
			super("");
		}
	}

	@Test
	public void cast_returnsOriginalOnClassMatch() throws Exception {
		final Result<?> result = new ScalarResult(5);

		Assert.assertSame(result, result.cast(ScalarResult.class));
	}

	@Test(expected = ResultCastException.class)
	public void cast_throwsOnClassMismatch() throws Exception {
		new ResultTest.DummyResult().cast(ScalarResult.class);
	}
}
